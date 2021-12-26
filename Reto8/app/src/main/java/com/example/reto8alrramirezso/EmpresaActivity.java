package com.example.reto8alrramirezso;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmpresaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEmpresa;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

        recyclerViewEmpresa = (RecyclerView)findViewById(R.id.recyclerEmpresas);
        recyclerViewEmpresa.setLayoutManager(new LinearLayoutManager(this));

        DevelopBD developBD = new DevelopBD(getApplicationContext());

        adapter = new RecyclerAdapter(developBD.showEmpresas());
        recyclerViewEmpresa.setAdapter(adapter);
    }

    /* public List<EmpresaModelo> obtenerEmpresas () {
        List<EmpresaModelo> empresas = new ArrayList<>();
        empresas.add(new EmpresaModelo("mobiera","mobiera.com","12345678","mobiera@gmail.com","software","desarrollo de software",R.drawable.build));
        empresas.add(new EmpresaModelo("mobiera2","mobiera.com","12345678","mobiera@gmail.com","software","desarrollo de software",R.drawable.build));
        empresas.add(new EmpresaModelo("mobiera3","mobiera.com","12345678","mobiera@gmail.com","software","desarrollo de software",R.drawable.build));
        empresas.add(new EmpresaModelo("mobiera4","mobiera.com","12345678","mobiera@gmail.com","software","desarrollo de software",R.drawable.build));

        return empresas;
    } */
}