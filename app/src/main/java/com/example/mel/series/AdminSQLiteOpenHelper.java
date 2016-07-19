package com.example.mel.series;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    //Constructor
    public AdminSQLiteOpenHelper(Context context, String nombre, CursorFactory factory, int version) {
        super(context, nombre, factory, version);
    }

    //Creamos las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario (id integer primary key autoincrement, idFace integer, nombreApellido text, tema text)");
    }

    //Borrar las tablas y crear las nuevas tablas
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionPosterior) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("create table usuario (id integer primary key autoincrement, idFace integer, nombreApellido text, tema text)");
    }
}