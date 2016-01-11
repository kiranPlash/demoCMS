package in.plash.trunext.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.plash.trunext.R;


public class SignUpConfirmationFrag extends SuperFragment {


    private View view;
    private TextView btnGoToLogin;

    public SignUpConfirmationFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_confirmation, container, false);
        btnGoToLogin = (TextView) view.findViewById(R.id.txt_go_to_login);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new LoginFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                if (fragment.isAdded()) {
                    fragmentTransaction.show(fragment);
                } else {
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
                    fragmentTransaction.replace(R.id.content_login_frame, fragment);
                    // fragmentTransaction.addToBackStack(null);
                }
                fragmentTransaction.commit();

            }
        });
    }
}
