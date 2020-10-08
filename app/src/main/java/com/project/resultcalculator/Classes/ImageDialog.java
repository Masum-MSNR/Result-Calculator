package com.project.resultcalculator.Classes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.project.resultcalculator.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDialog extends AppCompatDialogFragment {
    TextView resultTv;
    String result;
    LinearLayout ll;
    Context context;
    InterstitialAd interstitialAd;


    public ImageDialog(String result, Context context) {
        this.result = result;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.image_dialog, null);
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        interstitialAd=new InterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-6730734378580987/7545700991");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        dialog.setView(view)
                .setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        save();
                        if(interstitialAd.isLoaded()){
                            interstitialAd.show();
                        }
                    }
                })
                .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(interstitialAd.isLoaded()){
                            interstitialAd.show();
                        }
                    }
                });
        ll= view.findViewById(R.id.ll);
        resultTv = view.findViewById(R.id.result);
        resultTv.setText(result);
        return dialog.create();
    }


    private void save() {
        Bitmap bitmap = makeImage(ll);
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/Rs/");
        if(!dir.exists()){
            dir.mkdir();
        }
        String filename = String.format("RS_%d.png", System.currentTimeMillis());
        File outFile = new File(dir, filename);
        try {
            FileOutputStream fileOutPutStream = new FileOutputStream(outFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutPutStream);
            fileOutPutStream.flush();
            fileOutPutStream.close();
            Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(outFile));
            context.sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceAsColor")
    private Bitmap makeImage(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        } else {
            canvas.drawColor(android.R.color.white);
        }
        view.draw(canvas);
        return bitmap;
    }
}
