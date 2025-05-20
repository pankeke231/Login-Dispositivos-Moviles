package com.example.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_getAbilities, btn_getColor, btn_getBaseEvolution;
    EditText et_pokemonInput;
    ListView lv_results;

    ArrayList<String> resultsList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias de UI
        btn_getAbilities = findViewById(R.id.btn_getAbilities);
        btn_getColor = findViewById(R.id.btn_getSpeciesColor);
        btn_getBaseEvolution = findViewById(R.id.btn_getBaseEvolution);
        et_pokemonInput = findViewById(R.id.et_pokemonInput);
        lv_results = findViewById(R.id.lv_results);

        // Inicialización del ListView y el adaptador
        resultsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultsList);
        lv_results.setAdapter(adapter);

        // Obtener habilidades del Pokémon
        btn_getAbilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = et_pokemonInput.getText().toString().trim().toLowerCase();
                if (pokemonName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Pokémon name", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray abilitiesArray = response.getJSONArray("abilities");
                                    resultsList.clear();

                                    for (int i = 0; i < abilitiesArray.length(); i++) {
                                        JSONObject abilityObject = abilitiesArray.getJSONObject(i).getJSONObject("ability");
                                        String abilityName = abilityObject.getString("name");
                                        resultsList.add("Ability: " + abilityName);
                                    }

                                    adapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Parsing error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(request);
            }
        });

        // Obtener color de la especie del Pokémon
        btn_getColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = et_pokemonInput.getText().toString().trim().toLowerCase();
                if (pokemonName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Pokémon name or ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "https://pokeapi.co/api/v2/pokemon-species/" + pokemonName;
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String color = response.getJSONObject("color").getString("name");
                                    resultsList.clear();
                                    resultsList.add("Species color: " + color);
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Error parsing species data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Error fetching species", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(request);
            }
        });

        // Obtener evolución base del Pokémon
        btn_getBaseEvolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = et_pokemonInput.getText().toString().trim().toLowerCase();
                if (pokemonName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a Pokémon name", Toast.LENGTH_SHORT).show();
                    return;
                }

                String speciesUrl = "https://pokeapi.co/api/v2/pokemon-species/" + pokemonName;
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                JsonObjectRequest speciesRequest = new JsonObjectRequest(Request.Method.GET, speciesUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String evolutionUrl = response.getJSONObject("evolution_chain").getString("url");

                                    // Segunda solicitud: cadena de evolución
                                    JsonObjectRequest evolutionRequest = new JsonObjectRequest(Request.Method.GET, evolutionUrl, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject evolutionResponse) {
                                                    try {
                                                        JSONObject chain = evolutionResponse.getJSONObject("chain");
                                                        String baseSpecies = chain.getJSONObject("species").getString("name");
                                                        resultsList.clear();
                                                        resultsList.add("Base evolution: " + baseSpecies);
                                                        adapter.notifyDataSetChanged();
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(MainActivity.this, "Error parsing evolution chain", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(MainActivity.this, "Error fetching evolution chain", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    queue.add(evolutionRequest);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, "Error parsing species data", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "Error fetching species", Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(speciesRequest);
            }
        });
    }
}
