package com.example.activdades;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BasedbHelper extends SQLiteOpenHelper {

    String sqlCreateACTIVIDADES= "CREATE TABLE ACTIVIDADES (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ACTIVIDAD TEXT)";
    String sqlCreateMATRICULA= "CREATE TABLE MATRICULA (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, MATRICULA TEXT)";
    String sqlCreateCACTIVIDAD= "CREATE TABLE CACTIVIDAD (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ACTIVIDAD TEXT,FECHAINICIO TEXT, MATRICULA TEXT, HORAINICIO TEXT,CONDUCTOR TXT,FECHAFIN TEXT,HORAFIN TEXT, KM TEXT, MANTENIMIENTO TEXT,finalizado TEXT)";

    String sqlCreateCONDUCTOR = "CREATE TABLE CONDUCTOR (_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,CONDUCTOR TEXT, ID_ANDROID TEXT)";

    public BasedbHelper(Context context) {
        super(context, "DBSACTIVIDAD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreateACTIVIDADES);
        db.execSQL(sqlCreateMATRICULA);
        db.execSQL(sqlCreateCACTIVIDAD);
        db.execSQL(sqlCreateCONDUCTOR);


        Log.i(this.getClass().toString(), "BASE COMODINES CREADA");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//Se elimina la version anterior de la tabla

        if (newVersion > oldVersion) {
           // db.execSQL(sqlCreateCOMODINES);
            //db.execSQL("ALTER TABLE FORMACIONFIRMADAS ADD COLUMN FORMADOR TEXT");
        }
    }
}
