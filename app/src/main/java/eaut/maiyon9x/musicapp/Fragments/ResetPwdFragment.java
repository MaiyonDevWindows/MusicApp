package eaut.maiyon9x.musicapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import eaut.maiyon9x.musicapp.R;
public class ResetPwdFragment extends Fragment {
    private FrameLayout frameLayout;
    private TextView TVGoBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_pwd, container, false);
        TVGoBack = view.findViewById(R.id.tv_goBack);
        frameLayout = getActivity().findViewById(R.id.register_frame);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TVGoBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setFragment(new SignInFragment());
            }
        });
    }
    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}