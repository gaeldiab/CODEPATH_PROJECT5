package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity()
{

    var getCountryUrl = ""
    var getNameUrl = ""
    var getConditionUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.button)
        var country = findViewById<TextView>(R.id.Country)
        var name = findViewById<TextView>(R.id.Name)
        var condition = findViewById<TextView>(R.id.Condition)

        Log.d("jokeTextUrl","joke text URL set")
        getWeatherUrl()
        getWeather(button,country, name, condition)
    }

    private fun getWeather(button: Button,country: TextView, name: TextView, condition: TextView){
        button.setOnClickListener {
            getWeatherUrl()

            country.text = "Country:     "
            name.text = "Name:        "
            condition.text = "Condition:  "

            if (getCountryUrl== "Overcast"){ getCountryUrl = "Unavailable"}
            if (getNameUrl== "Overcast"){ getNameUrl = "Unavailable"}
            if (getConditionUrl== "Overcast"){ getConditionUrl = "Unavailable"}

            country.text = country.text.toString() + getCountryUrl
            name.text = name.text.toString() + getNameUrl
            condition.text = condition.text.toString() + getConditionUrl
        }
    }

    private fun getWeatherUrl()
    {

        val client = AsyncHttpClient() //instantiates an AsyncHttpClient object w/ variable client
        var input = findViewById<EditText>(R.id.Input)
        var country = input.getText().toString()

        client["https://api.weatherapi.com/v1/current.json?key=838aac7a30f7468eb3645017232803&q="+country, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Weather", "response successful$json") //shows JSON response
                val location_array = json.jsonObject.getJSONObject("location")
                val current_array = json.jsonObject.getJSONObject("current")
                val condition_array = current_array.getJSONObject("condition")

                val country = location_array.getString("country")
                val name = location_array.getString("name")
                val condition = condition_array.getString("text")

                getCountryUrl = country
                getNameUrl = name
                getConditionUrl = condition

            }
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Weather Error", errorResponse)
            }
        }]

    }

}