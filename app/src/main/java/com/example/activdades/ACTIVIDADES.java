package com.example.activdades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ACTIVIDADES extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public TextView fecha, conductor, hora,  Matricula;
    public String hora_inicio,S_actividad;
    private Spinner Actividad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_n_i_c_i_a_r__a_c_t_i_v_i_d_a_d);
        Inicializar();
    }
    private void Inicializar(){
        fecha = findViewById(R.id.TXT_FECHA);
        conductor= findViewById(R.id.TXT_CONDUCTOR);
        hora= findViewById(R.id.TXT_HORA);
        Matricula= findViewById(R.id.TXT_MATRICULA);
        Actividad= findViewById(R.id. SP_ACTIVIDAD);

        fecha.setText(ADAPTADORES.FECHAconformato());
        hora.setText(ADAPTADORES.HORAconformato());

        BasedbHelper  usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();


        Cursor c_actividad=db.rawQuery("SELECT * FROM ACTIVIDADES ", null);
        Cursor c_conductor=db.rawQuery("SELECT * FROM CONDUCTOR ", null);
        Cursor c_matricula=db.rawQuery("SELECT * FROM MATRICULA ", null);
        //Cursor c_control=db.rawQuery("SELECT * FROM CACTIVIDAD", null);

        SimpleCursorAdapter adapterACTIVIDAD = new SimpleCursorAdapter(this,R.layout.custom_spinner_item1,c_actividad,(new String[] {ADAPTADORES.C_COLUMNA_ACTIVIDAD}), new int[] {R.id.Spiner_text},0);
        // Resources res = getResources();
        // String actividades[] = res.getStringArray(R.array.actividades);
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.custom_spinner_item1, R.id.textView, actividades);
        c_conductor.moveToLast();c_matricula.moveToLast();//c_control.moveToLast();

        //fecha.setText(c_control.getString(9));

        conductor.setText(c_conductor.getString(1));

        Matricula.setText(c_matricula.getString(1));

        Actividad.setAdapter(adapterACTIVIDAD);

        Actividad.setOnItemSelectedListener(this);

    }
    private void Insertar_actividad(){

        BasedbHelper usdbh = new BasedbHelper(this);

        SQLiteDatabase db = usdbh.getWritableDatabase();



        if (db != null) {

            ContentValues nuevoRegistro = new ContentValues();

            nuevoRegistro.put("FECHA", ADAPTADORES.FECHAconformato());
            nuevoRegistro.put("ACTIVIDAD",S_actividad );
            nuevoRegistro.put("FECHAINICIO", fecha.getText().toString());
            nuevoRegistro.put("HORAINICIO", hora.getText().toString());
            nuevoRegistro.put("CONDUCTOR", conductor.getText().toString());
            nuevoRegistro.put("MATRICULA", Matricula.getText().toString());
            nuevoRegistro.put("KM", "");
            nuevoRegistro.put("MANTENIMIENTO", "");


            nuevoRegistro.put("finalizado", "PENDIENTE");

            db.insert("CACTIVIDAD", null, nuevoRegistro);

            Toast.makeText(this, "añadido ", Toast.LENGTH_LONG).show();
        }
       // final Intent i = new Intent(this, MainActivity.class);

        //startActivity(i);

        //finish();
        Cursor c_control=db.rawQuery("SELECT * FROM CACTIVIDAD", null);
        c_control.moveToLast();

        Toast.makeText(this, "id: "+c_control.getString(1), Toast.LENGTH_LONG).show();

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
    public void Iniciar_actividad(View view){
        Insertar_actividad();
    }
    public void Salir(View view){
        final Intent i = new Intent(this, MainActivity.class);

        startActivity(i);

        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.SP_ACTIVIDAD) {
            Cursor cli = (Cursor) parent.getItemAtPosition(position);

            S_actividad = cli.getString(cli.getColumnIndex(ADAPTADORES.C_COLUMNA_ACTIVIDAD));
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}