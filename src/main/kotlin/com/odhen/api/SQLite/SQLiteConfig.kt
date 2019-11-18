package com.odhen.api.SQLite

import org.springframework.core.env.Environment
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.util.*
import javax.sql.DataSource

@Configuration
class SQLiteConfig(val env: Environment) {

    fun getProperty(key: String): String? = env.getProperty("odhen.api.sqlite.$key")

     fun dataSource(): DataSource {
        with (DriverManagerDataSource()) {
            setDriverClassName(getProperty("driverClassName") ?: "")
            url = getProperty("url")
            username = getProperty("username")
            password = getProperty("password")
            return this
        }
    }

    fun properties(): Properties {
        with (Properties()) {
            setProperty("hibernate.hbm2ddl.auto", this@SQLiteConfig.getProperty("hibernate.hbm2ddl.auto"))
            setProperty("hibernate.dialect", this@SQLiteConfig.getProperty("hibernate.dialect"))
            setProperty("hibernate.show_sql", this@SQLiteConfig.getProperty("hibernate.show_sql"))
            return this
        }
    }

}