package com.example.activdades;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ADAPTADORES {

    public static final String R_RUTA_EXPORTACIONES = "/ACTIVIDAD/EXPORTACIONES/";
    public static final String R_RUTA_ACTUALIZACIONES = "/ACTIVIDAD/ACTUALIZACIONES/";
    // public static final String R_RUTA="/COMODIN/";
    public static final String C_COLUMNA_ID = "_id";
    public static final String C_COLUMNA_GESTOR = "GESTOR";
    public static final String C_COLUMNA_ID_ANDROID = "ID_ANDROID";
    public static final String C_COLUMNA_ACTIVIDAD = "ACTIVIDAD";
    public static final String C_COLUMNA_COMODIN = "COMODIN";

    private String[] listaGESTOR = new String[]{C_COLUMNA_ID, C_COLUMNA_GESTOR, C_COLUMNA_ID_ANDROID};
    private String[] listaactividad = new String[]{C_COLUMNA_ID, C_COLUMNA_GESTOR, C_COLUMNA_ID_ANDROID};
    public static final String A_CONTROL_ACTIVIDADES = "ACTIVIDADES.txt";
    public static final String A_CLIENTES = "CLIENTES.txt";
    public static final String A_GESTORES = "GESTORES.txt";
    public static final String A_PLAN = "PLAN.txt";

    private Context contexto;
    private BasedbHelper dbHelper;
    private SQLiteDatabase db;

    public ADAPTADORES(Context context) {
        this.contexto = context;
    }

    public BasedbHelper abrir(Context cntx) throws SQLException {
        dbHelper = new BasedbHelper(cntx);
        db = dbHelper.getWritableDatabase();
        return dbHelper;
    }

    public void cerrar() {
        dbHelper.close();
    }

    public Cursor getGESTOR() throws SQLException {
        Cursor c = db.query(true, C_COLUMNA_GESTOR, listaGESTOR, null, null, null, null, null, null);

        return c;
    }

    public Cursor getControl() throws SQLException {
        Cursor c= db.query(true, C_COLUMNA_GESTOR, listaGESTOR, null, null, null, null, null, null);
        return c;
}
    public static  String FECHAconformato() {
        Long date = System.currentTimeMillis()+172800000;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //String dateString =
        return sdf.format(date);
    }


    public static  String HORAconformato() {
        Long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ITALIAN);
        String dateString = sdf.format(date);
        return dateString;
    }
    //devuelve String con el MES Y AÑO
    public static  final String mesyano(String FECHA){
        String mmes="",anno="",mesyaNo="";

        anno = FECHA.substring(6);

        mmes = FECHA.substring(3,5);


        switch (mmes){
            case "01":
                mesyaNo="ENERO "+anno;
                break;
            case "02":
                mesyaNo="FEBRERO "+anno;
                break;
            case "03":
                mesyaNo="MARZO "+anno;
                break;
            case "04":
                mesyaNo="ABRIL "+anno;
                break;
            case "05":
                mesyaNo="MAYO "+anno;
                break;
            case "06":
                mesyaNo="JUNIO "+anno;
                break;
            case "07":
                mesyaNo="JULIO "+anno;
                break;
            case "08":
                mesyaNo="AGOSTO "+anno;
                break;
            case "09":
                mesyaNo="SEPTIEMBRE "+anno;
                break;
            case "10":
                mesyaNo="OCTUBRE "+anno;
                break;
            case "11":
                mesyaNo="NOVIEMBRE "+anno;
                break;
            case "12":
                mesyaNo="DICIEMBRE "+anno;
                break;
        }


        return mesyaNo;
    }

    //devuelve String con el dia de semana
    public static  final String Diadelasemana(int dia,int mes,int aNo){

        Calendar now = Calendar.getInstance();

        now.set(aNo,mes,dia);

        String [] diassemana={"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

        //return diassemana[now.get(Calendar.DAY_OF_WEEK) - 1];
        return diassemana[0];
    }
}
