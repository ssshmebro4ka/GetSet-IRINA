package com.example.getset.theme

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database_Helper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "exersicesandroid2.db"
        private const val DATABASE_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase) {
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
    fun getAllData( ): Cursor {
        val db = readableDatabase
        return db.query("exercises", null, null, null, null, null, null)
    }
    fun checkDatabase(): String {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null)
        val tables = mutableListOf<String>()
        while (cursor.moveToNext()) {
            tables.add(cursor.getString(0))
        }
        cursor.close()
        return tables.toString()
    }
    fun loadDataToList(): List<Map<String, Any>> {
        return try {
            val cursor = getAllData()
            val list = mutableListOf<Map<String, Any>>()
            cursor.use {
                while (it.moveToNext()) {
                    val row = mutableMapOf<String, Any>()
                    for (i in 0 until it.columnCount) {
                        val columnName = it.getColumnName(i)
                        val value = when (it.getType(i)) {
                            Cursor.FIELD_TYPE_STRING -> it.getString(i)
                            Cursor.FIELD_TYPE_INTEGER -> it.getInt(i)
                            Cursor.FIELD_TYPE_FLOAT -> it.getDouble(i)
                            else -> null
                        }
                        value?.let { row[columnName] = it }
                    }
                    if (row.isNotEmpty()) {
                        list.add(row)
                    }
                }
            }
            list
        } catch (e: Exception) {
            println("DatabaseHelper ошибка: ${e.message}")
            emptyList()
        }
    }
}