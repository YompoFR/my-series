package com.example.seriesorganizer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteConnectionHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "organizadordeseries.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Utilities.CREATE_SERIE_TABLE)
        db?.execSQL(Utilities.CREATE_CHECKBOX_TABLE)
        db?.execSQL(Utilities.CREATE_SEASONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS series")
        db?.execSQL(Utilities.CREATE_SERIE_TABLE)
        db?.execSQL("DROP TABLE IF EXISTS seasons")
        db?.execSQL(Utilities.TABLE_SEASONS)
        db?.execSQL("DROP TABLE IF EXISTS checkbox")
        db?.execSQL(Utilities.TABLE_CHECKBOX)
    }
}