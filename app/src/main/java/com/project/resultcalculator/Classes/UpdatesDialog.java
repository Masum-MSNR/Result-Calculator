package com.project.resultcalculator.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.project.resultcalculator.R;

public class UpdatesDialog extends AppCompatDialogFragment {

    TextView detailsTv, latestVersionTv;
    int x;
    Context context;
    LinearLayout ll;
    String details, version, appUrl,whatsNewUrl;

    public UpdatesDialog( Context context, String details, String version, String appUrl, String whatsNewUrl,int x) {
        this.x = x;
        this.context = context;
        this.details = details;
        this.version = version;
        this.appUrl = appUrl;
        this.whatsNewUrl = whatsNewUrl;
    }

    public UpdatesDialog(Context context, String details, int x) {
        this.x = x;
        this.context = context;
        this.details = details;
    }

    public UpdatesDialog(Context context, String details, String version, String appUrl, int x) {
        this.x = x;
        this.context = context;
        this.details = details;
        this.version = version;
        this.appUrl = appUrl;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.updates_dialog, null);
        detailsTv = view.findViewById(R.id.details);
        latestVersionTv = view.findViewById(R.id.latest_version);
        ll = view.findViewById(R.id.ll);
        if (x == 0) {
            ll.setVisibility(View.GONE);
            detailsTv.setText(details);
            dialog.setView(view)
                    .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        } else if(x==1) {
            detailsTv.setText(details);
            latestVersionTv.setText(version + "]");
            dialog.setView(view)
                    .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        }else{
            detailsTv.setText(details);
            latestVersionTv.setText(version + "]");
            dialog.setView(view)
                    .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("WHAT'S NEW?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(whatsNewUrl));
                            startActivity(intent);
                        }
                    });
        }
        return dialog.create();
    }
}
