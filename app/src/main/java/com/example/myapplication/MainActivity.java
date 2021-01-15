package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView country_yt, city_yt, temp_yt,time,longitude,latitude,pressure,wind,humidity,sunrise,sunset;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        country_yt = findViewById(R.id.country);
        city_yt = findViewById(R.id.city);
        temp_yt = findViewById(R.id.textView4);
        time = findViewById( R.id.editTextTime );
      //  date = findViewById( R.id.editTextDate );
        imageView = findViewById( R.id.imageView );
        longitude = findViewById(R.id.Longitude);
        latitude = findViewById( R.id.Latitude );
        humidity = findViewById( R.id.Humidity );
        sunrise = findViewById( R.id.Sunrise );
        sunset = findViewById( R.id.Sunset );
        pressure = findViewById( R.id.Pressure );
        wind = findViewById( R.id.Wind );

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findWeather();
            }
        } );

    }

    public void findWeather(){
        String city = editText.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=6d0457a33f9ce4d47ebeae6ce6a8993a";
        StringRequest stringRequest = new StringRequest( Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  // calling api
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //find country
                    JSONObject object1 = jsonObject.getJSONObject("sys");
                    String country_find = object1.getString("country");
                    country_yt.setText(country_find);

                    //find city

                    String city_find = jsonObject.getString("name");
                    city_yt.setText(city_find);

                    //find temperature
                    JSONObject object2 = jsonObject.getJSONObject("main");
                    String temp_find = object2.getString("temp");
                    temp_yt.setText(temp_find+" k");

                    //find date and time
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat std = new SimpleDateFormat("dd//MM//yyyy \nHH:mm:ss");
                    String date = std.format(calendar.getTime());
                    time.setText(date);
                     //find latitude
                    JSONObject object3 = jsonObject.getJSONObject("coord");
                    double lat_find = object3.getDouble("lat");
                    latitude.setText(lat_find+"° N");

                    //find logitude
                    JSONObject object4 = jsonObject.getJSONObject("coord");
                    double lon_find = object4.getDouble("lon");
                    longitude.setText(lon_find+"° E");

                    //find humidity
                    JSONObject object5 = jsonObject.getJSONObject("main");
                    int humidity_find = object5.getInt("humidity");
                    humidity.setText(humidity_find+" %");

                    //find sunrise
                    JSONObject object6 = jsonObject.getJSONObject("sys");
                    String sunrise_find = object6.getString( "sunrise" );
                    sunrise.setText(sunrise_find);

                    //find sunset
                    JSONObject object7 = jsonObject.getJSONObject("sys");
                    String sunset_find = object7.getString( "sunset" );
                    sunset.setText(sunset_find);

                    //find pressure
                    JSONObject object8 = jsonObject.getJSONObject("main");
                    String pressure_find = object8.getString( "pressure" );
                    pressure.setText(pressure_find+" hPa");

                    //find wind
                    JSONObject object9 = jsonObject.getJSONObject("wind");
                    String wind_find = object9.getString( "speed" );
                    wind.setText(wind_find+" km/h");

                    //find image icon
                    JSONArray jsonArray = jsonObject.getJSONArray( "weather" );
                    JSONObject jsonObject1 = jsonArray.getJSONObject( 0 );
                    String img = jsonObject1.getString( "icon" );

                    Picasso.get().load("http://openweathermap.org/img/wn/"+img+"@2x.png").into(imageView);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        } );
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}