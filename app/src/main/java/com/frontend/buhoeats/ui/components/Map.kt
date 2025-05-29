package com.frontend.buhoeats.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.preference.PreferenceManager
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun MapScreen() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var locationRequested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }


    LaunchedEffect(permissionState.status.isGranted, locationRequested) {
        if (permissionState.status.isGranted && !locationRequested) {
            locationRequested = true
            coroutineScope.launch {
                currentLocation = com.frontend.buhoeats.utils.getCurrentLocation(context, fusedLocationClient)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        if (!permissionState.status.isGranted) {
            Text("Solicitando permiso de ubicación...")
        } else if (currentLocation == null) {
            Text("Cargando ubicación...")
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(15.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .shadow(4.dp, RoundedCornerShape(12.dp))
            ){
            AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                factory = { ctx ->
                    val mapView = MapView(ctx).apply {
                        setMultiTouchControls(true)
                        controller.setZoom(18.0)
                    }
                    val geoPoint = GeoPoint(currentLocation!!.latitude, currentLocation!!.longitude)
                    mapView.controller.setCenter(geoPoint)
                    val marker = Marker(mapView).apply {
                        position = geoPoint
                    }
                    mapView.overlays.add(marker)
                    mapView

                }
            )}
        }
    }
}
