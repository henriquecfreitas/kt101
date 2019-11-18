package com.odhen.api.Logical.User

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository("userRepository")
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByEmailContains(email: String, pageable: Pageable): Page<User>
    fun findAllByEmail(email: String, pageable: Pageable): Page<User>
    fun findAllByEmailContainsAndEmail(email: String, auth: String, pageable: Pageable): Page<User>

    fun existsByEmail(email: String): Boolean?
}