package com.example.reto8alrramirezso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editNombre,editUrl,editTelefono,editEmail,editProd,editClasificacion;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNombre = (EditText)findViewById(R.id.editNombre);
        editUrl = (EditText)findViewById(R.id.editURL);
        editTelefono = (EditText)findViewById(R.id.editTelefono);
        editEmail = (EditText)findViewById(R.id.editTelefono);
        editProd = (EditText)findViewById(R.id.editProductos);
        editClasificacion = (EditText)findViewById(R.id.editClasificacion);

        btnAgregar = (Button)findViewById(R.id.btnAgregar);
        final DevelopBD developBD = new DevelopBD(getApplicationContext());

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                developBD.agregarEmpresa(editNombre.getText().toString(),editUrl.getText().toString(),editTelefono.getText().toString(),editEmail.getText().toString(),editProd.getText().toString(),editClasificacion.getText().toString());
                Toast.makeText(getApplicationContext(),"AGREGADA CORRECTAMENTE",Toast.LENGTH_SHORT).show();
            }
        });
    }
}