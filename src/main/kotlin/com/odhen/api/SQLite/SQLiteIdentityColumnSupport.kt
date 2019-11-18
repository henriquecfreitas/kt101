package com.odhen.api.SQLite

import org.hibernate.MappingException
import org.hibernate.dialect.identity.IdentityColumnSupportImpl

class SQLiteIdentityColumnSupport: IdentityColumnSupportImpl() {

    override fun supportsIdentityColumns() = true

    @Throws(MappingException::class)
    override fun getIdentitySelectString(table: String?, column: String?, type: Int)
            = "select last_insert_rowid()"

    @Throws(MappingException::class)
    override fun getIdentityColumnString(type: Int) = "integer"

}