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
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeMap;

public class PutDetails extends AppCompatActivity {

    TextView[] guildNameTv = new TextView[12];
    TextView[] squadNameTv = new TextView[12];
    TextView matchNoTv;

    EditText[] positionEt = new EditText[12];
    EditText[] killEt = new EditText[12];

    Button calculateBt, resultBt, nextMatchBt;

    long[] guildPointSum = new long[12];
    long[] squadPointSum = new long[12];
    long[] killPoint = new long[12];

    int[] position = new int[12];
    int[] squadNumber = new int[6];
    int flag = 0, mn = 2, n = 0, gFlag = 0;

    ArrayList<String> guildName;
    ArrayList<String> squadName;

    RelativeLayout prl;
    LinearLayout ll;

    Intent intent;

    String[] pointPerMatch = new String[12];
    String[] sPointPerMatch = new String[12];
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_put_details);

        //TextView
        guildNameTv[0] = findViewById(R.id.guildName1);
        guildNameTv[1] = findViewById(R.id.guildName2);
        guildNameTv[2] = findViewById(R.id.guildName3);
        guildNameTv[3] = findViewById(R.id.guildName4);
        guildNameTv[4] = findViewById(R.id.guildName5);
        guildNameTv[5] = findViewById(R.id.guildName6);
        guildNameTv[6] = findViewById(R.id.guildName7);
        guildNameTv[7] = findViewById(R.id.guildName8);
        guildNameTv[8] = findViewById(R.id.guildName9);
        guildNameTv[9] = findViewById(R.id.guildName10);
        guildNameTv[10] = findViewById(R.id.guildName11);
        guildNameTv[11] = findViewById(R.id.guildName12);

        squadNameTv[0] = findViewById(R.id.squadName1);
        squadNameTv[1] = findViewById(R.id.squadName2);
        squadNameTv[2] = findViewById(R.id.squadName3);
        squadNameTv[3] = findViewById(R.id.squadName4);
        squadNameTv[4] = findViewById(R.id.squadName5);
        squadNameTv[5] = findViewById(R.id.squadName6);
        squadNameTv[6] = findViewById(R.id.squadName7);
        squadNameTv[7] = findViewById(R.id.squadName8);
        squadNameTv[8] = findViewById(R.id.squadName9);
        squadNameTv[9] = findViewById(R.id.squadName10);
        squadNameTv[10] = findViewById(R.id.squadName11);
        squadNameTv[11] = findViewById(R.id.squadName12);

        matchNoTv = findViewById(R.id.matchNo);

        //EditText
        positionEt[0] = findViewById(R.id.positionEt1);
        positionEt[1] = findViewById(R.id.positionEt2);
        positionEt[2] = findViewById(R.id.positionEt3);
        positionEt[3] = findViewById(R.id.positionEt4);
        positionEt[4] = findViewById(R.id.positionEt5);
        positionEt[5] = findViewById(R.id.positionEt6);
        positionEt[6] = findViewById(R.id.positionEt7);
        positionEt[7] = findViewById(R.id.positionEt8);
        positionEt[8] = findViewById(R.id.positionEt9);
        positionEt[9] = findViewById(R.id.positionEt10);
        positionEt[10] = findViewById(R.id.positionEt11);
        positionEt[11] = findViewById(R.id.positionEt12);

        killEt[0] = findViewById(R.id.killEt1);
        killEt[1] = findViewById(R.id.killEt2);
        killEt[2] = findViewById(R.id.killEt3);
        killEt[3] = findViewById(R.id.killEt4);
        killEt[4] = findViewById(R.id.killEt5);
        killEt[5] = findViewById(R.id.killEt6);
        killEt[6] = findViewById(R.id.killEt7);
        killEt[7] = findViewById(R.id.killEt8);
        killEt[8] = findViewById(R.id.killEt9);
        killEt[9] = findViewById(R.id.killEt10);
        killEt[10] = findViewById(R.id.killEt11);
        killEt[11] = findViewById(R.id.killEt12);

        //Button
        calculateBt = findViewById(R.id.caclulate);
        resultBt = findViewById(R.id.result);
        nextMatchBt = findViewById(R.id.nextMatch);

        //RelativeLayout
        prl = findViewById(R.id.prl);

        //LinearLayout
        ll = findViewById(R.id.ll);

        //Intent
        intent = getIntent();
        type = intent.getStringExtra("Type");

        //Array
        Arrays.fill(guildPointSum, 0);
        Arrays.fill(squadPointSum, 0);
        Arrays.fill(killPoint, 0);
        Arrays.fill(pointPerMatch, "  ");
        Arrays.fill(sPointPerMatch, "  ");

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

        calculateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(type);
            }
        });

        resultBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultMaker(type);
            }
        });

        nextMatchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag != 0) {
                    flag = 0;
                    calculateBt.setClickable(true);
                    String s = "Match-" + mn;
                    matchNoTv.setText(s);
                    for (int i = 0; i < 12; i++) {
                        positionEt[i].setText("");
                        killEt[i].setText("");
                        positionEt[i].setEnabled(true);
                        killEt[i].setEnabled(true);
                    }
                    mn++;
                    s = "Match-" + mn;
                    nextMatchBt.setText(s);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Calculate This Match First..!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void calculate(String type) {
        if (isValid(type)) {
            switch (type) {
                case "12":
                    if (flag == 0) {
                        gFlag++;
                        flag = 1;
                        for (int i = 0; i < n; i++) {
                            guildPointSum[i] += getPositionPoint(positionEt[i].getText().toString()) + getKillPoint(killEt[i].getText().toString());
                            killPoint[i] += getKillPoint(killEt[i].getText().toString());
                            pointPerMatch[i] += (getPositionPoint(positionEt[i].getText().toString()) + getKillPoint(killEt[i].getText().toString())) + "+";
                            positionEt[i].setEnabled(false);
                            killEt[i].setEnabled(false);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Calculation Complete for This Match..!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "6":
                    if (flag == 0) {
                        gFlag++;
                        flag = 1;
                        int j = 0, k = 0, kk = 0;
                        for (int i = 0; i < n * 2; i += 2) {
                            for (int l = 0; l < squadNumber[j]; l++) {
                                squadPointSum[k] += getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString());
                                sPointPerMatch[k] += (getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString())) + "+";
                                killPoint[k] += getKillPoint(killEt[k].getText().toString());
                                positionEt[k].setEnabled(false);
                                killEt[k].setEnabled(false);
                                k++;
                            }
                            long tempGuildSome = 0L;
                            for (int l = 0; l < squadNumber[j]; l++) {
                                tempGuildSome += getPositionPoint(positionEt[kk].getText().toString()) + getKillPoint(killEt[kk].getText().toString());
                                kk++;
                            }
                            pointPerMatch[j] += (tempGuildSome) + "+";
                            if (squadNumber[j] == 1) {
                                k++;
                                kk++;
                            } else if (squadNumber[j] == 0) {
                                k += 2;
                                kk += 2;
                            }
                            j++;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Calculation Complete for This Match..!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "4":
                    if (flag == 0) {
                        gFlag++;
                        flag = 1;
                        int j = 0, k = 0, kk = 0;
                        for (int i = 0; i < n * 3; i += 3) {
                            for (int l = 0; l < squadNumber[j]; l++) {
                                squadPointSum[k] += getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString());
                                sPointPerMatch[k] += (getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString())) + "+";
                                killPoint[k] += getKillPoint(killEt[k].getText().toString());
                                positionEt[k].setEnabled(false);
                                killEt[k].setEnabled(false);
                                k++;
                            }
                            long tempGuildSome = 0L;
                            for (int l = 0; l < squadNumber[j]; l++) {
                                tempGuildSome += getPositionPoint(positionEt[kk].getText().toString()) + getKillPoint(killEt[kk].getText().toString());
                                kk++;
                            }
                            pointPerMatch[j] += (tempGuildSome) + "+";
                            if (squadNumber[j] == 2) {
                                k++;
                                kk++;
                            } else if (squadNumber[j] == 1) {
                                k += 2;
                                kk += 2;
                            } else if (squadNumber[j] == 0) {
                                k += 3;
                                kk += 3;
                            }
                            j++;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Calculation Complete for This Match..!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "3":
                    if (flag == 0) {
                        gFlag++;
                        flag = 1;
                        int j = 0, k = 0, kk = 0;
                        for (int i = 0; i < n * 4; i += 4) {
                            for (int l = 0; l < squadNumber[j]; l++) {
                                squadPointSum[k] += getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString());
                                killPoint[k] += getKillPoint(killEt[k].getText().toString());
                                sPointPerMatch[k] += (getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString())) + "+";
                                positionEt[k].setEnabled(false);
                                killEt[k].setEnabled(false);
                                k++;
                            }
                            long tempGuildSome = 0L;
                            for (int l = 0; l < squadNumber[j]; l++) {
                                tempGuildSome += getPositionPoint(positionEt[kk].getText().toString()) + getKillPoint(killEt[kk].getText().toString());
                                kk++;
                            }
                            pointPerMatch[j] += (tempGuildSome) + "+";
                            if (squadNumber[j] == 3) {
                                k++;
                                kk++;
                            } else if (squadNumber[j] == 2) {
                                k += 2;
                                kk += 2;
                            } else if (squadNumber[j] == 1) {
                                k += 3;
                                kk += 3;
                            } else if (squadNumber[j] == 0) {
                                k += 4;
                                kk += 4;
                            }
                            j++;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Calculation Complete for This Match..!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case "2":
                    if (flag == 0) {
                        gFlag++;
                        flag = 1;
                        int j = 0, k = 0, kk = 0;
                        for (int i = 0; i < n * 6; i += 6) {
                            for (int l = 0; l < squadNumber[j]; l++) {
                                squadPointSum[k] += getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString());
                                killPoint[k] += getKillPoint(killEt[k].getText().toString());
                                sPointPerMatch[k] += (getPositionPoint(positionEt[k].getText().toString()) + getKillPoint(killEt[k].getText().toString())) + "+";
                                positionEt[k].setEnabled(false);
                                killEt[k].setEnabled(false);
                                k++;
                            }
                            long tempGuildSome = 0L;
                            for (int l = 0; l < squadNumber[j]; l++) {
                                tempGuildSome += getPositionPoint(positionEt[kk].getText().toString()) + getKillPoint(killEt[kk].getText().toString());
                                kk++;
                            }
                            pointPerMatch[j] += (tempGuildSome) + "+";
                            if (squadNumber[j] == 5) {
                                k++;
                                kk++;
                            } else if (squadNumber[j] == 4) {
                                k += 2;
                                k += 2;
                            } else if (squadNumber[j] == 3) {
                                k += 3;
                                k += 3;
                            } else if (squadNumber[j] == 2) {
                                k += 4;
                                k += 4;
                            } else if (squadNumber[j] == 1) {
                                k += 5;
                                k += 5;
                            } else if (squadNumber[j] == 0) {
                                k += 6;
                                k += 6;
                            }
                            j++;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Calculation Complete for This Match..!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private void resultMaker(String type) {
        switch (type) {
            case "12": {
                TreeMap<Long, String> guildResult = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> killP = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> serial = new TreeMap<>(Collections.<Long>reverseOrder());
                for (int i = 0; i < n; i++) {
                    long tempSum = guildPointSum[i] * 10000;
                    String tempPointPerMatch;
                    if (gFlag > 1) {
                        tempPointPerMatch = pointPerMatch[i].substring(2, pointPerMatch[i].length() - 1);
                        tempPointPerMatch += " = ";
                    } else {
                        tempPointPerMatch = "";
                    }
                    guildResult.put(((tempSum + killPoint[i]) * 100) + i, guildName.get(i));
                    tPointPerMatch.put(((tempSum + killPoint[i]) * 100) + i, tempPointPerMatch);
                    killP.put(((tempSum + killPoint[i]) * 100) + i, killPoint[i]);
                    serial.put(((tempSum + killPoint[i]) * 100) + i, (long) i);
                }
                Intent intent1 = new Intent(PutDetails.this, Result.class);
                intent1.putExtra("PointPerMatch", tPointPerMatch);
                intent1.putExtra("GuildResult", guildResult);
                intent1.putExtra("KillP", killP);
                intent1.putExtra("Serial", serial);
                intent1.putExtra("Type", type);
                startActivity(intent1);
                break;
            }
            case "6": {
                TreeMap<Long, String> sortedGuildName = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tsPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, TreeMap<Long, String>> sortedSquadPk = new TreeMap<>(Collections.<Long>reverseOrder());
                int j = 0, k = 0, m = 0;
                long guildSum = 0L, killSum = 0L;
                for (int i = 0; i < n * 2; i += 2) {
                    TreeMap<Long, String> squadResult = new TreeMap<>(Collections.<Long>reverseOrder());
                    for (int l = 0; l < squadNumber[j]; l++) {
                        guildSum += squadPointSum[k];
                        killSum += killPoint[k];
                        long tempSum = squadPointSum[k] * 10000;
                        String tempPointPerMatch;
                        if (gFlag > 1) {
                            tempPointPerMatch = sPointPerMatch[k].substring(2, sPointPerMatch[k].length() - 1);
                            tempPointPerMatch += " = ";
                        } else {
                            tempPointPerMatch = "";
                        }
                        tsPointPerMatch.put(((tempSum + killPoint[k]) * 100) + l + i, tempPointPerMatch);
                        squadResult.put(((tempSum + killPoint[k]) * 100) + l + i, squadName.get(m));
                        sortedSquadKill.put(((tempSum + killPoint[k]) * 100) + l + i, killPoint[k]);
                        sortedSquadSerial.put(((tempSum + killPoint[k]) * 100) + l + i, (long) (l + i));
                        m++;
                        k++;
                    }
                    long tempSum = guildSum * 10000;
                    String tempPointPerMatch;
                    if (gFlag > 1) {
                        tempPointPerMatch = pointPerMatch[j].substring(2, pointPerMatch[j].length() - 1);
                        tempPointPerMatch += " = ";
                    } else {
                        tempPointPerMatch = "";
                    }
                    tPointPerMatch.put(((tempSum + killSum) * 100) + i, tempPointPerMatch);
                    sortedGuildName.put(((tempSum + killSum) * 100) + i, guildName.get(j));
                    sortedGuildKill.put(((tempSum + killSum) * 100) + i, killSum);
                    sortedSquadPk.put(((tempSum + killSum) * 100) + i, squadResult);
                    sortedGuildSerial.put(((tempSum + killSum) * 100) + i, (long) i);
                    guildSum = 0L;
                    killSum = 0L;
                    if (squadNumber[j] == 1) {
                        k++;
                    } else if (squadNumber[j] == 0) {
                        k += 2;
                    }
                    j++;
                }
                Intent intent1 = new Intent(PutDetails.this, Result.class);
                intent1.putExtra("PointPerMatch", tPointPerMatch);
                intent1.putExtra("sPointPerMatch", tsPointPerMatch);
                intent1.putExtra("SortedGuildName", sortedGuildName);
                intent1.putExtra("SortedGuildKill", sortedGuildKill);
                intent1.putExtra("SortedSquadPk", sortedSquadPk);
                intent1.putExtra("SortedSquadKill", sortedSquadKill);
                intent1.putExtra("SortedGuildSerial", sortedGuildSerial);
                intent1.putExtra("SortedSquadSerial", sortedSquadSerial);
                intent1.putExtra("Type", type);
                startActivity(intent1);
                break;
            }
            case "4": {
                TreeMap<Long, String> sortedGuildName = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tsPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, TreeMap<Long, String>> sortedSquadPk = new TreeMap<>(Collections.<Long>reverseOrder());
                int j = 0, k = 0, m = 0;
                long guildSum = 0L, killSum = 0L;
                for (int i = 0; i < n * 3; i += 3) {
                    TreeMap<Long, String> squadResult = new TreeMap<>(Collections.<Long>reverseOrder());
                    for (int l = 0; l < squadNumber[j]; l++) {
                        guildSum += squadPointSum[k];
                        killSum += killPoint[k];
                        long tempSum = squadPointSum[k] * 10000;
                        String tempPointPerMatch;
                        if (gFlag > 1) {
                            tempPointPerMatch = sPointPerMatch[k].substring(2, sPointPerMatch[k].length() - 1);
                            tempPointPerMatch += " = ";
                        } else {
                            tempPointPerMatch = "";
                        }
                        tsPointPerMatch.put(((tempSum + killPoint[k]) * 100) + l + i, tempPointPerMatch);
                        squadResult.put(((tempSum + killPoint[k]) * 100) + l + i, squadName.get(m));
                        sortedSquadKill.put(((tempSum + killPoint[k]) * 100) + l + i, killPoint[k]);
                        sortedSquadSerial.put(((tempSum + killPoint[k]) * 100) + l + i, (long) (l + i));
                        m++;
                        k++;
                    }
                    long tempSum = guildSum * 10000;
                    String tempPointPerMatch;
                    if (gFlag > 1) {
                        tempPointPerMatch = pointPerMatch[j].substring(2, pointPerMatch[j].length() - 1);
                        tempPointPerMatch += " = ";
                    } else {
                        tempPointPerMatch = "";
                    }
                    tPointPerMatch.put(((tempSum + killSum) * 100) + i, tempPointPerMatch);
                    sortedGuildName.put(((tempSum + killSum) * 100) + i, guildName.get(j));
                    sortedGuildKill.put(((tempSum + killSum) * 100) + i, killSum);
                    sortedSquadPk.put(((tempSum + killSum) * 100) + i, squadResult);
                    sortedGuildSerial.put(((tempSum + killSum) * 100) + i, (long) i);
                    guildSum = 0L;
                    killSum = 0L;
                    if (squadNumber[j] == 2) {
                        k++;
                    } else if (squadNumber[j] == 1) {
                        k += 2;
                    } else if (squadNumber[j] == 0) {
                        k += 3;
                    }
                    j++;
                }
                Intent intent1 = new Intent(PutDetails.this, Result.class);
                intent1.putExtra("PointPerMatch", tPointPerMatch);
                intent1.putExtra("sPointPerMatch", tsPointPerMatch);
                intent1.putExtra("SortedGuildName", sortedGuildName);
                intent1.putExtra("SortedGuildKill", sortedGuildKill);
                intent1.putExtra("SortedSquadPk", sortedSquadPk);
                intent1.putExtra("SortedSquadKill", sortedSquadKill);
                intent1.putExtra("SortedGuildSerial", sortedGuildSerial);
                intent1.putExtra("SortedSquadSerial", sortedSquadSerial);
                intent1.putExtra("Type", type);
                startActivity(intent1);
                break;
            }
            case "3": {
                TreeMap<Long, String> sortedGuildName = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tsPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, TreeMap<Long, String>> sortedSquadPk = new TreeMap<>(Collections.<Long>reverseOrder());
                int j = 0, k = 0, m = 0;
                long guildSum = 0L, killSum = 0L;
                for (int i = 0; i < n * 4; i += 4) {
                    TreeMap<Long, String> squadResult = new TreeMap<>(Collections.<Long>reverseOrder());
                    for (int l = 0; l < squadNumber[j]; l++) {
                        guildSum += squadPointSum[k];
                        killSum += killPoint[k];
                        long tempSum = squadPointSum[k] * 10000;
                        String tempPointPerMatch;
                        if (gFlag > 1) {
                            tempPointPerMatch = sPointPerMatch[k].substring(2, sPointPerMatch[k].length() - 1);
                            tempPointPerMatch += " = ";
                        } else {
                            tempPointPerMatch = "";
                        }
                        tsPointPerMatch.put(((tempSum + killPoint[k]) * 100) + l + i, tempPointPerMatch);
                        squadResult.put(((tempSum + killPoint[k]) * 100) + l + i, squadName.get(m));
                        sortedSquadKill.put(((tempSum + killPoint[k]) * 100) + l + i, killPoint[k]);
                        sortedSquadSerial.put(((tempSum + killPoint[k]) * 100) + l + i, (long) (l + i));
                        m++;
                        k++;
                    }
                    long tempSum = guildSum * 10000;
                    String tempPointPerMatch;
                    if (gFlag > 1) {
                        tempPointPerMatch = pointPerMatch[j].substring(2, pointPerMatch[j].length() - 1);
                        tempPointPerMatch += " = ";
                    } else {
                        tempPointPerMatch = "";
                    }
                    tPointPerMatch.put(((tempSum + killSum) * 100) + i, tempPointPerMatch);
                    sortedGuildName.put(((tempSum + killSum) * 100) + i, guildName.get(j));
                    sortedGuildKill.put(((tempSum + killSum) * 100) + i, killSum);
                    sortedSquadPk.put(((tempSum + killSum) * 100) + i, squadResult);
                    sortedGuildSerial.put(((tempSum + killSum) * 100) + i, (long) i);
                    guildSum = 0L;
                    killSum = 0L;
                    if (squadNumber[j] == 3) {
                        k++;
                    } else if (squadNumber[j] == 2) {
                        k += 2;
                    } else if (squadNumber[j] == 1) {
                        k += 3;
                    } else if (squadNumber[j] == 0) {
                        k += 0;
                    }
                    j++;
                }
                Intent intent1 = new Intent(PutDetails.this, Result.class);
                intent1.putExtra("PointPerMatch", tPointPerMatch);
                intent1.putExtra("sPointPerMatch", tsPointPerMatch);
                intent1.putExtra("SortedGuildName", sortedGuildName);
                intent1.putExtra("SortedGuildKill", sortedGuildKill);
                intent1.putExtra("SortedSquadPk", sortedSquadPk);
                intent1.putExtra("SortedSquadKill", sortedSquadKill);
                intent1.putExtra("SortedGuildSerial", sortedGuildSerial);
                intent1.putExtra("SortedSquadSerial", sortedSquadSerial);
                intent1.putExtra("Type", type);
                startActivity(intent1);
                break;
            }
            case "2": {
                TreeMap<Long, String> sortedGuildName = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, String> tsPointPerMatch = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedGuildSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadKill = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, Long> sortedSquadSerial = new TreeMap<>(Collections.<Long>reverseOrder());
                TreeMap<Long, TreeMap<Long, String>> sortedSquadPk = new TreeMap<>(Collections.<Long>reverseOrder());
                int j = 0, k = 0, m = 0;
                long guildSum = 0L, killSum = 0L;
                for (int i = 0; i < n * 6; i += 6) {
                    TreeMap<Long, String> squadResult = new TreeMap<>(Collections.<Long>reverseOrder());
                    for (int l = 0; l < squadNumber[j]; l++) {
                        guildSum += squadPointSum[k];
                        killSum += killPoint[k];
                        long tempSum = squadPointSum[k] * 10000;
                        String tempPointPerMatch;
                        if (gFlag > 1) {
                            tempPointPerMatch = sPointPerMatch[k].substring(2, sPointPerMatch[k].length() - 1);
                            tempPointPerMatch += " = ";
                        } else {
                            tempPointPerMatch = "";
                        }
                        tsPointPerMatch.put(((tempSum + killPoint[k]) * 100) + l + i, tempPointPerMatch);
                        squadResult.put(((tempSum + killPoint[k]) * 100) + l + i, squadName.get(m));
                        sortedSquadKill.put(((tempSum + killPoint[k]) * 100) + l + i, killPoint[k]);
                        sortedSquadSerial.put(((tempSum + killPoint[k]) * 100) + l + i, (long) (l + i));
                        m++;
                        k++;
                    }
                    long tempSum = guildSum * 10000;
                    String tempPointPerMatch;
                    if (gFlag > 1) {
                        tempPointPerMatch = pointPerMatch[j].substring(2, pointPerMatch[j].length() - 1);
                        tempPointPerMatch += " = ";
                    } else {
                        tempPointPerMatch = "";
                    }
                    tPointPerMatch.put(((tempSum + killSum) * 100) + i, tempPointPerMatch);
                    sortedGuildName.put(((tempSum + killSum) * 100) + i, guildName.get(j));
                    sortedGuildKill.put(((tempSum + killSum) * 100) + i, killSum);
                    sortedSquadPk.put(((tempSum + killSum) * 100) + i, squadResult);
                    sortedGuildSerial.put(((tempSum + killSum) * 100) + i, (long) i);
                    guildSum = 0L;
                    killSum = 0L;
                    if (squadNumber[j] == 5) {
                        k++;
                    } else if (squadNumber[j] == 4) {
                        k += 2;
                    } else if (squadNumber[j] == 3) {
                        k += 3;
                    } else if (squadNumber[j] == 2) {
                        k += 4;
                    } else if (squadNumber[j] == 1) {
                        k += 5;
                    } else if (squadNumber[j] == 0) {
                        k += 6;
                    }
                    j++;
                }
                Intent intent1 = new Intent(PutDetails.this, Result.class);
                intent1.putExtra("PointPerMatch", tPointPerMatch);
                intent1.putExtra("sPointPerMatch", tsPointPerMatch);
                intent1.putExtra("SortedGuildName", sortedGuildName);
                intent1.putExtra("SortedGuildKill", sortedGuildKill);
                intent1.putExtra("SortedSquadPk", sortedSquadPk);
                intent1.putExtra("SortedSquadKill", sortedSquadKill);
                intent1.putExtra("SortedGuildSerial", sortedGuildSerial);
                intent1.putExtra("SortedSquadSerial", sortedSquadSerial);
                intent1.putExtra("Type", type);
                startActivity(intent1);
                break;
            }
        }
    }

    private void structureMaker(String type) {
        switch (type) {
            case "12":
                guildName = intent.getStringArrayListExtra("GuildName");
                n = guildName != null ? guildName.size() : 0;
                for (int i = 0; i < n; i++) {
                    String s = i + 1 + ". " + guildName.get(i);
                    guildNameTv[i].setText(s);
                    killEt[i].setHint("Kill    ");
                    guildNameTv[i].setVisibility(View.VISIBLE);
                    positionEt[i].setVisibility(View.VISIBLE);
                    killEt[i].setVisibility(View.VISIBLE);
                }
                break;
            case "6": {
                guildName = intent.getStringArrayListExtra("GuildName");
                squadName = intent.getStringArrayListExtra("SquadName");
                squadNumber = intent.getIntArrayExtra("SquadNumber");
                n = guildName.size();
                int j = 0, k = 0, m = 0, c = 0;
                for (int i = 0; i < n * 2; i += 2) {
                    String s = j + 1 + ". " + guildName.get(j);
                    guildNameTv[i].setText(s);
                    guildNameTv[i].setVisibility(View.VISIBLE);
                    for (int l = 0; l < squadNumber[j]; l++) {
                        position[c++] = k;
                        killEt[k].setHint("Kill    ");
                        squadNameTv[k].setText(squadName.get(m));
                        squadNameTv[k].setVisibility(View.VISIBLE);
                        positionEt[k].setVisibility(View.VISIBLE);
                        killEt[k].setVisibility(View.VISIBLE);
                        k++;
                        m++;
                    }
                    if (squadNumber[j] == 1) {
                        k++;
                    } else if (squadNumber[j] == 0) {
                        k += 2;
                    }
                    j++;
                }
                break;
            }
            case "4": {
                guildName = intent.getStringArrayListExtra("GuildName");
                squadName = intent.getStringArrayListExtra("SquadName");
                squadNumber = intent.getIntArrayExtra("SquadNumber");
                n = guildName.size();
                int j = 0, k = 0, m = 0, c = 0;
                for (int i = 0; i < n * 3; i += 3) {
                    String s = j + 1 + ". " + guildName.get(j);
                    guildNameTv[i].setText(s);
                    guildNameTv[i].setVisibility(View.VISIBLE);
                    for (int l = 0; l < squadNumber[j]; l++) {
                        position[c++] = k;
                        killEt[k].setHint("Kill    ");
                        squadNameTv[k].setText(squadName.get(m));
                        squadNameTv[k].setVisibility(View.VISIBLE);
                        positionEt[k].setVisibility(View.VISIBLE);
                        killEt[k].setVisibility(View.VISIBLE);
                        k++;
                        m++;
                    }
                    if (squadNumber[j] == 2) {
                        k++;
                    } else if (squadNumber[j] == 1) {
                        k += 2;
                    } else if (squadNumber[j] == 0) {
                        k += 3;
                    }
                    j++;
                }
                break;
            }
            case "3": {
                guildName = intent.getStringArrayListExtra("GuildName");
                squadName = intent.getStringArrayListExtra("SquadName");
                squadNumber = intent.getIntArrayExtra("SquadNumber");
                n = guildName.size();
                int j = 0, k = 0, m = 0, c = 0;
                for (int i = 0; i < n * 4; i += 4) {
                    String s = j + 1 + ". " + guildName.get(j);
                    guildNameTv[i].setText(s);
                    guildNameTv[i].setVisibility(View.VISIBLE);
                    for (int l = 0; l < squadNumber[j]; l++) {
                        position[c++] = k;
                        killEt[k].setHint("Kill    ");
                        squadNameTv[k].setText(squadName.get(m));
                        squadNameTv[k].setVisibility(View.VISIBLE);
                        positionEt[k].setVisibility(View.VISIBLE);
                        killEt[k].setVisibility(View.VISIBLE);
                        k++;
                        m++;
                    }
                    if (squadNumber[j] == 3) {
                        k++;
                    } else if (squadNumber[j] == 2) {
                        k += 2;
                    } else if (squadNumber[j] == 1) {
                        k += 3;
                    } else if (squadNumber[j] == 0) {
                        k += 4;
                    }
                    j++;
                }
                break;
            }
            case "2": {
                guildName = intent.getStringArrayListExtra("GuildName");
                squadName = intent.getStringArrayListExtra("SquadName");
                squadNumber = intent.getIntArrayExtra("SquadNumber");
                n = guildName.size();
                int j = 0, k = 0, m = 0, c = 0;
                for (int i = 0; i < n * 6; i += 6) {
                    String s = j + 1 + ". " + guildName.get(j);
                    guildNameTv[i].setText(s);
                    guildNameTv[i].setVisibility(View.VISIBLE);
                    for (int l = 0; l < squadNumber[j]; l++) {
                        position[c++] = k;
                        killEt[k].setHint("Kill    ");
                        squadNameTv[k].setText(squadName.get(m));
                        squadNameTv[k].setVisibility(View.VISIBLE);
                        positionEt[k].setVisibility(View.VISIBLE);
                        killEt[k].setVisibility(View.VISIBLE);
                        k++;
                        m++;
                    }
                    if (squadNumber[j] == 5) {
                        k++;
                    } else if (squadNumber[j] == 4) {
                        k += 2;
                    } else if (squadNumber[j] == 3) {
                        k += 3;
                    } else if (squadNumber[j] == 2) {
                        k += 4;
                    } else if (squadNumber[j] == 1) {
                        k += 5;
                    } else if (squadNumber[j] == 0) {
                        k += 6;
                    }
                    j++;
                }
                break;
            }
        }
    }

    private int getPositionPoint(String s) {
        switch (s) {
            case "1":
                return 300;
            case "2":
                return 200;
            case "3":
                return 170;
            case "4":
                return 135;
            case "5":
                return 105;
            case "6":
                return 80;
            case "7":
                return 60;
            case "8":
                return 45;
            case "9":
                return 30;
            case "10":
                return 20;
            case "11":
                return 10;
            default:
                return 0;
        }
    }

    private int getKillPoint(String s) {
        if (!s.isEmpty()) {
            int x = Integer.parseInt(s);
            return x * 20;
        } else {
            return 0;
        }

    }

    private boolean isValid(String type) {
        int[] tempP = new int[12];
        int[] tempK = new int[12];
        if (type.equals("12")) {
            int x, tSum = 0;
            int bFlag = 0, tj = 0, ti;
            for (int i = 0; i < guildName.size(); i++) {
                ti = i;
                try {
                    tempK[i] = Integer.parseInt(killEt[i].getText().toString());
                    x = Integer.parseInt(positionEt[i].getText().toString());
                    if (x > 12) {
                        Toast.makeText(getApplicationContext(), "Position Error :-(", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Empty Field :-(", Toast.LENGTH_SHORT).show();
                    return false;
                }
                for (int j = 0; j < i; j++) {
                    if (tempP[j] == x) {
                        tj = j;
                        bFlag = 1;
                        break;
                    }
                }
                if (bFlag == 1) {
                    Toast.makeText(getApplicationContext(), "[" + guildName.get(tj) + "," + guildName.get(ti) + "] Same Position :-(", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    tempP[i] = x;
                }
            }
            for (int value : tempK) {
                tSum += value;
            }
            if (tSum > 48) {
                Toast.makeText(getApplicationContext(), "Total Kill Invalid.!!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            int x, tSum = 0;
            int bFlag = 0, tj = 0, ti;
            for (int i = 0; i < squadName.size(); i++) {
                ti = i;
                try {
                    tempK[i] = Integer.parseInt(killEt[position[i]].getText().toString());
                    x = Integer.parseInt(positionEt[position[i]].getText().toString());
                    if (x > 12) {
                        Toast.makeText(getApplicationContext(), "Position Error :-(", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Empty Field :-(", Toast.LENGTH_SHORT).show();
                    return false;
                }
                for (int j = 0; j < i; j++) {
                    if (tempP[j] == x) {
                        tj = j;
                        bFlag = 1;
                        break;
                    }
                }
                if (bFlag == 1) {
                    Toast.makeText(getApplicationContext(), "[" + squadName.get(tj) + "," + squadName.get(ti) + "] Same Position :-(", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    tempP[i] = x;
                }
            }
            for (int value : tempK) {
                tSum += value;
            }
            if (tSum > 48) {
                Toast.makeText(getApplicationContext(), "Total Kill Invalid.!!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(prl.getWindowToken(), 0);
        for (int i = 0; i < 12; i++) {
            positionEt[i].clearFocus();
            killEt[i].clearFocus();
        }
    }

    public void Back(View v) {
        super.onBackPressed();
    }

    public void Home(View v) {
        Intent intent = new Intent(PutDetails.this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}