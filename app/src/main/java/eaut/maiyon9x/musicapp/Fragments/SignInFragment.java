package eaut.maiyon9x.musicapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import eaut.maiyon9x.musicapp.Activities.MainActivity;
import eaut.maiyon9x.musicapp.R;
public class SignInFragment extends Fragment {
    private FrameLayout frameLayout;
    private TextView TVResetPassword;
    private TextView TVDontHaveAnAccount;
    private EditText userEmail;
    private EditText password;
    private Button btnLogin;
    private ProgressBar progressLogin;

    private FirebaseAuth mFirebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        TVResetPassword = view.findViewById(R.id.tv_resetPassword);
        TVDontHaveAnAccount = view.findViewById(R.id.tv_dontHaveAnAccount);
        frameLayout = getActivity().findViewById(R.id.register_frame);

        userEmail = view.findViewById(R.id.txtLoginEmail);
        password = view.findViewById(R.id.txtLoginPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        progressLogin = view.findViewById(R.id.progressLogin);

        mFirebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TVDontHaveAnAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setFragment(new SignUpFragment());
            }
        });

        TVResetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setFragment(new ResetPwdFragment());
            }
        });

        userEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                btnLogin.setTextColor(getResources().getColor(R.color.transWhite));
                loginWithFirebase();
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.out_from_left);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs(){
        if(!userEmail.getText().toString().isEmpty()){
            if(!password.getText().toString().isEmpty()){
                btnLogin.setEnabled(true);
                btnLogin.setTextColor(getResources().getColor(R.color.white));
            }else{
                btnLogin.setEnabled(false);
                btnLogin.setTextColor(getResources().getColor(R.color.transWhite));
            }
        }else{
            btnLogin.setEnabled(false);
            btnLogin.setTextColor(getResources().getColor(R.color.transWhite));
        }
    }
    private void loginWithFirebase(){
        if(userEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            progressLogin.setVisibility(View.VISIBLE);
            mFirebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString(),
                    password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressLogin.setVisibility(View.INVISIBLE);
                    if(task.isSuccessful()){
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }else{
                        Toast.makeText(getContext(), task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        btnLogin.setEnabled(true);
                        btnLogin.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            });
        }else{
            userEmail.setError("Invalid Email Pattern!");
            btnLogin.setEnabled(true);
            btnLogin.setTextColor(getResources().getColor(R.color.white));
        }
    }
}