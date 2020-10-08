package com.project.resultcalculator.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.project.resultcalculator.Classes.ImageDialog;
import com.project.resultcalculator.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Result extends AppCompatActivity {

    TextView textResult;

    String type = "", TS = "", TS1 = "", TS2 = "";

    ArrayList<String> textGuildResult = new ArrayList<>();
    ArrayList<String> textGuildResult1 = new ArrayList<>();
    ArrayList<String> textGuildResult2 = new ArrayList<>();

    Spinner format;

    LinearLayout ll;

    RelativeLayout prl;

    Button copyAsText, saveAsImage;

    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_result);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //InterstitialAd
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-6730734378580987/7545700991");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        //BannerAdd
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        //EditText
        textResult = findViewById(R.id.text_result);

        //LinearLayout
        ll = findViewById(R.id.ll);
        prl = findViewById(R.id.prl);

        //Button
        copyAsText = findViewById(R.id.copy_as_text);
        saveAsImage = findViewById(R.id.save_as_image);

        //Spinner
        format = findViewById(R.id.format);

        ArrayAdapter<String> formatAdapter = new ArrayAdapter<>(
                this,
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.format));
        formatAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        format.setAdapter(formatAdapter);

        format.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    textResult.setText(TS);
                } else if (position == 1) {
                    textResult.setText(TS2);
                } else if (position == 2) {
                    textResult.setText(TS1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        copyAsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("key", textResult.getText().toString() + "\n\nCreated by\nResult Calculator Android App.");
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });
        saveAsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    ImageDialog imageDialog = new ImageDialog(textResult.getText().toString() + "\n\nCreated by\nResult Calculator Android App.", Result.this);
                    imageDialog.setCancelable(false);
                    imageDialog.show(getSupportFragmentManager(), null);
                }
            }
        });

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ll.getWindowToken(), 0);
                textResult.clearFocus();
            }
        });
        prl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(prl.getWindowToken(), 0);
                textResult.clearFocus();
            }
        });

        Intent intent = getIntent();
        type = intent.getStringExtra("Type");
        if (type.equals("12")) {
            Map<Long, String> guildResult = (HashMap<Long, String>) intent.getExtras().get("GuildResult");
            Map<Long, String> pointPerMatch = (HashMap<Long, String>) intent.getExtras().get("PointPerMatch");
            Map<Long, Long> killP = (HashMap<Long, Long>) intent.getExtras().get("KillP");
            Map<Long, Long> serial = (HashMap<Long, Long>) intent.getExtras().get("Serial");
            TreeMap<Long, String> nGuildResult = new TreeMap<>(guildResult);
            TreeMap<Long, String> nPointPerMatch = new TreeMap<>(pointPerMatch);
            TreeMap<Long, Long> nKillP = new TreeMap<>(killP);
            TreeMap<Long, Long> nSerial = new TreeMap<>(serial);
            String winner = "";
            for (Map.Entry<Long, String> temp : nGuildResult.entrySet()) {
                String tGuildResult, tGuildResult1, tGuildResult2;
                tGuildResult = temp.getValue() + " = ";
                tGuildResult1 = tGuildResult;
                tGuildResult2 = tGuildResult;
                tGuildResult1 = tGuildResult1 + nPointPerMatch.get(temp.getKey()) + (((temp.getKey() - nSerial.get(temp.getKey())) / 100 - nKillP.get(temp.getKey())) / 10000) + " (" + (nKillP.get(temp.getKey()) / 20) + " Kill)";
                tGuildResult2 = tGuildResult2 + (nKillP.get(temp.getKey())) + "+" + ((((temp.getKey() - nSerial.get(temp.getKey())) / 100 - nKillP.get(temp.getKey())) / 10000) - nKillP.get(temp.getKey())) + " = ";
                tGuildResult = tGuildResult + ((((temp.getKey() - nSerial.get(temp.getKey())) / 100 - nKillP.get(temp.getKey())) / 10000) - nKillP.get(temp.getKey())) + "+" + (nKillP.get(temp.getKey())) + " = ";
                tGuildResult2 = tGuildResult2 + (((temp.getKey() - nSerial.get(temp.getKey())) / 100 - nKillP.get(temp.getKey())) / 10000);
                tGuildResult = tGuildResult + (((temp.getKey() - nSerial.get(temp.getKey())) / 100 - nKillP.get(temp.getKey())) / 10000);
                tGuildResult2 = tGuildResult2 + " (" + (nKillP.get(temp.getKey()) / 20) + " Kill)";
                tGuildResult = tGuildResult + " (" + (nKillP.get(temp.getKey()) / 20) + " Kill)";
                winner = "\nCongratulations To " + temp.getValue();
                textGuildResult.add(tGuildResult);
                textGuildResult1.add(tGuildResult1);
                textGuildResult2.add(tGuildResult2);
            }
            int i = 1;
            String ts = "__Result__\n\n\n", ts1 = ts, ts2 = ts;
            for (int x = guildResult.size() - 1; x >= 0; x--) {
                ts = ts + i + ". " + textGuildResult.get(x) + "\n\n";
                ts1 = ts1 + i + ". " + textGuildResult1.get(x) + "\n\n";
                ts2 = ts2 + i++ + ". " + textGuildResult2.get(x) + "\n\n";
            }
            ts = ts + winner;
            ts1 = ts1 + winner;
            ts2 = ts2 + winner;
            TS = ts;
            TS1 = ts1;
            TS2 = ts2;
            textResult.setText(ts);
        } else {
            Map<Long, String> sortedGuildName = (HashMap<Long, String>) intent.getExtras().get("SortedGuildName");
            TreeMap<Long, String> nSortedGuildName = new TreeMap<>(sortedGuildName);
            Map<Long, Long> sortedGuildKill = (HashMap<Long, Long>) intent.getExtras().get("SortedGuildKill");
            TreeMap<Long, Long> nSortedGuildKill = new TreeMap<>(sortedGuildKill);
            Map<Long, TreeMap<Long, String>> sortedSquadPk = (HashMap<Long, TreeMap<Long, String>>) intent.getExtras().get("SortedSquadPk");
            TreeMap<Long, TreeMap<Long, String>> nSortedSquadPk = new TreeMap<>(sortedSquadPk);
            Map<Long, Long> sortedSquadKill = (HashMap<Long, Long>) intent.getExtras().get("SortedSquadKill");
            TreeMap<Long, Long> nSortedSquadKill = new TreeMap<>(sortedSquadKill);
            Map<Long, Long> sortedSquadSerial = (HashMap<Long, Long>) intent.getExtras().get("SortedSquadSerial");
            TreeMap<Long, Long> nSortedSquadSerial = new TreeMap<>(sortedSquadSerial);
            Map<Long, Long> sortedGuildSerial = (HashMap<Long, Long>) intent.getExtras().get("SortedGuildSerial");
            TreeMap<Long, Long> nSortedGuildSerial = new TreeMap<>(sortedGuildSerial);
            Map<Long, String> pointPerMatch = (HashMap<Long, String>) intent.getExtras().get("PointPerMatch");
            TreeMap<Long, String> nPointPerMatch = new TreeMap<>(pointPerMatch);
            Map<Long, String> sPointPerMatch = (HashMap<Long, String>) intent.getExtras().get("sPointPerMatch");
            TreeMap<Long, String> nsPointPerMatch = new TreeMap<>(sPointPerMatch);
            ArrayList<ArrayList<String>> TextSquadResult = new ArrayList<>();
            ArrayList<ArrayList<String>> TextSquadResult1 = new ArrayList<>();
            ArrayList<ArrayList<String>> TextSquadResult2 = new ArrayList<>();
            String winner = "";
            for (Map.Entry<Long, String> temp : nSortedGuildName.entrySet()) {
                String tGuildResult, tGuildResult1, tGuildResult2;
                tGuildResult = temp.getValue() + " = ";
                tGuildResult1 = tGuildResult;
                tGuildResult2 = tGuildResult;
                tGuildResult1 = tGuildResult1 + nPointPerMatch.get(temp.getKey()) + (((temp.getKey() - nSortedGuildSerial.get(temp.getKey())) / 100 - nSortedGuildSerial.get(temp.getKey())) / 10000) + " (" + (nSortedGuildKill.get(temp.getKey()) / 20) + " Kill)";
                tGuildResult2 = tGuildResult2 + (nSortedGuildKill.get(temp.getKey())) + "+" + ((((temp.getKey() - nSortedGuildSerial.get(temp.getKey())) / 100 - nSortedGuildKill.get(temp.getKey())) / 10000) - nSortedGuildKill.get(temp.getKey())) + " = ";
                tGuildResult = tGuildResult + ((((temp.getKey() - nSortedGuildSerial.get(temp.getKey())) / 100 - nSortedGuildKill.get(temp.getKey())) / 10000) - nSortedGuildKill.get(temp.getKey())) + "+" + (nSortedGuildKill.get(temp.getKey())) + " = ";
                tGuildResult = tGuildResult + (((temp.getKey() - nSortedGuildSerial.get(temp.getKey())) / 100 - nSortedGuildKill.get(temp.getKey())) / 10000);
                tGuildResult2 = tGuildResult2 + (((temp.getKey() - nSortedGuildSerial.get(temp.getKey())) / 100 - nSortedGuildKill.get(temp.getKey())) / 10000);
                tGuildResult = tGuildResult + " (" + (nSortedGuildKill.get(temp.getKey()) / 20) + " Kill)";
                tGuildResult2 = tGuildResult2 + " (" + (nSortedGuildKill.get(temp.getKey()) / 20) + " Kill)";
                textGuildResult.add(tGuildResult);
                textGuildResult1.add(tGuildResult1);
                textGuildResult2.add(tGuildResult2);
                winner = "\nCongratulations To " + temp.getValue();
            }
            int ii = sortedGuildName.size() - 1;
            int iii = 1;
            String ts = "__Result__\n\n\n", ts1 = ts, ts2 = ts;
            for (Map.Entry<Long, TreeMap<Long, String>> nTemp : nSortedSquadPk.entrySet()) {
                ArrayList<String> textSquadResult = new ArrayList<>();
                ArrayList<String> textSquadResult1 = new ArrayList<>();
                ArrayList<String> textSquadResult2 = new ArrayList<>();
                Map<Long, String> nm = nTemp.getValue();
                TreeMap<Long, String> nnm = new TreeMap<>(nm);
                for (Map.Entry<Long, String> nTemp1 : nnm.entrySet()) {
                    String tSquadResult, tSquadResult1, tSquadResult2;
                    tSquadResult = nTemp1.getValue() + " = ";
                    tSquadResult1 = tSquadResult;
                    tSquadResult2 = tSquadResult;
                    tSquadResult1 = tSquadResult1 + nsPointPerMatch.get(nTemp1.getKey()) + (((nTemp1.getKey() - nSortedSquadSerial.get(nTemp1.getKey())) / 100 - nSortedSquadSerial.get(nTemp1.getKey())) / 10000) + " (" + (nSortedSquadKill.get(nTemp1.getKey()) / 20) + " Kill)";
                    tSquadResult2 = tSquadResult2 + (nSortedSquadKill.get(nTemp1.getKey())) + "+" + ((((nTemp1.getKey() - nSortedSquadSerial.get(nTemp1.getKey())) / 100 - nSortedSquadKill.get(nTemp1.getKey())) / 10000) - nSortedSquadKill.get(nTemp1.getKey())) + " = ";
                    tSquadResult = tSquadResult + ((((nTemp1.getKey() - nSortedSquadSerial.get(nTemp1.getKey())) / 100 - nSortedSquadKill.get(nTemp1.getKey())) / 10000) - nSortedSquadKill.get(nTemp1.getKey())) + "+" + (nSortedSquadKill.get(nTemp1.getKey())) + " = ";
                    tSquadResult2 = tSquadResult2 + (((nTemp1.getKey() - nSortedSquadSerial.get(nTemp1.getKey())) / 100 - nSortedSquadKill.get(nTemp1.getKey())) / 10000);
                    tSquadResult = tSquadResult + (((nTemp1.getKey() - nSortedSquadSerial.get(nTemp1.getKey())) / 100 - nSortedSquadKill.get(nTemp1.getKey())) / 10000);
                    tSquadResult2 = tSquadResult2 + " (" + (nSortedSquadKill.get(nTemp1.getKey()) / 20) + " Kill)";
                    tSquadResult = tSquadResult + " (" + (nSortedSquadKill.get(nTemp1.getKey()) / 20) + " Kill)";
                    textSquadResult.add(tSquadResult);
                    textSquadResult1.add(tSquadResult1);
                    textSquadResult2.add(tSquadResult2);
                }
                TextSquadResult.add(textSquadResult);
                TextSquadResult1.add(textSquadResult1);
                TextSquadResult2.add(textSquadResult2);
            }
            for (int i = TextSquadResult.size() - 1; i >= 0; i--) {
                ts = ts + iii + ". " + textGuildResult.get(ii) + "\n";
                ts1 = ts1 + iii + ". " + textGuildResult1.get(ii) + "\n";
                ts2 = ts2 + iii++ + ". " + textGuildResult2.get(ii--) + "\n";
                ArrayList<String> textSquadResult = new ArrayList<>(TextSquadResult.get(i));
                ArrayList<String> textSquadResult1 = new ArrayList<>(TextSquadResult1.get(i));
                ArrayList<String> textSquadResult2 = new ArrayList<>(TextSquadResult2.get(i));
                int iiii = 1;
                for (int x = textSquadResult.size() - 1; x >= 0; x--) {
                    ts = ts + "     " + iiii + ". " + textSquadResult.get(x) + "\n";
                    ts1 = ts1 + "     " + iiii + ". " + textSquadResult1.get(x) + "\n";
                    ts2 = ts2 + "     " + iiii++ + ". " + textSquadResult2.get(x) + "\n";
                }
                ts += "\n";
                ts1 += "\n";
                ts2 += "\n";
            }
            ts = ts + winner;
            ts1 = ts1 + winner;
            ts2 = ts2 + winner;
            TS = ts;
            TS1 = ts1;
            TS2 = ts2;
            textResult.setText(ts);
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else {
            return true;
        }
    }

    public void Back(View v) {
        super.onBackPressed();
    }

    public void Home(View v) {
        Intent intent = new Intent(Result.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
}