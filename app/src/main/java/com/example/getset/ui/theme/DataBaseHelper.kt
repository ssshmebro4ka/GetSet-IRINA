package com.example.getset.ui.theme


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import org.json.JSONArray
import java.io.InputStream

class ExerciseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "exercises.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "exercises"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_SHORT_DESC = "short_description"
        private const val COLUMN_TECHNIQUE = "technique"
        private const val COLUMN_MUSCLE_GROUP = "muscle_group"
    }
    private val appContext = context.applicationContext
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_SHORT_DESC TEXT,
                $COLUMN_TECHNIQUE TEXT,
                $COLUMN_MUSCLE_GROUP TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
        loadExercisesFromJson(db)
    }
    private fun loadExercisesFromJson(db: SQLiteDatabase) {
        try {
            val inputStream: InputStream = appContext.assets.open("exercises.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)
            android.util.Log.d("DatabaseHelper", "JSON длина: ${jsonString.length}")
            android.util.Log.d("DatabaseHelper", "JSON начало: ${jsonString.take(100)}")

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val values = ContentValues().apply {
                    put(COLUMN_NAME, jsonObject.getString("name"))
                    put(COLUMN_SHORT_DESC, jsonObject.getString("shortDescription"))
                    put(COLUMN_TECHNIQUE, jsonObject.getString("technique"))
                    put(COLUMN_MUSCLE_GROUP, jsonObject.getString("muscleGroup"))
                }
                db.insert(TABLE_NAME, null, values)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            insertSampleData(db)
        }
    }
    private fun insertSampleData(db: SQLiteDatabase) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, "Отжимания")
            put(COLUMN_SHORT_DESC, "Классическое упражнение для груди")
            put(COLUMN_TECHNIQUE, "Техника отжиманий...")
            put(COLUMN_MUSCLE_GROUP, "Грудь")
        }
        db.insert(TABLE_NAME, null, values)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun getAllExercises(): List<Exercise> {
        val exercises = mutableListOf<Exercise>()
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val exercise = Exercise(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                shortDescription = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SHORT_DESC)),
                technique = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TECHNIQUE)),
                muscleGroup = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MUSCLE_GROUP))
            )
            exercises.add(exercise)
        }
        cursor.close()
        db.close()
        return exercises
    }
}
data class Exercise(
    val id: Int,
    val name: String,
    val shortDescription: String,
    val technique: String,
    val muscleGroup: String
)