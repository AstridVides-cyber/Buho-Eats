package com.frontend.buhoeats.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.preference.PreferenceManager
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.frontend.buhoeats.ui.theme.AppColors
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.frontend.buhoeats.utils.Translations

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun LocationPickerMap(
    lat: Double,
    lon: Double,
    onLocationChange: (Double, Double) -> Unit,
    label: String = Translations.t("select_location")
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var currentLocation by remember { mutableStateOf(GeoPoint(lat, lon)) }
    val mapViewState = remember { mutableStateOf<MapView?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted && lat == 0.0 && lon == 0.0) {
            coroutineScope.launch {
                val loc = com.frontend.buhoeats.utils.getCurrentLocation(context, fusedLocationClient)
                loc?.let {
                    currentLocation = GeoPoint(it.latitude, it.longitude)
                    onLocationChange(it.latitude, it.longitude)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label, // ← ahora dinámico
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.texto,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(2.dp, Color(0xFF588B8B), RoundedCornerShape(12.dp))
                .shadow(6.dp, RoundedCornerShape(12.dp))
        ) {
            AndroidView(
                modifier = Modifier.matchParentSize(),
                factory = { ctx ->
                    MapView(ctx).apply {
                        setMultiTouchControls(true)
                        controller.setZoom(18.0)
                        controller.setCenter(currentLocation)
                        val marker = Marker(this).apply {
                            position = currentLocation
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = Translations.t("local_location")
                            isDraggable = true
                            setOnMarkerDragListener(object : Marker.OnMarkerDragListener {
                                override fun onMarkerDrag(marker: Marker?) {}
                                override fun onMarkerDragEnd(marker: Marker?) {
                                    marker?.let {
                                        onLocationChange(it.position.latitude, it.position.longitude)
                                    }
                                }
                                override fun onMarkerDragStart(marker: Marker?) {}
                            })
                        }
                        overlays.add(marker)
                        mapViewState.value = this
                    }
                },
                update = { map ->
                    val marker = map.overlays.filterIsInstance<Marker>().firstOrNull()
                    marker?.position = GeoPoint(lat, lon)
                    map.controller.setCenter(GeoPoint(lat, lon))
                    map.invalidate()
                }
            )
        }
    }
}
