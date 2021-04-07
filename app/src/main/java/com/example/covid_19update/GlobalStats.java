package com.example.covid_19update;

import androidx.appcompat.app.AppCompatActivity;

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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class GlobalStats extends AppCompatActivity {

    TextView txtCases,txtDeaths,txtRecovered,txtActive,txtCritical,txtTodayCases,txtTodayDeaths,txtTodayRecovered,txtAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    PieChart pieChart;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_stats);


        txtCases = findViewById(R.id.casesGlobalStatsId);
        txtDeaths = findViewById(R.id.deathsGlobalStatsId);
        txtRecovered = findViewById(R.id.recoveredGlobalStatsId);
        txtActive = findViewById(R.id.activeGlobalStatsId);
        txtCritical = findViewById(R.id.criticalGlobalStatsId);
        txtTodayCases = findViewById(R.id.todayCasesGlobalStatsId);
        txtTodayDeaths = findViewById(R.id.todayDeathsGlobalStatsId);
        txtTodayRecovered = findViewById(R.id.todayRecoveredGlobalStatsId);
        txtAffectedCountries = findViewById(R.id.affectedCountriesGlobalStatsId);

        simpleArcLoader = findViewById(R.id.simpleArcloaderId);
        scrollView = findViewById(R.id.scrollGlobalStatsId);
        pieChart = findViewById(R.id.piechartGlobalStatsId);

        fetchData();
    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all";
        simpleArcLoader.start();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            txtCases.setText(jsonObject.getString("cases"));
                            txtDeaths.setText(jsonObject.getString("deaths"));
                            txtRecovered.setText(jsonObject.getString("recovered"));
                            txtActive.setText(jsonObject.getString("active"));
                            txtCritical.setText(jsonObject.getString("critical"));
                            txtTodayCases.setText(jsonObject.getString("todayCases"));
                            txtTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            txtTodayRecovered.setText(jsonObject.getString("todayRecovered"));
                            txtAffectedCountries.setText(jsonObject.getString("affectedCountries"));


                            pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(txtDeaths.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(txtRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(txtActive.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.startAnimation();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);

                Toast.makeText(GlobalStats.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}