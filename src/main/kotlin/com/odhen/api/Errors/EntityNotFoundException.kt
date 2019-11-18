package com.odhen.api.Errors

import org.springframework.util.StringUtils

class EntityNotFoundException(clazz: Class<*>, vararg searchParamsMap: String) : RuntimeException (
        generateMessage(clazz.simpleName, toMap(String::class.java, String::class.java, *searchParamsMap))
) {
    companion object {
        private fun generateMessage(entity: String, searchParams: Map<String, String>): String {
            return "${StringUtils.capitalize(entity)} was not found for parameters $searchParams"
        }

        private fun <K, V> toMap(keyType: Class<K>, valueType: Class<V>, vararg entries: Any): Map<K, V> {
            if (entries.size % 2 != 0) {
                throw IllegalArgumentException("Invalid entries")
            }

            val map: MutableMap<K, V> = mutableMapOf()
            for (i in 0 until entries.size step 2) {
                val key = keyType.cast(entries[i])
                val value = valueType.cast(entries[i+1])
                map[key] = value
            }
            return map
        }
    }
}