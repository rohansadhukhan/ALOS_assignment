package com.example.assignment.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import com.example.assignment.R;
import com.example.assignment.Utility;
import com.google.android.material.textfield.TextInputEditText;

public class ForgetPassword extends AppCompatDialogFragment {

    private TextInputEditText recovery_email;
    private ForgotDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_forget_password, null);

        recovery_email = view.findViewById(R.id.recovery_email);

        builder.setView(view)
                .setTitle("Forgot Password")
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .setPositiveButton("Send", (dialog, which) -> {
                    String email = recovery_email.getText().toString();
                    if(validateEmail(email)) {
                        listener.onRequestResetPasswordLink(email);
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ForgotDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " implement Forgot Dialog Listener");
        }
    }

    public interface ForgotDialogListener {
        void onRequestResetPasswordLink(String email);
    }

    private boolean validateEmail(String email) {

        if(email.isEmpty()) {
            recovery_email.setError("Field can't be empty");
            return false;
        } else if(!Utility.EMAIL_PATTERN.matcher(email).matches()) {
            recovery_email.setError("Please enter a valid email");
            return false;
        }
        recovery_email.setError(null);
        return true;

    }

}