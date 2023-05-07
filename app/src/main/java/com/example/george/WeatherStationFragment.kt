package com.example.george

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.george.databinding.FragmentWeatherStationBinding
import com.github.anastr.speedviewlib.SpeedView
import com.google.gson.GsonBuilder
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
import java.util.*

class WeatherStationFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentWeatherStationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // muuttujat BuildConfigiin myöhemmin

    // this could be in BuildConfig (local.properties -file) too
    // e.g. var MQTT_URL = BuildConfig.MQTT_URL
    var MQTT_URL = "i5u4t3.messaging.internetofthings.ibmcloud.com"
    var MQTT_TOPIC = "iot-2/type/ws-10/id/3001/evt/data/fmt/json"
    var MQTT_USERNAME = "a-i5u4t3-kg2otp2blv"
    var MQTT_PASSWORD = "iJt_MQqHikrls*)Cpc"

    // var MQTT_CLIENT_ID = BuildConfig.MQTT_CLIENT_ID + UUID.randomUUID().toString()
    var MQTT_CLIENT_ID = "a:i5u4t3:" + UUID.randomUUID().toString()

    private lateinit var client: Mqtt3AsyncClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherStationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // version 3, IBM Cloud, weather station
        client = MqttClient.builder()
            .useMqttVersion3()
            .sslWithDefaultConfig()
            .identifier(MQTT_CLIENT_ID)
            .serverHost(MQTT_URL)
            .serverPort(8883)
            .buildAsync()

        client.connectWith()
            .simpleAuth()
            .username(MQTT_USERNAME)
            .password(MQTT_PASSWORD.toByteArray())
            .applySimpleAuth()
            .send()
            .whenComplete { connAck: Mqtt3ConnAck?, throwable: Throwable? ->
                if (throwable != null) {
                    Log.d("ADVTECH", "Connection failure.")
                } else {
                    // Setup subscribes or start publishing
                    subscribeToTopic()
                }
            }

        return root
    }

    fun subscribeToTopic()
    {
        client.subscribeWith()
            .topicFilter(MQTT_TOPIC)
            .callback { publish ->
                // this callback runs everytime your code receives new data payload
                var result = String(publish.getPayloadAsBytes())
                Log.d("ADVTECH", result)

                // laitetaan raakadata TextViewiin, ei toimi suoraan
                // koska tämä callback ei pääse käyttöliittymään käsiksi (eri thread)
                //binding.textViewWeatherStationTemperature.text = result

            }
            .send()
            .whenComplete { subAck, throwable ->
                if (throwable != null) {
                    // Handle failure to subscribe
                    Log.d("ADVTECH", "Subscribe failed.")
                } else {
                    // Handle successful subscription, e.g. logging or incrementing a metric
                    Log.d("ADVTECH", "Subscribed!")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        // hyvä tapa sammuttaa MQTT-client kun poistutaan fragmentista
        client.disconnect()
    }
}