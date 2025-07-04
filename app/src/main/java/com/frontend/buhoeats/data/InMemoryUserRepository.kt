package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.User

class InMemoryUserRepository : UserRepository {
    override fun getUsers(): List<User> {
        return InMemoryUserDataSource.getUsers()
    }

    override fun getUserByEmail(email: String): User? {
        return InMemoryUserDataSource.getUsers().find { it.email.equals(email, ignoreCase = true) }
    }

    override fun registerUser(newUser: User): Boolean {
        val users = InMemoryUserDataSource.getUsers().toMutableList()
        if (users.any { it.email.equals(newUser.email, ignoreCase = true) }) {
            return false
        }
        users.add(newUser)
        InMemoryUserDataSource.setUsers(users)
        return true
    }

    override fun updateUser(user: User): Boolean {
        val userList = InMemoryUserDataSource.getUsers().toMutableList()
        val userIndex = userList.indexOfFirst { it.id == user.id }
        if (userIndex != -1) {
            userList[userIndex] = user
            InMemoryUserDataSource.setUsers(userList)
            return true
        }
        return false
    }

    override fun assignRoleToUser(email: String, newRole: String): Boolean {
        val users = InMemoryUserDataSource.getUsers().toMutableList()
        val index = users.indexOfFirst { it.email.trim().equals(email.trim(), ignoreCase = true) }
        if (index != -1) {
            val updatedUser = users[index].copy(rol = newRole)
            users[index] = updatedUser
            InMemoryUserDataSource.setUsers(users)
            return true
        }
        return false
    }

    override fun getUserById(userId: String): User? {
        return InMemoryUserDataSource.getUserById(userId)
    }

    override fun blockUserFromRestaurant(userId: String, restaurantId: String) {
        InMemoryUserDataSource.blockUserFromRestaurant(userId, restaurantId)
    }

    override fun unblockUserFromRestaurant(userId: String, restaurantId: String) {
        InMemoryUserDataSource.unblockUserFromRestaurant(userId, restaurantId)
    }
}
