package com.example.assignment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.R;
import com.example.assignment.fragment.ForgetPassword;
import com.example.assignment.fragment.SignUpFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements SignUpFragment.SignUpListener, ForgetPassword.ForgotDialogListener {

    private TextInputEditText email;
    private TextInputEditText password;
    private MaterialButton signIn;
    private ProgressBar progressBar;
    private MaterialTextView forgetPassword;
    private MaterialTextView signUp;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    private String TAG = "myDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        signIn = findViewById(R.id.sign_in);
        progressBar = findViewById(R.id.progress_bar);
        forgetPassword = findViewById(R.id.forget_password);
        signUp = findViewById(R.id.sign_up_text);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();

        forgetPassword.setOnClickListener(v -> {
            ForgetPassword forgetPassword = new ForgetPassword();
            forgetPassword.show(getSupportFragmentManager(), "ForgotFragment");
        });

        signUp.setOnClickListener(v -> {
            SignUpFragment signUpFragment = new SignUpFragment();
            signUpFragment.show(getSupportFragmentManager(), "SignUpFragment");
        });

        signIn.setOnClickListener(v -> {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty())
                return;
            progressBar.setVisibility(View.VISIBLE);
            signIn.setVisibility(View.GONE);
            signInUser(email.getText().toString(), password.getText().toString());
        });

    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        signIn.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public void signUpUser(String name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");

                        String Uid = mAuth.getCurrentUser().getUid();

                        DocumentReference documentReference = mStore.collection("users").document(Uid);

                        Map<String, Object> curUser = new HashMap<>();
                        curUser.put("Name", name);
                        curUser.put("Email", email);
                        documentReference.set(curUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "New user profile created");
                            }
                        }).addOnFailureListener(e -> Log.d(TAG, "Failure : " + e.getMessage()));

                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onRequestResetPasswordLink(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Verification link has been send", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error occurred : " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}