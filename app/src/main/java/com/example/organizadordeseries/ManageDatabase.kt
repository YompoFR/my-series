package com.example.organizadordeseries

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.CheckBox
import android.widget.Toast

class ManageDatabase(contex: Context) {

    val dbHelper = SQLiteConnectionHelper(contex)

    // Funciones para hacer debug

    fun debugCheckBox(cursor: Cursor) {
        println("SERIE ID " + cursor.getInt(0))
        println("CHECKBOX ID " + cursor.getInt(1))
        println("GROUP POSITION " + cursor.getInt(2))
        println("CHILD POSITION " + cursor.getInt(3))
        println("CHECKED STATE " + cursor.getInt(4))
    }

    fun debugReadDatabase(cursor: Cursor) {
        println("ID: " + cursor.getInt(0))
        println("Titulo: " + cursor.getString(1))
        println("Numero de temporadas: " + cursor.getInt(2))
        println("Numero de capitulos: " + cursor.getInt(3))
        println("URL de la imagen: " + cursor.getString(4))
        println()
    }

    // Funciones de la base de datos serie

    fun readDataBase() {
        val query = "SELECT * FROM ${Utilities.TABLE_SERIES}"
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            debugReadDatabase(cursor)
            addSerie(cursor)
        }
    }

    fun reloadDatabase() {
        MySeries.listSerie.clear()
        readDataBase()
    }

    fun updateDatabase(
        id: Int,
        title: String,
        numberSeasons: Int,
        numberChaptersPerSeason: Int,
        image: String
    ) {
        var db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Utilities.COLUMN_TITLE, title)
            put(Utilities.COLUMN_NUMBER_SEASONS, numberSeasons)
            put(Utilities.COLUMN_NUMBER_CHAPTERS_PER_SEASON, numberChaptersPerSeason)
            put(Utilities.COLUMN_IMAGE, image)
        }
        val selection = "${Utilities.COLUMN_ID} LIKE ?"
        val selectionArgs = arrayOf((id).toString())
        db?.update(
            Utilities.TABLE_SERIES, values,
            selection,
            selectionArgs
        )
        MySeries.listSerie.clear()
        readDataBase()
    }

    fun registerSerie(
        contex: Context,
        title: String,
        numberSeasons: Int,
        numberChaptersPerSeason: Int,
        image: String
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Utilities.COLUMN_TITLE, title)
            put(Utilities.COLUMN_NUMBER_SEASONS, numberSeasons)
            put(Utilities.COLUMN_NUMBER_CHAPTERS_PER_SEASON, numberChaptersPerSeason)
            put(Utilities.COLUMN_IMAGE, image)
        }
        db?.insert(Utilities.TABLE_SERIES, null, values)
        Toast.makeText(contex, "Serie registrada", Toast.LENGTH_SHORT).show()
    }

    fun addSerie(cursor: Cursor) {
        var id = cursor.getInt(0)
        var title = cursor.getString(1)
        var numberSeasons = cursor.getInt(2)
        var numberChaptersPerSeason = cursor.getInt(3)
        var image = cursor.getString(4)

        var serie = Serie(id, title, numberSeasons, numberChaptersPerSeason, image)
        MySeries.listSerie.add(serie)
    }

    fun removeSerie(id: Int) {
        var db = dbHelper.writableDatabase
        val selection = "${Utilities.COLUMN_ID} LIKE ?"
        val selectionArgs = arrayOf((id).toString())
        db.delete(Utilities.TABLE_SERIES, selection, selectionArgs)
        reloadDatabase()
    }

    // #######################
    // Funciones del checkbox

    fun setCheckboxState(
        serieId: Int,
        checkBoxId: Int,
        groupPosition: Int,
        childPosition: Int,
        checked: String
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Utilities.COLUMN_SERIE_ID, serieId)
            put(Utilities.COLUMN_CHECKBOX_ID, checkBoxId)
            put(Utilities.COLUMN_GROUP_POSITION, groupPosition)
            put(Utilities.COLUMN_CHILD_POSITION, childPosition)
            put(Utilities.COLUMN_CHECKED, checked)
        }
        db?.insert(Utilities.TABLE_CHECKBOX, null, values)
    }

    fun readCheckBoxState(
        serieId: Int,
        checkBoxId: Int,
        groupPosition: Int,
        childPosition: Int,
        checked: String
    ) {
        val query =
            "SELECT * FROM ${Utilities.TABLE_CHECKBOX} WHERE ${Utilities.COLUMN_SERIE_ID} = $serieId AND ${Utilities.COLUMN_CHECKBOX_ID} = $checkBoxId"
        val db = dbHelper.readableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.count == 0)
            setCheckboxState(serieId, checkBoxId, groupPosition, childPosition, checked)
        else {
            while (cursor.moveToNext()) {
                var id = cursor.getInt(0)
                var chkId = cursor.getInt(1)

                if (id != serieId && chkId != checkBoxId)
                    setCheckboxState(serieId, checkBoxId, groupPosition, childPosition, checked)
                else if (id == serieId && chkId == checkBoxId) {
                    var checkedbox = checked
                    println(id)
                    println(chkId)
                    println(checkedbox)
                    updateCheckboxState(serieId, checkBoxId, checked)
                }
            }
        }
    }

    fun updateCheckboxState(
        serieId: Int,
        checkBoxId: Int,
        checked: String
    ) {
        var db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(Utilities.COLUMN_CHECKED, checked)
        }
        val selection =
            "${Utilities.COLUMN_SERIE_ID} LIKE ? AND ${Utilities.COLUMN_CHECKBOX_ID} LIKE ?"
        val selectionArgs = arrayOf(
            (serieId).toString(), checkBoxId.toString()
        )
        db?.update(
            Utilities.TABLE_CHECKBOX, values,
            selection,
            selectionArgs
        )
    }

    fun getCheckBoxState(context: Context, serieId: Int, checkBoxId: Int): Boolean {
        var checkBox = CheckBox(context)

        val db = dbHelper.readableDatabase

        val query =
            "SELECT * FROM ${Utilities.TABLE_CHECKBOX} WHERE ${Utilities.COLUMN_SERIE_ID} = $serieId AND ${Utilities.COLUMN_CHECKBOX_ID} = $checkBoxId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            checkBox.isSelected = cursor.getString(4).toBoolean()
        }
        return checkBox.isSelected
    }

    fun saveSeasons(
        serieId: Int,
        seasonId : Int,
        title: String,
        numberChaptersPerSeason: Int
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(Utilities.COLUMN_SERIE_SEASON_ID, serieId)
            put(Utilities.COLUMN_SEASON_ID, seasonId)
            put(Utilities.COLUMN_TITLE, title)
            put(Utilities.COLUMN_SEASON_NUMBER_CHAPTERS, numberChaptersPerSeason)
        }
        db?.insert(Utilities.TABLE_SEASONS, null, values)
    }

    fun showSeasonsChapters(serieId: Int, groupPosition: Int) : Int{
        var numberChapters = 0
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM ${Utilities.TABLE_SEASONS} WHERE ${Utilities.COLUMN_SERIE_SEASON_ID} = $serieId AND ${Utilities.COLUMN_SEASON_ID} = $groupPosition"
        val cursor = db.rawQuery(query, null)

        while(cursor.moveToNext()){
            numberChapters = cursor.getInt(3)
        }
        return numberChapters
    }
}