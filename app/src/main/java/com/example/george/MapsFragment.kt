package com.example.george

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.george.databinding.FragmentDataReadBinding
import com.example.george.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var gMap : GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->

        gMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        var marker1 : Marker? = googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        marker1?.tag = "Sydney"

        val rovaniemi = LatLng(66.50335838035951, 25.725845519228194)
        var marker2 : Marker? = googleMap.addMarker(MarkerOptions().position(rovaniemi).title("Rovaniemi"))
        marker2?.tag = "Rovaniemi"

        // siirretään alkunäkymä
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rovaniemi, 15f))

        googleMap.setOnMarkerClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.checkBoxZoomControls.setOnCheckedChangeListener { compoundButton, b ->
            gMap.uiSettings.isZoomControlsEnabled = b
        }

        binding.radioButtonNormalMap.setOnCheckedChangeListener { compoundButton, b ->
            gMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        }

        binding.radioButtonHybridMap.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                gMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onMarkerClick(p0: Marker): Boolean {

        Log.d("TESTI", "MARKKERI CLICK! JES!")

        // kaivetaan marker-oliosta klikatun markkerin nimi ja koordinaatit
        Log.d("TESTI", p0.tag.toString())

        Log.d("TESTI", p0.position.latitude.toString())
        Log.d("TESTI", p0.position.longitude.toString())

        val latitude : Float = p0.position.latitude.toFloat()
        val longitude : Float = p0.position.longitude.toFloat()

        // tähän action ja navigate, parametreiksi  ylläolevat latitude ja longitude -muuttujat
        val action = MapsFragmentDirections.actionMapsFragmentToCityWeatherFragment(latitude, longitude)
        findNavController().navigate(action)

        // OnMarkerClick vaatii että lopuksi palautetaan Boolean
        return false
    }
}