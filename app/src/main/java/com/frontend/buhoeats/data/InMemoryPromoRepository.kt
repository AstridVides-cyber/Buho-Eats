package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.Promo

class InMemoryPromoRepository : PromoRepository {
    override fun getPromosForRestaurant(restaurantId: String): List<Promo> {
        return InMemoryUserDataSource.getRestaurantById(restaurantId)?.promos ?: emptyList()
    }

    override fun getAllPromos(): List<Promo> {
        return InMemoryUserDataSource.getRestaurants().flatMap { it.promos }
    }

    override fun addPromoToRestaurant(restaurantId: String, promo: Promo) {
        InMemoryUserDataSource.addPromoToRestaurant(restaurantId, promo)
    }

    override fun removePromo(promo: Promo) {
        InMemoryUserDataSource.removePromo(promo)
    }

    override fun updatePromo(updatedPromo: Promo) {
        InMemoryUserDataSource.updatePromo(updatedPromo)
    }
}

