package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.models.Promo

class PromoViewModel : ViewModel() {
    private val _promos = mutableStateListOf<Promo>()
    val promos: List<Promo> get() = _promos

    private var isLoaded = false

    fun loadPromos(promos: List<Promo>) {
        if (!isLoaded) {
            _promos.clear()
            _promos.addAll(promos)
            isLoaded = true
        }
    }

    fun deletePromo(promo: Promo) {
        _promos.remove(promo)
    }
}
