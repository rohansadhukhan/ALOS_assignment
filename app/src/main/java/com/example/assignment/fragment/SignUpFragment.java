package com.example.assignment.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment.R;
import com.example.assignment.Utility;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends BottomSheetDialogFragment {

    private SignUpListener listener;
    private TextInputEditText name_sign_up;
    private TextInputEditText email_sign_up;
    private TextInputEditText password_sign_up;
    private MaterialButton signUp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);


        name_sign_up = view.findViewById(R.id.sign_up_name);
        email_sign_up = view.findViewById(R.id.sign_up_email);
        password_sign_up = view.findViewById(R.id.sign_up_password);
        signUp = view.findViewById(R.id.sign_up);

        signUp.setOnClickListener(v -> {
            if(validateEmail(email_sign_up.getText().toString()) && validateName(name_sign_up.getText().toString()) && validatePassword(password_sign_up.getText().toString())) {
                listener.signUpUser(name_sign_up.getText().toString(), email_sign_up.getText().toString(), password_sign_up.getText().toString());
                dismiss();
            }
        });


        return view;
    }

    public interface SignUpListener {
        void signUpUser(String name, String email, String password);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SignUpListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " implement SignUp Bottom Sheet Listener");
        }
    }

    private boolean validateEmail(String email) {

        if(email.isEmpty()) {
            email_sign_up.setError("Field can't be empty");
            return false;
        } else if(!Utility.EMAIL_PATTERN.matcher(email).matches()) {
            email_sign_up.setError("Please enter a valid email");
            return false;
        }
        email_sign_up.setError(null);
        return true;

    }

    private boolean validateName(String name) {

        if(name.isEmpty()) {
            name_sign_up.setError("Field can't be empty");
            return false;
        }
        name_sign_up.setError(null);
        return true;

    }

    private boolean validatePassword(String password) {

        if(password.isEmpty()) {
            password_sign_up.setError("Field can't be empty");
            return false;
        } else if(!Utility.PASSWORD_PATTERN.matcher(password).matches()) {
            password_sign_up.setError("a-z or A-Z and min 6 char");
            return false;
        }
        password_sign_up.setError(null);
        return true;

    }
}