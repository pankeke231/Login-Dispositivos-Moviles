package com.example.login;

import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

    Button btn_cityID, btn_getWeatherByID, btn_getWeatherByName;
    EditText et_dataInput;
    ListView lv_weatherReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias de UI
        btn_cityID = findViewById(R.id.btn_getCityID);
        btn_getWeatherByID = findViewById(R.id.btn_getWeatherByCityID);
        btn_getWeatherByName = findViewById(R.id.btn_gwtWeatherByCityName);
        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherReport = findViewById(R.id.lv_weatherReports);

        // Listener del botón principal
        btn_cityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pokemonName = et_dataInput.getText().toString().trim().toLowerCase();
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
                                    StringBuilder abilitiesText = new StringBuilder();

                                    for (int i = 0; i < abilitiesArray.length(); i++) {
                                        JSONObject abilityObject = abilitiesArray.getJSONObject(i).getJSONObject("ability");
                                        String abilityName = abilityObject.getString("name");
                                        abilitiesText.append(abilityName).append("\n");
                                    }

                                    Toast.makeText(MainActivity.this, "Abilities:\n" + abilitiesText, Toast.LENGTH_LONG).show();

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

        btn_getWeatherByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You clicked me2", Toast.LENGTH_SHORT).show();
            }
        });

        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You typed " + et_dataInput.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
