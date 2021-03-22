package com.example.activdades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final int CODIGO_PERMISOS_write = 1;
    public Button Inicio_Actividad,Fin_Actividad;
    public EditText txt_actividad;
    public TextView fecha, conductor, hora,  Matricula;
    public String hora_inicio,S_actividad;
    private Spinner Actividad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ver_permisos();

        CREAR_CARPETAS();
       // Insertar_conductor("-");
       // Insertar_MATRICULA("-");
       // Insertar_actividad();
        inicializar();

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



       // Cursor c_control=db.rawQuery("SELECT * FROM CACTIVIDAD ", null);

        Cursor c_actividad=db.rawQuery("SELECT * FROM ACTIVIDADES ", null);
        Cursor c_conductor=db.rawQuery("SELECT * FROM CONDUCTOR ", null);
        Cursor c_matricula=db.rawQuery("SELECT * FROM MATRICULA ", null);

        SimpleCursorAdapter adapterACTIVIDAD = new SimpleCursorAdapter(this,R.layout.custom_spinner_item1,c_actividad,(new String[] {ADAPTADORES.C_COLUMNA_ACTIVIDAD}), new int[] {R.id.Spiner_text},0);

       c_conductor.moveToLast();c_matricula.moveToLast();

        conductor.setText(c_conductor.getString(1));

       Matricula.setText(c_matricula.getString(1));

       Actividad.setAdapter(adapterACTIVIDAD);

        Actividad.setOnItemSelectedListener(this);


       /** if (c_control.moveToFirst()==true) {



              c_control.moveToLast();



                String finalizado=c_control.getString(9);
            String finalizado2=c_control.getString(1);
            Toast.makeText(this, finalizado2+" "+ID(), Toast.LENGTH_LONG).show();

                if (finalizado.equals("PENDIENTE")){
                    Fin_Actividad.setVisibility(View.VISIBLE);
                    Inicio_Actividad.setVisibility(View.INVISIBLE);

                }else{

                    Fin_Actividad.setVisibility(View.INVISIBLE);
                   Inicio_Actividad.setVisibility(View.VISIBLE);
                }




        }*/




    }
    private void inicializar(){

        Inicio_Actividad= findViewById(R.id.CMD_INICIAR_ACTIVIDAD);

        Fin_Actividad= findViewById(R.id.CMD_FIN_ACTIVIDAD);

       // txt_actividad= findViewById(R.id.txt_actividad);

        fecha = findViewById(R.id.t_fecha);
        conductor= findViewById(R.id.t_conductor);
        hora= findViewById(R.id.T_hora);
        Matricula= findViewById(R.id.t_matricula);
        Actividad= findViewById(R.id. sp_s_ACTIVIDAD);

        fecha.setText(ADAPTADORES.FECHAconformato());
        hora.setText(ADAPTADORES.HORAconformato());
    }
    private void CREAR_CARPETAS(){
        // listado de carpetas a crear
        File DIR = new File(this.getExternalFilesDir(null)+ADAPTADORES.R_RUTA_EXPORTACIONES);
        File DIR1 = new File(this.getExternalFilesDir(null)+ADAPTADORES.R_RUTA_ACTUALIZACIONES);


        // comprobamoms si existen los directorios "fotopuesto" y "ORDEN DE PUESTO" y creamos carpetas y subcarpetas
        if (!DIR.exists()){
            DIR.mkdirs();
            DIR1.mkdirs();

             Insertar_conductor("-");
             Insertar_MATRICULA("-");
             Insertar_actividad2();

            //Toast.makeText(getApplicationContext(), "CARPETA CREADA "+Environment.getExternalStorageDirectory().getPath(), Toast.LENGTH_SHORT).show();


        }

    }
    private void Ver_permisos(){
        int estadoDePermiso = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // Aquí el usuario dio permisos para acceder
        } else {
            // Si no, entonces pedimos permisos...
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    CODIGO_PERMISOS_write);
            // Insertar_conductor("SIN CONDUCTOR");

        }
    }
    private void Insertar_actividad() {
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();


        if (db != null) {

            ContentValues nuevoRegistro = new ContentValues();


           // nuevoRegistro.put("FECHAINICIO", ADAPTADORES.FECHAconformato());
            nuevoRegistro.put("ACTIVIDAD",S_actividad );
            nuevoRegistro.put("FECHAINICIO", ADAPTADORES.FECHAconformato());
            nuevoRegistro.put("HORAINICIO", ADAPTADORES.HORAconformato());
            nuevoRegistro.put("CONDUCTOR", conductor.getText().toString());
            nuevoRegistro.put("MATRICULA",Matricula.getText().toString());
            nuevoRegistro.put("KM", "");
            nuevoRegistro.put("MANTENIMIENTO", "");


            nuevoRegistro.put("finalizado", "PENDIENTE");

            db.insert("CACTIVIDAD", null, nuevoRegistro);

            Fin_Actividad.setVisibility(View.VISIBLE);
            Inicio_Actividad.setVisibility(View.INVISIBLE);


        }
    }
    private void Insertar_actividad2(){

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



        if (db != null) {

            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("finalizado", "FINALIZADO");

            db.insert("CACTIVIDAD", null, nuevoRegistro);


        }
        // final Intent i = new Intent(this, MainActivity.class);

        //startActivity(i);

        //finish();


    }
    private Integer ID(){
        Integer ID;
        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();

        Cursor c_fichaje=db.rawQuery("SELECT * FROM CACTIVIDAD ", null);

        c_fichaje.moveToLast();

        ID=c_fichaje.getInt(0);

        return ID;
    }
    private void fin_actividad(){


        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



        if (db != null) {

            ContentValues nuevoRegistro = new ContentValues();


            nuevoRegistro.put("finalizado", "FINALIZADO");



            db.update("CACTIVIDAD",nuevoRegistro,"_id="+ID(), null);
            Toast.makeText(this, "id: "+ ID(), Toast.LENGTH_LONG).show();

        }



    }
    public void Iniciar_Actividad(View view){

        Insertar_actividad();
        //final Intent i = new Intent(this, ACTIVIDADES.class);

        //startActivity(i);


        //finish();
    }
    public void Fin_Actividad(View view){
        fin_actividad();
        Fin_Actividad.setVisibility(View.INVISIBLE);
        Inicio_Actividad.setVisibility(View.VISIBLE);
    }
    public void inportar(View view){
        importar_Actividades();
    }
    public void Conductor(View view){
        ALERT_conductor();
    }

    public void Matricula(View view){
        ALERT_Matricula();
    }
    public void importar_Actividades(){


        File DIR = new File(this.getExternalFilesDir(null)+ADAPTADORES.R_RUTA_ACTUALIZACIONES);

        File f = new File(DIR, ADAPTADORES.A_CONTROL_ACTIVIDADES);

        if (f.exists()) {

            String texto;

            int N = 0;

            BasedbHelper usdbh = new BasedbHelper(this);

            SQLiteDatabase db = usdbh.getWritableDatabase();

            if (db != null) {
                db.execSQL("DELETE FROM ACTIVIDADES");
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name = '" + "ACTIVIDADES" + "'");
                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("ACTIVIDAD", "");

                db.insert("ACTIVIDADES", null, nuevoRegistro);


            }

            try
            {
                Toast.makeText(getApplicationContext(), "IMPORTANDO ACTIVIDADES... ", Toast.LENGTH_SHORT).show();

                BufferedReader ACTIVIDADES =
                        new BufferedReader(
                                new InputStreamReader(
                                        new FileInputStream(f)));
                while((texto = ACTIVIDADES.readLine())!=null){
                    String CODIFICACION= new String(texto.getBytes("UTF-8"),"UTF-8");
                    String [] registro=CODIFICACION.split(";");
                    if (N==0){N += 1;}else{

                        ContentValues nuevoRegistro = new ContentValues();

                        nuevoRegistro.put("ACTIVIDAD", registro[0]);



                        db.insert("ACTIVIDADES", null, nuevoRegistro);

                        N += 1;

                    }

                }
                ACTIVIDADES.close();

                db.close();

                //f.delete();

                Toast.makeText(getBaseContext(), "ARCHIVO ACTIVIDADES IMPORTADO... "+Integer.toString(N-1)+" Registros", Toast.LENGTH_SHORT).show();
            }
            catch (Exception ex)
            {
                Log.e("Ficheros", "Error al leer fichero desde tarjeta SD");

                Toast.makeText(getBaseContext(), "Error al leer fichero", Toast.LENGTH_SHORT).show();
            }
        }else{

            Toast.makeText(getBaseContext(), "NO EXISTE EL ARCHIVO", Toast.LENGTH_SHORT).show();}
    }
    private void ALERT_conductor(){
        final AlertDialog.Builder login= new AlertDialog.Builder(this);
        final EditText input= new EditText(this);
        input.setTextColor(Color.MAGENTA);
        login.setView(input);
        login.setTitle(R.string.ELEGIR);
        login.setMessage("CONDUCTOR (4 min):");
        login.setIcon(R.drawable.log_eulen);
        login.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(getApplicationContext(), "CANCELADO", Toast.LENGTH_SHORT).show();

            }
        });
        login.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String CONDUCTOR = input.getText().toString().trim();

                Insertar_conductor(CONDUCTOR.toUpperCase());

                Toast.makeText(getApplicationContext(), "El conductor "+CONDUCTOR+"\n ha sido Establecido como conductor por defecto", Toast.LENGTH_SHORT).show();


            }
        });

        AlertDialog alertDialog= login.create();
        alertDialog.show();
    }

    private void ALERT_Matricula(){
        final AlertDialog.Builder login= new AlertDialog.Builder(this);
        final EditText input= new EditText(this);
        input.setTextColor(Color.MAGENTA);
        login.setView(input);
        login.setTitle(R.string.ELEGIR);
        login.setMessage("MATRICULA (7 min):");
        login.setIcon(R.drawable.log_eulen);
        login.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(getApplicationContext(), "CANCELADO", Toast.LENGTH_SHORT).show();

            }
        });
        login.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                String MATRICULA = input.getText().toString().trim();

                Insertar_MATRICULA(MATRICULA.toUpperCase());

                Toast.makeText(getApplicationContext(), "LA MATRICULA "+MATRICULA+"\n ha sido Establecido como gestor por defecto", Toast.LENGTH_SHORT).show();


            }
        });

        AlertDialog alertDialog= login.create();
        alertDialog.show();
    }
    public void Insertar_conductor(String Conductor ){
        BasedbHelper  usdbh = new BasedbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();


        String Id_android;

        Integer l=Conductor.length();

        Id_android=String.valueOf(l)+Conductor.substring(l-1,l)+Conductor.substring(2,3)+String.valueOf(l-2)+Conductor.substring(0,1)+Conductor.substring(3,4);

        ContentValues nuevoRegistro = new ContentValues();

        nuevoRegistro.put("CONDUCTOR", Conductor);
        nuevoRegistro.put("ID_ANDROID",Id_android);

        //Insertamos el registro en la base de datos
        db.insert("CONDUCTOR", null, nuevoRegistro);
        // db.insert("ACTIVIDADES", null, actividades);
        db.close();


    }

    public void Insertar_MATRICULA(String Conductor ){
        BasedbHelper  usdbh = new BasedbHelper(this);
        SQLiteDatabase db = usdbh.getWritableDatabase();


        String Id_android;

        Integer l=Conductor.length();

        Id_android=String.valueOf(l)+Conductor.substring(l-1,l)+Conductor.substring(2,3)+String.valueOf(l-2)+Conductor.substring(0,1)+Conductor.substring(3,4);

        ContentValues nuevoRegistro = new ContentValues();

        nuevoRegistro.put("MATRICULA", Conductor);


        //Insertamos el registro en la base de datos
        db.insert("MATRICULA", null, nuevoRegistro);

        db.close();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sp_s_ACTIVIDAD) {
            Cursor cli = (Cursor) parent.getItemAtPosition(position);

            S_actividad = cli.getString(cli.getColumnIndex(ADAPTADORES.C_COLUMNA_ACTIVIDAD));

           // Toast.makeText(this, "id: "+ S_actividad, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}