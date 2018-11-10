package com.sevcabel.sevcabelport.maps

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.fragment_maps.view.*
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker


class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    lateinit var mapView : MapView
    var admin = false
    lateinit var map : GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_maps, container, false)
        mapView = view.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)

        mapView.onResume()

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mapView.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        SevcabelApplication.setMap(googleMap)
        map = googleMap

        mapSetting()

        floatingActionAdd.setOnClickListener {
            startActivity(Intent(activity, AddMarkerActivity::class.java))
        }

        floatingActionAdmin.setOnClickListener {
            admin = !admin
            for (mark : MyMarker in SevcabelApplication.getMarkers()) {
                mark.marker?.isDraggable = admin
            }
        }

        map.setOnMarkerClickListener(this)
        map.setOnMarkerDragListener(this)
    }

    fun mapSetting() {
        map.setMinZoomPreference(18.0f)
        map.setMaxZoomPreference(21.0f)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(59.924331, 30.241246), 18f))
        map.setLatLngBoundsForCameraTarget(LatLngBounds(LatLng(59.923733, 30.239277), LatLng(59.924376, 30.242883)))
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        if(!admin) {
            for(mark in SevcabelApplication.getMarkers()) {
                if(mark.marker?.position == p0.position) {
                    SevcabelApplication.setMyMarker(mark)
                }
            }
            startActivity(Intent(activity, MarkerActivity::class.java))
        }

        return true
    }

    override fun onMarkerDragEnd(p0: Marker?) {

    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {
        map.moveCamera(CameraUpdateFactory.newLatLng(p0!!.position))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
