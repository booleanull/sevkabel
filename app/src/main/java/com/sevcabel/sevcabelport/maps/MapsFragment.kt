package com.sevcabel.sevcabelport.maps

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.sevcabel.sevcabelport.R
import com.sevcabel.sevcabelport.utils.SevcabelApplication
import kotlinx.android.synthetic.main.fragment_maps.*


class MapsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    lateinit var mapView: MapView
    lateinit var database: FirebaseDatabase
    lateinit var rep : DatabaseReference
    var admin = false
    lateinit var mark : MyMarker

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

        val database = FirebaseDatabase.getInstance()
        rep = database.reference.child("markers")
        rep.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val m = p0.getValue(MyMarker::class.java)!!
                SevcabelApplication.getMarkers().add(m)
                SevcabelApplication.getMarks().add(SevcabelApplication.getMap().addMarker(MarkerOptions().position(LatLng(m.markerPositionX, m.markerPositionY))))
                SevcabelApplication.updateMarkers()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val m = p0.getValue(MyMarker::class.java)!!
                SevcabelApplication.getMarkers().indexOf(m)
                val s = SevcabelApplication.getMarkers().indexOf(m)
                SevcabelApplication.getMarkers().removeAt(s)
                SevcabelApplication.getMarks().removeAt(s)
                SevcabelApplication.updateMarkers()
            }
        })

        mapSetting()

        floatingActionAdd.setOnClickListener {
            startActivity(Intent(activity, AddMarkerActivity::class.java))
        }

        floatingActionAdmin.setOnClickListener {
            admin = !admin
            for (mark: Marker in SevcabelApplication.getMarks()) {
                mark.isDraggable = admin
            }
        }

        SevcabelApplication.getMap().setOnMarkerClickListener(this)
        SevcabelApplication.getMap().setOnMarkerDragListener(this)
    }

    fun mapSetting() {
        SevcabelApplication.getMap().setMinZoomPreference(18.0f)
        SevcabelApplication.getMap().setMaxZoomPreference(21.0f)
        SevcabelApplication.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(59.924331, 30.241246), 18f))
        SevcabelApplication.getMap().setLatLngBoundsForCameraTarget(LatLngBounds(LatLng(59.923733, 30.239277), LatLng(59.924376, 30.242883)))
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        if (!admin) {
            for (mark in SevcabelApplication.getMarkers()) {
                if (LatLng(mark.markerPositionX, mark.markerPositionY) == p0.position) {
                    SevcabelApplication.setMyMarker(mark)
                }
            }
            startActivity(Intent(activity, MarkerActivity::class.java))
        }
        return true
    }

    override fun onMarkerDragEnd(p0: Marker?) {
        mark.markerPositionX = p0!!.position.latitude
        mark.markerPositionY = p0!!.position.longitude
        rep.child(mark.id.toString()).setValue(mark)
    }

    override fun onMarkerDragStart(p0: Marker?) {
        for(m in SevcabelApplication.getMarks())
            if(m == p0)
                mark = SevcabelApplication.getMarkers()[SevcabelApplication.getMarks().indexOf(m)]
    }

    override fun onMarkerDrag(p0: Marker?) {
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
