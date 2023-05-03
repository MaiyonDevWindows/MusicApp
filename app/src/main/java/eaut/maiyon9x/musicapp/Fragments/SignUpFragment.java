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

public class SignUpFragment extends Fragment {
    private FrameLayout frameLayout;
    private TextView TVAlreadyHaveAccount;
    private EditText userName;
    private EditText userEmail;
    private EditText password;
    private EditText rePassword;
    private ProgressBar progressRegister;
    private Button btnRegister;
    private FirebaseAuth mFirebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        TVAlreadyHaveAccount = view.findViewById(R.id.tv_alreadyHaveAnAccount);
        frameLayout = getActivity().findViewById(R.id.register_frame);

        userName = view.findViewById(R.id.txtRegisterUserName);
        userEmail = view.findViewById(R.id.txtRegisterEmail);
        password = view.findViewById(R.id.txtRegisterPwd);
        rePassword = view.findViewById(R.id.txtRegisterRePwd);
        progressRegister = view.findViewById(R.id.progressLogin);
        btnRegister = view.findViewById(R.id.btnRegister);
        mFirebaseAuth = FirebaseAuth.getInstance();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TVAlreadyHaveAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setFragment(new SignInFragment());
            }
        });
        userName.addTextChangedListener(new TextWatcher() {
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
        rePassword.addTextChangedListener(new TextWatcher() {
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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerWithFirebase();
                btnRegister.setEnabled(false);
                btnRegister.setTextColor(getResources().getColor(R.color.transWhite));
            }
        });
    }
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs(){
        if(!userName.getText().toString().isEmpty()){
            if(!userEmail.getText().toString().isEmpty()){
                if(!password.getText().toString().isEmpty()
                        && password.getText().toString().length() >= 6){
                    if(!rePassword.getText().toString().isEmpty()){
                        btnRegister.setEnabled(true);
                        btnRegister.setTextColor(getResources().getColor(R.color.white));
                    }else{
                        btnRegister.setEnabled(false);
                        btnRegister.setTextColor(getResources().getColor(R.color.transWhite));
                    }
                }else{
                    btnRegister.setEnabled(false);
                    btnRegister.setTextColor(getResources().getColor(R.color.transWhite));
                }
            }else{
                btnRegister.setEnabled(false);
                btnRegister.setTextColor(getResources().getColor(R.color.transWhite));
            }
        }else{
            btnRegister.setEnabled(false);
            btnRegister.setTextColor(getResources().getColor(R.color.transWhite));
        }
    }
    private void registerWithFirebase(){
        if(userEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            if(password.getText().toString().equals(rePassword.getText().toString())){
                progressRegister.setVisibility(View.VISIBLE);
                mFirebaseAuth.createUserWithEmailAndPassword(userEmail.getText().toString(),
                        password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressRegister.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getContext(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            btnRegister.setEnabled(true);
                            btnRegister.setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                });
            }else{
                rePassword.setError("RePassword doesn't match!");
                btnRegister.setEnabled(true);
                btnRegister.setTextColor(getResources().getColor(R.color.white));
            }
        }else{
            userEmail.setError("Invalid Email Pattern!");
            btnRegister.setEnabled(true);
            btnRegister.setTextColor(getResources().getColor(R.color.white));
        }
    }
}