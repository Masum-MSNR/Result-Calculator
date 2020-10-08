package com.project.resultcalculator.Classes;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.resultcalculator.R;

public class FeedbackDialog extends AppCompatDialogFragment {
    EditText messageEt;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.feedback_dialog,null);
        messageEt=view.findViewById(R.id.feedback);
        dialog.setView(view)
                .setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message=messageEt.getText().toString();
                        String uniqueId= Settings.Secure.getString(getContext().getContentResolver(),Settings.Secure.ANDROID_ID);
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Feedbacks");
                        databaseReference.child(uniqueId).setValue(message);
                    }
                })
                .setNegativeButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return dialog.create();
    }
}
