package com.odhen.api.Authentication

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import java.util.*
import org.springframework.security.core.userdetails.UserDetails
import io.jsonwebtoken.Claims
import java.util.HashMap

object TokenAuthenticationService {

    private const val EXPIRATION_TIME: Long = 86400000 // 24h
    const val TOKEN_PREFIX = "Bearer"
    const val HEADER_STRING = "Authorization"
    private const val SECRET: String = "teknisa"

    fun generateToken(userDetails: UserDetails): String {
        return Jwts.builder()
                .setClaims(HashMap<String, Any>())
                .setSubject(userDetails.username)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()
    }

    fun getJWT(authorizationHeader: String): String{
        return authorizationHeader
                .replaceFirst(TOKEN_PREFIX, "")
                .replaceFirst(" ", "")
    }

    fun validateToken(token: String): Boolean {
        val username = getUsernameFromToken(token)
        return !username.isNullOrEmpty() && !isTokenExpired(token)
    }

    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token) { it.subject }
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token) { it.expiration }
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: (claims: Claims) -> T): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

}