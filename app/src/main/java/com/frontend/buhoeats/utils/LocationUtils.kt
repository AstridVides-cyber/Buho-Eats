package com.frontend.buhoeats.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine

@SuppressLint("MissingPermission")
suspend fun getCurrentLocation(context: Context, fused: FusedLocationProviderClient): Location? {
    return suspendCancellableCoroutine { cont ->
        fused.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location: Location? ->
            cont.resume(location, null)
        }.addOnFailureListener {
            cont.resume(null, null)
        }
    }
}
