package com.example.covid_19update;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
import java.util.Iterator;

public class CountryDetails extends AppCompatActivity {

    String countryNameForJson;

    int yearLoop,monthLoop,dayLoop;
    int caseByDatesInc = 0;
    int februaryFlag = 0;

    TextView txtCountryNameCountryDetails,txtCasesCountryDetails,txtDeathsCountryDetails,txtRecoveredCountryDetails,txtTodayCasesCountryDetails,txtTodayDeathsCountryDetails,txtTodayRecoveredCountryDetails;
    SimpleArcLoader simpleArcLoaderCountryDetails;
    ScrollView scrollViewCountryDetails;

    TextView txtActiveCasesCountryDetails,txtCriticalCasesCountryDetails,txtFirstPositiveCountryDetails,txtMortalityCountryDetails,txtPopulationCountryDetails;
    SimpleArcLoader simpleArcLoaderCountryDetails2;
    ScrollView scrollViewCountryDetails2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);


        countryNameForJson = getIntent().getStringExtra("myCountryName");

        txtCountryNameCountryDetails = findViewById(R.id.countryNameCountryDetailsId);
        txtCountryNameCountryDetails.setText(countryNameForJson);

        txtCasesCountryDetails = findViewById(R.id.casesCountryDetailsId);
        txtDeathsCountryDetails = findViewById(R.id.deathsCountryDetailsId);
        txtRecoveredCountryDetails = findViewById(R.id.recoveredCountryDetailsId);
        txtTodayCasesCountryDetails = findViewById(R.id.todayCasesCountryDetailsId);
        txtTodayDeathsCountryDetails = findViewById(R.id.todayDeathsCountryDetailsId);
        txtTodayRecoveredCountryDetails = findViewById(R.id.todayRecoveredCountryDetailsId);
        simpleArcLoaderCountryDetails = findViewById(R.id.simpleArcloaderCountryDetailsId);
        scrollViewCountryDetails = findViewById(R.id.scrollViewCountryDetailsId);


        txtActiveCasesCountryDetails = findViewById(R.id.activeCasesCountryDetailsId);
        txtCriticalCasesCountryDetails = findViewById(R.id.criticalCasesCountryDetailsId);
        txtFirstPositiveCountryDetails = findViewById(R.id.firstPositiveCountryDetailsId);
        txtMortalityCountryDetails = findViewById(R.id.mortalityCountryDetailsId);
        txtPopulationCountryDetails = findViewById(R.id.totalPopulationCountryDetailsId);
        simpleArcLoaderCountryDetails2 = findViewById(R.id.simpleArcloaderCountryDetailsId2);
        scrollViewCountryDetails2 = findViewById(R.id.scrollViewCountryDetailsId2);


        fetchCountryDataOverAll();
        //fetchCountryDataHistory();

    }

    private void fetchCountryDataOverAll() {
        String url = "https://corona.lmao.ninja/v2/countries/"+countryNameForJson;
        simpleArcLoaderCountryDetails.start();
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            String casesString = jsonObject.getString("cases");
                            String deathsString = jsonObject.getString("deaths");
                            int casesInt = Integer.parseInt(casesString);
                            int deathsInt = Integer.parseInt(deathsString);
                            double mortalityDecimal = (double) deathsInt/(double)casesInt;
                            DecimalFormat df = new DecimalFormat("###.###");
                            String mortality = df.format(mortalityDecimal);



                            txtCountryNameCountryDetails.setText(jsonObject.getString("country"));
                            txtCasesCountryDetails.setText(jsonObject.getString("cases"));
                            txtDeathsCountryDetails.setText(jsonObject.getString("deaths"));
                            txtRecoveredCountryDetails.setText(jsonObject.getString("recovered"));
                            txtTodayCasesCountryDetails.setText(jsonObject.getString("todayCases"));
                            txtTodayDeathsCountryDetails.setText(jsonObject.getString("todayDeaths"));
                            txtTodayRecoveredCountryDetails.setText(jsonObject.getString("todayRecovered"));

                            txtActiveCasesCountryDetails.setText(jsonObject.getString("active"));
                            txtCriticalCasesCountryDetails.setText(jsonObject.getString("critical"));
                            txtPopulationCountryDetails.setText(jsonObject.getString("population"));
                            txtMortalityCountryDetails.setText(mortality);

                            simpleArcLoaderCountryDetails.stop();
                            simpleArcLoaderCountryDetails.setVisibility(View.GONE);
                            scrollViewCountryDetails.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            simpleArcLoaderCountryDetails.stop();
                            simpleArcLoaderCountryDetails.setVisibility(View.GONE);
                            scrollViewCountryDetails.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                simpleArcLoaderCountryDetails.stop();
                simpleArcLoaderCountryDetails.setVisibility(View.GONE);
                scrollViewCountryDetails.setVisibility(View.VISIBLE);

                Toast.makeText(CountryDetails.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }



    /*
    private void fetchCountryDataHistory() {


        String url = "https://corona.lmao.ninja/v2/historical/"+countryNameForJson;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            String[] caseByDates = new String[800];

                            for(yearLoop = 2020; yearLoop<=2021; yearLoop++)
                                {
                                    for(monthLoop = 1; monthLoop<=12; monthLoop++)
                                    {
                                        februaryFlag = 0;

                                        for(dayLoop = 1; dayLoop<=31; dayLoop++)
                                        {
                                            caseByDates[caseByDatesInc] = monthLoop+"/"+dayLoop+"/"+yearLoop;
                                            caseByDatesInc++;


                                            if(dayLoop == 30){
                                            if(monthLoop == 4 || monthLoop == 6 || monthLoop == 9 || monthLoop == 11)
                                                break;
                                            }

                                            if(februaryFlag == 1)
                                                break;

                                            if(monthLoop == 2 && dayLoop == 28)
                                            {
                                                if(yearLoop%4 == 0)
                                                {
                                                    februaryFlag = 1;
                                                }
                                                else
                                                    break;;
                                            }

                                        }

                                    }
                                }



                            JSONObject jsonObject = new JSONObject(response.toString());
                            String jsontimeline = jsonObject.getString("timeline");
                            JSONObject jsonObjectTimeline = new JSONObject(jsontimeline);
                            String jsonCases = jsonObjectTimeline.getString("cases");
                            JSONObject jsonObjectCases = new JSONObject(jsonCases);



                            //txtdate2.setText(jsonObject.getString("deaths"));
                            //caseByDates[0] = jsonObjectCases.getString("3/9/21");



                            //Date Key Fetch:

                            int length = jsonObjectCases.length();

                            Iterator<String> iterator = jsonObjectCases.keys();
                            String[] dateKey = new String[length];
                            int caseNo = 0;
                            while (iterator.hasNext()) {
                                dateKey[caseNo] = iterator.next();
                                caseNo += 1;
                            }

                            //Date Key Fetch/

                            //   String test = dateKey[0] +" "+dateKey[1]+" "+dateKey[2];
                          //  txtDate1.setText(test);



                        } catch (JSONException e) {
                            e.printStackTrace();

                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CountryDetails.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

     */


}