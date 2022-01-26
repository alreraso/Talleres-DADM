package com.unal.reto10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner departamenos_spinner;
    private Spinner regional_spinner;
    private ArrayList<String> departamentoss;
    private ArrayList<String> regionaless;
    private ArrayAdapter<String> departamenos_adapter;
    private ArrayAdapter<String> regionales_adapter;
    private Spinner municipios_spinner;
    private Spinner tipos_spinner;
    private ArrayList<String> municipioss;
    private ArrayList<String> tipos;
    private ArrayAdapter<String> municipios_adapter;
    private ArrayAdapter<String> tipos_adapter;
    private ListView list;
    private ListView listadoGeneral;
    private ArrayList<String> peajes;
    private ArrayList<String> datos;
    private ArrayAdapter<String> cod_adapter;
    private ArrayAdapter<String> datos_adapter;
    private Context context = this;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        regionaless = new ArrayList<>();
        tipos = new ArrayList<>();
        datos = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        String url = "https://www.datos.gov.co/resource/88fs-w95j.json?$select=distinct%20regional&$order=regional%20ASC";
        regional_spinner = (Spinner) findViewById(R.id.regionales);
        tipos_spinner = (Spinner) findViewById(R.id.tipos);
        listadoGeneral = findViewById(R.id.list);
        JsonArrayRequest departamentos = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject tmp = null;
                            try {
                                tmp = response.getJSONObject(i);
                                regionaless.add(tmp.getString("regional"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        regionales_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, regionaless);
                        regionales_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        regional_spinner.setAdapter(regionales_adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        regional_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                tipos.clear();
                String tmp = (String) parent.getItemAtPosition(pos);
                String url = "https://www.datos.gov.co/resource/88fs-w95j.json?$select=distinct%20tipo&regional="+ tmp +"&$order=tipo%20ASC";
                JsonArrayRequest municipios = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject tmp = null;
                                    try {
                                        tmp = response.getJSONObject(i);
                                        tipos.add(tmp.getString("tipo"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                tipos_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, tipos);
                                tipos_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                tipos_spinner.setAdapter(tipos_adapter);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                queue.add(municipios);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tipos_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                datos.clear();
                final String tmp = (String) parent.getItemAtPosition(pos);
                String url = "https://www.datos.gov.co/resource/88fs-w95j.json?tipo=" + tmp;
                JsonArrayRequest codes = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject tmp = null;
                                    try {
                                        tmp = response.getJSONObject(i);
                                        String tmp2 = "Nombre: " + tmp.getString("nombre") + "\n";
                                        tmp2 += "Direccion: " + tmp.getString("direccion") + "\n";
                                        tmp2 += "Telefono: " + tmp.getString("telefono") + "\n";
                                        tmp2 += "Horario" + tmp.getString("horario_de_atencion") + "\n";
                                        tmp2 += "Longitud" + tmp.getString("longitud") + "\n";
                                        tmp2 += "Latitud" + tmp.getString("latitud") + "\n";
                                        datos.add(tmp2);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                datos_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, datos);
                                listadoGeneral.setAdapter(datos_adapter);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("REQ", "bad");
                            }
                        });
                queue.add(codes);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        queue.add(departamentos);

    }
}
