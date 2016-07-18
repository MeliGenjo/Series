package com.example.mel.series;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity implements TransmitirDatos{

    private String nombre;
    private String idFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //FacebookSdk.sdkInitialize(getApplicationContext());
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case (R.id.iniciarSesion):
                Toast.makeText(getApplicationContext(), "loggin", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void altaUsuario(View v) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"DBAppSeries", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        //levanto la info de los TextView pero en realidad hay que traerlo desde Profile
        String nom = nombre;
        String idF = idFace;
        String tema = "tema1";

        ContentValues registro = new ContentValues();  //es una clase para guardar datos

        registro.put("idFace", idF);
        registro.put("nombreApellido", nom);
        registro.put ("tema", tema);

        bd.insert("usuario", null, registro);
        bd.close();

        String msj = "idFace " + idF + " nombre " + nom;
        Toast.makeText(this,"AltaUsuario " + msj,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void transmitirDatos(String id, String name) {
        nombre=name;
        idFace = id;
    }
}
