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
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.utils.Translations

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun Map(
    restaurants: List<Restaurant>,
    focusLocation: GeoPoint? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var locationRequested by remember { mutableStateOf(false) }
    val mapViewState = remember { mutableStateOf<MapView?>(null) }

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

    LaunchedEffect(focusLocation) {
        focusLocation?.let { focus ->
            mapViewState.value?.controller?.setCenter(focus)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        when {
            !permissionState.status.isGranted -> {
                Text(Translations.t("location_permission_request"))
            }
            currentLocation == null -> {
                Text(Translations.t("location_loading"))
            }
            else -> {
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                        .shadow(4.dp, RoundedCornerShape(12.dp)),
                    factory = { ctx ->
                        MapView(ctx).apply {
                            setMultiTouchControls(true)
                            controller.setZoom(20.0)
                            val defaultPoint = GeoPoint(currentLocation!!.latitude, currentLocation!!.longitude)
                            controller.setCenter(defaultPoint)

                            restaurants.forEach { rest ->
                                overlays.add(Marker(this).apply {
                                    position = GeoPoint(rest.latitud, rest.longitud)
                                    title = rest.name
                                })
                            }

                            overlays.add(Marker(this).apply {
                                position = defaultPoint
                                title = Translations.t("you_are_here")
                            })

                            mapViewState.value = this
                        }
                    },
                    update = { map ->
                        map.invalidate()
                    }
                )
            }
        }
    }
}
