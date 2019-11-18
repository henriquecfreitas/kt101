package com.odhen.api.Security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.http.SessionCreationPolicy
import com.odhen.api.Errors.NullParameterException
import com.odhen.api.Logical.User.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
class WebSecurityConfig(val userService: UserService): WebSecurityConfigurerAdapter() {

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        with (DaoAuthenticationProvider()) {
            setPasswordEncoder(ApiPasswordEncoder)
            setUserDetailsService(userService)
            return this
        }
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Autowired
    @Throws(Exception::class)
    fun confireGlobalUserDetails(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService).passwordEncoder(ApiPasswordEncoder)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        if (http == null) {
            throw NullParameterException(this::class.java.name, "configure", "http")
        }

        with (http.cors().and().csrf()) {
            disable()
        }

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()

                // GRAPHQL ROUTES
                .antMatchers(HttpMethod.POST, "/graphql").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/graphiql",
                        "/vendor/graphiql.min.css",
                        "/vendor/es6-promise.auto.min.js",
                        "/vendor/fetch.min.js",
                        "/vendor/react.min.js",
                        "/vendor/react-dom.min.js",
                        "/vendor/graphiql.min.js",
                        "/subscriptions"
                ).permitAll()

                // DENY OTHERS
                .anyRequest().denyAll()
    }
}