package com.example.reto8alrramirezso;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DevelopBD extends SQLiteOpenHelper {
    private static final String NOMBRE_BD = "sql_rene_BD_reto8";
    private static final int VERSION_BD = 1;
    private static final String TABALA_EMPRESAS = "CREATE TABLE EMPRESAS(NOMBRE TEXT PRIMARY KEY, URLWEB TEXT, TELEFONO TEXT, CORREO TEXT,PRODSERV TEXT, CLASIFICACION TEXT)";

    public DevelopBD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABALA_EMPRESAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABALA_EMPRESAS);
        db.execSQL(TABALA_EMPRESAS);
    }

    public void agregarEmpresa(String nombre, String url ,String telefono, String email, String produc,String clasificacion){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            db.execSQL("INSERT INTO EMPRESAS VALUES('"+nombre+"','"+url+"','"+telefono+"','"+email+"','"+produc+"','"+clasificacion+"')");
            db.close();
        }
    }
}
