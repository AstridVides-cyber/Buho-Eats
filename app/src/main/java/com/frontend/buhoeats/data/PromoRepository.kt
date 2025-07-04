package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.Promo

interface PromoRepository {
    fun getPromosForRestaurant(restaurantId: String): List<Promo>
    fun getAllPromos(): List<Promo>
    fun addPromoToRestaurant(restaurantId: String, promo: Promo)
    fun removePromo(promo: Promo)
    fun updatePromo(updatedPromo: Promo)
}

