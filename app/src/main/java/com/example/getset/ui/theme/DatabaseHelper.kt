package com.example.getset.ui.theme

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "exercisesandroid2.db"
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
        val cursor = db.rawQuery("SELECT exercises FROM sqlite_master WHERE type='table'", null)
        val tables = mutableListOf<String>()
        while (cursor.moveToNext()) {
            tables.add(cursor.getString(0))
        }
        cursor.close()
        return tables.toString()
    }
    fun loadDataToList(): List<Map<String, Any>> {
        val cursor = getAllData()
        val list = mutableListOf<Map<String, Any>>()
        cursor.use {
            while (it.moveToNext()) {
                val row = mutableMapOf<String, Any>()
                for (i in 0 until it.columnCount) {
                    when (it.getType(i)) {
                        Cursor.FIELD_TYPE_STRING -> row[it.getColumnName(i)] = it.getString(i)
                        Cursor.FIELD_TYPE_INTEGER -> row[it.getColumnName(i)] = it.getInt(i)
                        Cursor.FIELD_TYPE_FLOAT -> row[it.getColumnName(i)] = it.getDouble(i)
                    }
                }
                list.add(row)
            }
        }
        return list
    }
}