package com.example.mel.series;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static android.widget.Toast.makeText;

/**
 * Created by Mel on 15/7/2016.
 */
public class FragmentLogin extends Fragment {

    private View rootView;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView info, info2;

    private TransmitirDatos informacion;


    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        rootView = inflater.inflate(R.layout.fragmentlogin, container,false);
        return rootView;

    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginButton = (LoginButton)view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        info = (TextView)view.findViewById(R.id.nombreUsuario);
        info2 = (TextView)view.findViewById(R.id.idFacebook);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                if (profile!=null) {
                    info.setText("BIENVENIDO " + profile.getName());
                    info2.setText("ID " + profile.getId());

                    //informacion.transmitirDatos(profile.getId(), profile.getName());
                }
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //tener cuidado que el onAttach (activity) esta en desuso
    public void onAttach (Context context){
        super.onAttach(context);
        informacion = (TransmitirDatos) context;

    }



}
