package com.example.george

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.george.databinding.FragmentCityWeatherBinding
import com.example.george.datatypes.cityweather.CityWeather
import com.google.gson.GsonBuilder



class CityWeatherFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentCityWeatherBinding? = null

    val args: CityWeatherFragmentArgs by navArgs()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.d("TESTI", args.latitude.toString())
        Log.d("TESTI", args.longitude.toString())

        getWeather()

        // the binding -object allows you to access views in the layout, textviews etc.

        return root
    }

    fun getWeather() {
        val API_KEY = BuildConfig.OPENWEATHER_API_KEY

        val JSON_URL : String = "https://api.openweathermap.org/data/2.5/weather?lat=${args.latitude}&lon=${args.longitude}&units=metric&appid=${API_KEY}"

        val gson = GsonBuilder().setPrettyPrinting().create()

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, JSON_URL,
            Response.Listener { response ->

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                Log.d("TESTI", response)

                var item : CityWeather = gson.fromJson(response, CityWeather::class.java)

                Log.d("TESTI", "Lämpötila: ${item.main?.temp} C")

                // asetetaan lämpötila esim. TextViewiin, tässä tapauksessa TextViewin
                // id = textView_city_temperature
                // binding.textViewCityTemperature.text = item.main?.temp.toString() + " C"

            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("TESTI", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                // basic headers for the data
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }

        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        // if using this in an activity, use "this" instead of "context"
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}