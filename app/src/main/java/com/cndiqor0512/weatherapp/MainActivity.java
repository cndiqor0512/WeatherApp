package com.cndiqor0512.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView t1_temp, t2_city, t3_description, t4_date,variable;
    EditText EditText;
    Button button;
    ImageView ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView = (ImageView)findViewById(R.id.imageView);
        t1_temp = (TextView)findViewById(R.id.textView);
        t2_city = (TextView)findViewById(R.id.textView3);
        t3_description = (TextView)findViewById(R.id.textView4);
        t4_date = (TextView)findViewById(R.id.textView2);
        EditText = (EditText)findViewById(R.id.location);
        button = (Button)findViewById(R.id.button);
        variable = (TextView)findViewById(R.id.String);

        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String location = "서울";
                String query;
                location = EditText.getText().toString();
                variable.setText(location);
                query = URLEncoder.encode(variable.getText().toString());
                find_weather(query);
            }
        }) ;


    }

    public void find_weather(String location){
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+location+",korea&appid=d8b99e9c7a560d149a1bf4b121242aaa&units=Imperial";


        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                    // t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE=MM-dd");
                    String formatted_date = sdf.format(calendar.getTime());

                    t4_date.setText(formatted_date);

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 32) / 1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    t1_temp.setText(String.valueOf(i));


                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){

            }
        }
                );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);

    }
}
