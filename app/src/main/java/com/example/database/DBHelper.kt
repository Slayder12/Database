package com.example.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "PERSON_DATABASE"
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "person_table"
        val KEY_ID = "id"
        val KEY_FIRST_NAME = "first_name"
        val KEY_LAST_NAME = "last_name"
        val KEY_AGE = "age"
        val KEY_ROLE = "role"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("  +
                KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_FIRST_NAME + " TEXT, " +
                KEY_LAST_NAME + " TEXT, " +
                KEY_AGE + " TEXT, " +
                KEY_ROLE + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun addData(firstName: String, lastName: String, age: String, role: String){
        val values = ContentValues()
        values.put(KEY_FIRST_NAME, firstName)
        values.put(KEY_LAST_NAME, lastName)
        values.put(KEY_AGE, age)
        values.put(KEY_ROLE, role)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getInfo(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun removeAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }
}