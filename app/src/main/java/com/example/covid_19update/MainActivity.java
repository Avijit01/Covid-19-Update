package com.example.covid_19update;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView cardGlobal,cardCountry;
    TextView txtCountryName,txtCasesMain,txtDeathsMain,txtRecoveredMain,txtTodayCasesMain,txtTodayDeathsMain,txtTodayRecoveredMain;
    SimpleArcLoader simpleArcLoaderMain;
    ScrollView scrollViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardGlobal = findViewById(R.id.cardGlobalStatsId);
        cardCountry = findViewById(R.id.cardCountryStatsId);

        cardCountry.setOnClickListener(this);
        cardGlobal.setOnClickListener(this);

        txtCountryName = findViewById(R.id.countryNameId);
        txtCasesMain = findViewById(R.id.casesMainId);
        txtDeathsMain = findViewById(R.id.deathsMainId);
        txtRecoveredMain = findViewById(R.id.recoveredMainId);
        txtTodayCasesMain = findViewById(R.id.todayCasesMainId);
        txtTodayDeathsMain = findViewById(R.id.todayDeathsMainId);
        txtTodayRecoveredMain = findViewById(R.id.todayRecoveredMainId);

        simpleArcLoaderMain = findViewById(R.id.simpleArcloaderMainId);
        scrollViewMain = findViewById(R.id.scrollMainId);

        fetchDataMain();
    }

    private void fetchDataMain() {
        String url = "https://corona.lmao.ninja/v2/countries/bangladesh";
        simpleArcLoaderMain.start();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            txtCountryName.setText(jsonObject.getString("country"));
                            txtCasesMain.setText(jsonObject.getString("cases"));
                            txtDeathsMain.setText(jsonObject.getString("deaths"));
                            txtRecoveredMain.setText(jsonObject.getString("recovered"));
                            txtTodayCasesMain.setText(jsonObject.getString("todayCases"));
                            txtTodayDeathsMain.setText(jsonObject.getString("todayDeaths"));
                            txtTodayRecoveredMain.setText(jsonObject.getString("todayRecovered"));

                            simpleArcLoaderMain.stop();
                            simpleArcLoaderMain.setVisibility(View.GONE);
                            scrollViewMain.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            simpleArcLoaderMain.stop();
                            simpleArcLoaderMain.setVisibility(View.GONE);
                            scrollViewMain.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoaderMain.stop();
                simpleArcLoaderMain.setVisibility(View.GONE);
                scrollViewMain.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cardGlobalStatsId)
        {
            Intent intent = new Intent(this,GlobalStats.class);
            startActivity(intent);
        }

        if(view.getId() == R.id.cardCountryStatsId)
        {
            Toast.makeText(this, "Work In Progress", Toast.LENGTH_SHORT).show();
        }

    }
}