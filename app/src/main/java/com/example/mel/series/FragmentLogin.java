package com.example.mel.series;

import android.app.Fragment;
import android.content.ContentValues;

import android.content.Intent;
import android.database.Cursor;
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


/**
 * Created by Mel on 15/7/2016.
 */
public class FragmentLogin extends Fragment {

    private View rootView;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView info, info2;

    private String nombreBD;
    private String idFaceBD;


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

                    nombreBD=profile.getName();
                    idFaceBD = profile.getId();

                    info.setText("BIENVENIDO " + nombreBD);
                    info2.setText("ID " + idFaceBD);
                    altaUsuario(idFaceBD,nombreBD);
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

    public void altaUsuario(String idF, String nom) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(),"DBAppSeries", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        //primero lo busco en la BD
        Cursor fila = bd.rawQuery("select id,nombreApellido,tema  from usuario where idFace=" + idF, null); //devuelve 0 o 1 fila //es una consulta

        if (fila.moveToFirst()) {  //si devolvió 1 fila, vamos al primero (que es el unico)

            //ya esta registrado en la BD
            Toast.makeText(getActivity(),"BIENVENIDO NUEVAMENTE",Toast.LENGTH_SHORT).show();
        }
        else {
            //si no esta en la BD lo inserto


            String tema = "tema1";  //le estoy mandado cualquier tema, ojo aca!

            ContentValues registro = new ContentValues();  //es una clase para guardar datos

            registro.put("idFace", idF);
            registro.put("nombreApellido", nom);
            registro.put("tema", tema);

            bd.insert("usuario", null, registro);
            bd.close();

            String msj = "idFace " + idF + " nombre " + nom;
            Toast.makeText(getActivity(), "AltaUsuario " + msj, Toast.LENGTH_SHORT).show();
        }
    }



}
