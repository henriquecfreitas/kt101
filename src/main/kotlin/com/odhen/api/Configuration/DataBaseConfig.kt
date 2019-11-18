//package com.odhen.api.Configuration
//
//import com.odhen.api.SQLite.SQLiteConfig
//import javax.sql.DataSource
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.core.env.Environment
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
//
//@Configuration
//class DataBaseConfig(
//        val env: Environment,
//        //val dataSource: DataSource,
//        val sqLiteConfig: SQLiteConfig
//) {
//
//    private val isMySQL = env.getProperty("odhen.api.database") == "mysql"
//    private val isSQLServer = env.getProperty("odhen.api.database") == "sqlserver"
//    private val isSQLite = env.getProperty("odhen.api.database") == "sqlite"
//
//    @Bean
//    fun dataSource(): DataSource {
//        return if (isSQLite) {
//            sqLiteConfig.dataSource()
//        } else {
//            sqLiteConfig.dataSource()
//        }
//    }
//
//    @Bean
//    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
//        with (LocalContainerEntityManagerFactoryBean()) {
//            dataSource = dataSource()
//            jpaVendorAdapter = HibernateJpaVendorAdapter()
//            setPackagesToScan("com.odhen.api.Logical")
//
//            if (isSQLite) {
//                setJpaProperties(sqLiteConfig.properties())
//            }
//
//            return this
//        }
//    }
//
//}