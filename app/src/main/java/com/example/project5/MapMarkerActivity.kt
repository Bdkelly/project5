package com.example.project5

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MapsMarkerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    private lateinit var fabAddMarker: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var appToolbar: Toolbar

    private val specificLocation = LatLng(40.7128, -74.0060) // Example: New York City
    private val locationName = "New York City, NY"
    private var markerCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        appToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(appToolbar)

        supportActionBar?.title = "My Interactive Map"

        fabAddMarker = findViewById(R.id.fab_add_marker)
        fabAddMarker.setOnClickListener {
            // Action to perform when FAB is clicked
            if (::googleMap.isInitialized) { // Check if the map is ready
                val currentMapCenter =
                    googleMap.cameraPosition.target // Get current center of the map
                googleMap.addMarker(
                    MarkerOptions()
                        .position(currentMapCenter)
                        .title("Added Marker ${++markerCount}")
                )
                Toast.makeText(this, "Marker added at map center!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Map is not ready yet.", Toast.LENGTH_SHORT).show()
            }
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(map: GoogleMap) {
        googleMap = map // Assign the ready GoogleMap instance to your class variable
        googleMap.addMarker(MarkerOptions().position(specificLocation).title("Marker in $locationName"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(specificLocation, 10f))
        googleMap.uiSettings.isZoomControlsEnabled = true
        Toast.makeText(this, "$locationName displayed on the map!", Toast.LENGTH_LONG).show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {

                Toast.makeText(this, "Navigation icon clicked!", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
