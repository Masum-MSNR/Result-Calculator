package com.project.resultcalculator.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.resultcalculator.R;

import java.util.ArrayList;

public class NameList extends AppCompatActivity {

    EditText[] guildNameEt = new EditText[12];
    EditText[] squadNameEt = new EditText[12];

    TextView title, tv;

    RelativeLayout prl;
    LinearLayout ll;

    String type;

    Button nextBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_name_list);

        //EditText
        guildNameEt[0] = findViewById(R.id.guildNameEt1);
        guildNameEt[1] = findViewById(R.id.guildNameEt2);
        guildNameEt[2] = findViewById(R.id.guildNameEt3);
        guildNameEt[3] = findViewById(R.id.guildNameEt4);
        guildNameEt[4] = findViewById(R.id.guildNameEt5);
        guildNameEt[5] = findViewById(R.id.guildNameEt6);
        guildNameEt[6] = findViewById(R.id.guildNameEt7);
        guildNameEt[7] = findViewById(R.id.guildNameEt8);
        guildNameEt[8] = findViewById(R.id.guildNameEt9);
        guildNameEt[9] = findViewById(R.id.guildNameEt10);
        guildNameEt[10] = findViewById(R.id.guildNameEt11);
        guildNameEt[11] = findViewById(R.id.guildNameEt12);

        squadNameEt[0] = findViewById(R.id.squadNameEt1);
        squadNameEt[1] = findViewById(R.id.squadNameEt2);
        squadNameEt[2] = findViewById(R.id.squadNameEt3);
        squadNameEt[3] = findViewById(R.id.squadNameEt4);
        squadNameEt[4] = findViewById(R.id.squadNameEt5);
        squadNameEt[5] = findViewById(R.id.squadNameEt6);
        squadNameEt[6] = findViewById(R.id.squadNameEt7);
        squadNameEt[7] = findViewById(R.id.squadNameEt8);
        squadNameEt[8] = findViewById(R.id.squadNameEt9);
        squadNameEt[9] = findViewById(R.id.squadNameEt10);
        squadNameEt[10] = findViewById(R.id.squadNameEt11);
        squadNameEt[11] = findViewById(R.id.squadNameEt12);

        //TextView
        title = findViewById(R.id.title);
        tv = findViewById(R.id.tv);

        //RelativeLayout
        prl = findViewById(R.id.prl);

        //LinearLayout
        ll = findViewById(R.id.ll);

        //Button
        nextBt = findViewById(R.id.next);

        //Intent
        Intent intent = getIntent();
        type = intent.getStringExtra("Type");


        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(type)) {
                    textSetter();
                }
            }
        });
        prl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        structureMaker(type);
    }

    private boolean isValid(String t) {
        if (t.equals("12")) {
            int x = 0;
            for (int i = 0; i < 12; i++) {
                if (!guildNameEt[i].getText().toString().isEmpty()) {
                    x++;
                }
            }
            if (x < 3) {
                Toast.makeText(getApplicationContext(), "Minimum Three Guild Required :-(", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (t.equals("6")) {
            int x = 0, j = 0;
            for (int i = 0; i < 12; i += 2) {
                if (!guildNameEt[i].getText().toString().isEmpty()) {
                    x++;
                    String temps = guildNameEt[i].getText().toString();
                    int y = 0;
                    for (int k = 0; k < 2; k++) {
                        if (!squadNameEt[j].getText().toString().isEmpty()) {
                            y++;
                        }
                        j++;
                    }
                    if (y == 0) {
                        Toast.makeText(getApplicationContext(), "Minimum One Squad Required for Guild [" + temps + "] :-(", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    j += 2;
                }
            }
            if (x < 3) {
                Toast.makeText(getApplicationContext(), "Minimum Three Guild Required :-(", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (type.equals("4")) {
            int x = 0, j = 0;
            for (int i = 0; i < 12; i += 3) {
                if (!guildNameEt[i].getText().toString().isEmpty()) {
                    x++;
                    String temps = guildNameEt[i].getText().toString();
                    int y = 0;
                    for (int k = 0; k < 3; k++) {
                        if (!squadNameEt[j].getText().toString().isEmpty()) {
                            y++;
                        }
                        j++;
                    }
                    if (y == 0) {
                        Toast.makeText(getApplicationContext(), "Minimum One Squad Required for Guild [" + temps + "] :-(", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    j += 3;
                }
            }
            if (x < 3) {
                Toast.makeText(getApplicationContext(), "Minimum Three Guild Required :-(", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (type.equals("3")) {
            int x = 0, j = 0;
            for (int i = 0; i < 12; i += 4) {
                if (!guildNameEt[i].getText().toString().isEmpty()) {
                    x++;
                    String temps = guildNameEt[i].getText().toString();
                    int y = 0;
                    for (int k = 0; k < 4; k++) {
                        if (!squadNameEt[j].getText().toString().isEmpty()) {
                            y++;
                        }
                        j++;
                    }
                    if (y == 0) {
                        Toast.makeText(getApplicationContext(), "Minimum One Squad Required for Guild [" + temps + "] :-(", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    j += 4;
                }
            }
            if (x < 2) {
                Toast.makeText(getApplicationContext(), "Minimum Two Guild Required :-(", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        } else if (type.equals("2")) {
            int x = 0, j = 0;
            for (int i = 0; i < 12; i += 6) {
                if (!guildNameEt[i].getText().toString().isEmpty()) {
                    x++;
                    String temps = guildNameEt[i].getText().toString();
                    int y = 0;
                    for (int k = 0; k < 6; k++) {
                        if (!squadNameEt[j].getText().toString().isEmpty()) {
                            y++;
                        }
                        j++;
                    }
                    if (y < 2) {
                        Toast.makeText(getApplicationContext(), "Minimum Two Squad Required for Guild [" + temps + "] :-(", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    j += 6;
                }
            }
            if (x < 2) {
                Toast.makeText(getApplicationContext(), "Minimum Two Guild Required :-(", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void structureMaker(String type) {
        switch (type) {
            case "12":
                tv.setText(R.string.guild_war_12);
                title.setText(R.string.enter_guild_name);
                for (int i = 0; i < 12; i++) {
                    squadNameEt[i].setVisibility(View.GONE);
                }
                break;
            case "6": {
                tv.setText(R.string.guild_war_6);
                title.setText(R.string.enter_guild_and_squad_name);
                int flag = 0;
                int j = 1;
                for (int i = 1; i < 12; i += 2) {
                    guildNameEt[i].setVisibility(View.GONE);
                    guildNameEt[i - 1].setHint("Guild-" + j);
                    j++;
                }
                for (int i = 0; i < 12; i++) {
                    if (flag == 0) {
                        squadNameEt[i].setHint("Squad-1           ");
                        flag = 1;
                    } else {
                        squadNameEt[i].setHint("Squad-2           ");
                        flag = 0;
                    }
                }
                break;
            }
            case "4": {
                tv.setText(R.string.guild_war_4);
                title.setText(R.string.enter_guild_and_squad_name);
                int flag = 0;
                int j = 1;
                for (int i = 1; i < 12; i += 3) {
                    guildNameEt[i].setVisibility(View.GONE);
                    guildNameEt[i + 1].setVisibility(View.GONE);
                    guildNameEt[i - 1].setHint("Guild-" + j);
                    j++;
                }
                for (int i = 0; i < 12; i++) {
                    if (flag == 0) {
                        squadNameEt[i].setHint("Squad-1           ");
                        flag = 1;
                    } else if (flag == 1) {
                        squadNameEt[i].setHint("Squad-2           ");
                        flag = 2;
                    } else {
                        squadNameEt[i].setHint("Squad-3           ");
                        flag = 0;
                    }
                }
                break;
            }
            case "3": {
                tv.setText(R.string.guild_war_3);
                title.setText(R.string.enter_guild_and_squad_name);
                int flag = 0;
                int j = 1;
                for (int i = 1; i < 12; i += 4) {
                    guildNameEt[i].setVisibility(View.GONE);
                    guildNameEt[i + 1].setVisibility(View.GONE);
                    guildNameEt[i + 2].setVisibility(View.GONE);
                    guildNameEt[i - 1].setHint("Guild-" + j);
                    j++;
                }
                for (int i = 0; i < 12; i++) {
                    if (flag == 0) {
                        squadNameEt[i].setHint("Squad-1           ");
                        flag = 1;
                    } else if (flag == 1) {
                        squadNameEt[i].setHint("Squad-2           ");
                        flag = 2;
                    } else if (flag == 2) {
                        squadNameEt[i].setHint("Squad-3           ");
                        flag = 3;
                    } else {
                        squadNameEt[i].setHint("Squad-4           ");
                        flag = 0;
                    }
                }
                break;
            }
            case "2": {
                tv.setText(R.string.guild_war_2);
                title.setText(R.string.enter_guild_and_squad_name);
                int flag = 0;
                int j = 1;
                for (int i = 1; i < 12; i += 6) {
                    guildNameEt[i].setVisibility(View.GONE);
                    guildNameEt[i + 1].setVisibility(View.GONE);
                    guildNameEt[i + 2].setVisibility(View.GONE);
                    guildNameEt[i + 3].setVisibility(View.GONE);
                    guildNameEt[i + 4].setVisibility(View.GONE);
                    guildNameEt[i - 1].setHint("Guild-" + j);
                    j++;
                }
                for (int i = 0; i < 12; i++) {
                    if (flag == 0) {
                        squadNameEt[i].setHint("Squad-1           ");
                        flag = 1;
                    } else if (flag == 1) {
                        squadNameEt[i].setHint("Squad-2           ");
                        flag = 2;
                    } else if (flag == 2) {
                        squadNameEt[i].setHint("Squad-3           ");
                        flag = 3;
                    } else if (flag == 3) {
                        squadNameEt[i].setHint("Squad-4           ");
                        flag = 4;
                    } else if (flag == 4) {
                        squadNameEt[i].setHint("Squad-5           ");
                        flag = 5;
                    } else {
                        squadNameEt[i].setHint("Squad-6           ");
                        flag = 0;
                    }
                }
                break;
            }
        }
    }

    private void textSetter() {
        switch (type) {
            case "12": {
                ArrayList<String> guildName = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    if (!guildNameEt[i].getText().toString().isEmpty()) {
                        guildName.add(guildNameEt[i].getText().toString());
                    }
                }
                Intent intent = new Intent(NameList.this, PutDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("GuildName", guildName);
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
            }
            case "6": {
                ArrayList<String> guildName = new ArrayList<>();
                ArrayList<String> squadName = new ArrayList<>();
                int[] squadNumber = new int[6];
                int j = 0, m = 0;
                for (int i = 0; i < 12; i += 2) {
                    if (!guildNameEt[i].getText().toString().isEmpty()) {
                        guildName.add(guildNameEt[i].getText().toString());
                        int l = 0;
                        for (int k = 0; k < 2; k++) {
                            if (!squadNameEt[j].getText().toString().isEmpty()) {
                                squadName.add(squadNameEt[j].getText().toString());
                                l++;
                            }
                            j++;
                        }
                        squadNumber[m] = l;
                        m++;
                    } else {
                        j += 2;
                    }
                }
                Intent intent = new Intent(NameList.this, PutDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("GuildName", guildName);
                intent.putExtra("SquadName", squadName);
                intent.putExtra("SquadNumber", squadNumber);
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
            }
            case "4": {
                ArrayList<String> guildName = new ArrayList<>();
                ArrayList<String> squadName = new ArrayList<>();
                int j = 0, m = 0;
                int[] squadNumber = new int[6];
                for (int i = 0; i < 12; i += 3) {
                    if (!guildNameEt[i].getText().toString().isEmpty()) {
                        guildName.add(guildNameEt[i].getText().toString());
                        int l = 0;
                        for (int k = 0; k < 3; k++) {
                            if (!squadNameEt[j].getText().toString().isEmpty()) {
                                squadName.add(squadNameEt[j].getText().toString());
                                l++;
                            }
                            j++;
                        }
                        squadNumber[m] = l;
                        m++;
                    } else {
                        j += 3;
                    }
                }
                Intent intent = new Intent(NameList.this, PutDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("GuildName", guildName);
                intent.putExtra("SquadName", squadName);
                intent.putExtra("SquadNumber", squadNumber);
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
            }
            case "3": {
                ArrayList<String> guildName = new ArrayList<>();
                ArrayList<String> squadName = new ArrayList<>();
                int j = 0, m = 0;
                int[] squadNumber = new int[6];
                for (int i = 0; i < 12; i += 4) {
                    if (!guildNameEt[i].getText().toString().isEmpty()) {
                        guildName.add(guildNameEt[i].getText().toString());
                        int l = 0;
                        for (int k = 0; k < 4; k++) {
                            if (!squadNameEt[j].getText().toString().isEmpty()) {
                                squadName.add(squadNameEt[j].getText().toString());
                                l++;
                            }
                            j++;
                        }
                        squadNumber[m] = l;
                        m++;
                    } else {
                        j += 4;
                    }
                }
                Intent intent = new Intent(NameList.this, PutDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("GuildName", guildName);
                intent.putExtra("SquadName", squadName);
                intent.putExtra("SquadNumber", squadNumber);
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
            }
            case "2": {
                ArrayList<String> guildName = new ArrayList<>();
                ArrayList<String> squadName = new ArrayList<>();
                int j = 0, m = 0;
                int[] squadNumber = new int[6];
                for (int i = 0; i < 12; i += 6) {
                    if (!guildNameEt[i].getText().toString().isEmpty()) {
                        guildName.add(guildNameEt[i].getText().toString());
                        int l = 0;
                        for (int k = 0; k < 6; k++) {
                            if (!squadNameEt[j].getText().toString().isEmpty()) {
                                squadName.add(squadNameEt[j].getText().toString());
                                l++;
                            }
                            j++;
                        }
                        squadNumber[m] = l;
                        m++;
                    } else {
                        j += 6;
                    }
                }
                Intent intent = new Intent(NameList.this, PutDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("GuildName", guildName);
                intent.putExtra("SquadName", squadName);
                intent.putExtra("SquadNumber", squadNumber);
                intent.putExtra("Type", type);
                startActivity(intent);
                break;
            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(prl.getWindowToken(), 0);
        for (int i = 0; i < 12; i++) {
            guildNameEt[i].clearFocus();
            squadNameEt[i].clearFocus();
        }
    }

    public void Back(View v) {
        super.onBackPressed();
    }

    public void Home(View v) {
        Intent intent = new Intent(NameList.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}