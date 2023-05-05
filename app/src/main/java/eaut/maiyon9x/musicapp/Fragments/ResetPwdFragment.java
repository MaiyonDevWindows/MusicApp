package eaut.maiyon9x.musicapp.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

import com.google.firebase.auth.FirebaseAuth;

import eaut.maiyon9x.musicapp.R;
public class ResetPwdFragment extends Fragment {
    private Context context;
    private Drawable errorIcon;
    private FrameLayout frameLayout;
    private TextView tvGoBack;
    private TextView tvResponseMsg;
    private EditText userEmail;
    private ProgressBar progressRsPwd;
    private Button btnResetPwd;
    private FirebaseAuth mFirebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_pwd, container, false);
        tvGoBack = view.findViewById(R.id.tvGoBack);
        frameLayout = getActivity().findViewById(R.id.register_frame);
        context = container.getContext();
        errorIcon = ContextCompat.getDrawable(context, R.drawable.ic_error);

        userEmail = view.findViewById(R.id.txtUserEmail);
        tvResponseMsg = view.findViewById(R.id.tvResponseMsg);
        progressRsPwd = view.findViewById(R.id.progressRsPwd);
        btnResetPwd = view.findViewById(R.id.btnResetPwd);
        mFirebaseAuth = FirebaseAuth.getInstance();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        errorIcon.setBounds(0,0, errorIcon.getIntrinsicWidth(),
                errorIcon.getIntrinsicHeight());
        tvGoBack.setOnClickListener(v -> setFragment(new SignInFragment()));
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
        btnResetPwd.setOnClickListener(v -> resetPassword());
    }
    public void setFragment(Fragment fragment) {
        FragmentTransaction
                fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.out_from_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs(){
        if(!userEmail.getText().toString().isEmpty()){
            btnResetPwd.setEnabled(true);
            btnResetPwd.setTextColor(ContextCompat.getColor(context, R.color.white));
        }else{
            btnResetPwd.setEnabled(false);
            btnResetPwd.setTextColor(ContextCompat.getColor(context, R.color.transWhite));
        }
    }
    private void resetPassword(){
        if(userEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            progressRsPwd.setVisibility(View.VISIBLE);
            mFirebaseAuth.sendPasswordResetEmail(userEmail.getText().toString()).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    tvResponseMsg.setText(R.string.rs_pwd_success);
                }else{
                    tvResponseMsg.setText(R.string.rs_pwd_failure);
                }
                tvResponseMsg.setTextColor(ContextCompat.getColor(context, R.color.btgrad_end));
                progressRsPwd.setVisibility(View.INVISIBLE);
                tvResponseMsg.setVisibility(View.VISIBLE);
            });
        }else{
            userEmail.setError("Invalid Email Pattern!", errorIcon);
            btnResetPwd.setEnabled(true);
            btnResetPwd.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }
}