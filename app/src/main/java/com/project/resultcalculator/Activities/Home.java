package com.project.resultcalculator.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.resultcalculator.Classes.AboutDialog;
import com.project.resultcalculator.Classes.FeedbackDialog;
import com.project.resultcalculator.Classes.UpdatesDialog;
import com.project.resultcalculator.R;

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    CardView cardView1, cardView2;

    String details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_home);
        if(isConnected()){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Updates");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String[] updates = new String[3];
                    if (dataSnapshot.exists()) {
                        int i = 0;
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            updates[i++] = dataSnapshot1.getValue().toString();
                        }
                        String pVersion = "1.0.3";
                        if (!updates[1].equals(pVersion)) {
                            details = "A new version is available. Would you like to update now?";
                            UpdatesDialog updatesDialog = new UpdatesDialog(Home.this, details, updates[1], updates[0],updates[2], 2);
                            updatesDialog.setCancelable(false);
                            updatesDialog.show(getSupportFragmentManager(), null);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //CardView
        cardView1 = findViewById(R.id.cv1);
        cardView2 = findViewById(R.id.cv2);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, GuildWar.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Tournament.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }


    public void ShowPopUpMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.three_line);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                AboutDialog aboutDialog = new AboutDialog();
                aboutDialog.setCancelable(false);
                aboutDialog.show(getSupportFragmentManager(), null);
                return true;
            case R.id.check_for_updates:
                if (isConnected()) {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_bar_cfu);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Updates");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String[] updates = new String[3];
                            if (dataSnapshot.exists()) {
                                int i = 0;
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    updates[i++] = dataSnapshot1.getValue().toString();
                                }
                                String pVersion = "1.0.3";
                                if (!updates[1].equals(pVersion)) {
                                    details = "A new version is available. Would you like to update now?";
                                    progressDialog.dismiss();
                                    UpdatesDialog updatesDialog = new UpdatesDialog(Home.this, details, updates[1], updates[0], 1);
                                    updatesDialog.setCancelable(false);
                                    updatesDialog.show(getSupportFragmentManager(), null);
                                } else {
                                    progressDialog.dismiss();
                                    details = "Your are using latest version of this app.";
                                    UpdatesDialog updatesDialog = new UpdatesDialog(Home.this,details,0);
                                    updatesDialog.setCancelable(false);
                                    updatesDialog.show(getSupportFragmentManager(), null);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
                } else {
                    Toast.makeText(Home.this, "Network Error", Toast.LENGTH_SHORT).show();
                    return false;
                }
            case R.id.feedback:
                if (isConnected()) {
                    FeedbackDialog feedbackDialog = new FeedbackDialog();
                    feedbackDialog.setCancelable(false);
                    feedbackDialog.show(getSupportFragmentManager(), null);
                    return true;
                } else {
                    Toast.makeText(Home.this, "Network Error", Toast.LENGTH_SHORT).show();
                    return false;
                }
            case R.id.how_to_use:
                if (isConnected()) {
                    final ProgressDialog progressDialog1 = new ProgressDialog(this, R.style.StyledDialog);
                    progressDialog1.setCancelable(false);
                    progressDialog1.show();
                    progressDialog1.setContentView(R.layout.progress_bar_htu);
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("How To Use");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            progressDialog1.dismiss();
                            String url = dataSnapshot.getValue().toString();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    return true;
                } else {
                    Toast.makeText(Home.this, "Network Error", Toast.LENGTH_SHORT).show();
                    return false;
                }
            default:
                return false;
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public void vs(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://variableshops.com/"));
        startActivity(intent);
    }
}