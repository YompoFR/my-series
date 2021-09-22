package com.example.organizadordeseries

class Utilities {
    companion object {
        // Constantes Campos Tabla Usuario
        const val TABLE_SERIES = "series"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_NUMBER_SEASONS = "numberSeasons"
        const val COLUMN_NUMBER_CHAPTERS_PER_SEASON = "numberChaptersPerSeason"
        const val COLUMN_IMAGE = "image"

        // Constantes Campos Tabla Checkbox
        const val TABLE_CHECKBOX = "checkbox"
        const val COLUMN_SERIE_ID = "serieId"
        const val COLUMN_CHECKBOX_ID = "checkboxId"
        const val COLUMN_GROUP_POSITION = "groupPosition"
        const val COLUMN_CHILD_POSITION = "childPosition"
        const val COLUMN_CHECKED = "checked"

        // Constantes Campos Tabla Seasons
        const val TABLE_SEASONS = "seasons"
        const val COLUMN_SERIE_SEASON_ID = "serieSeasonId"
        const val COLUMN_SEASON_ID = "seasonId"
        const val COLUMN_SEASON_TITLE = "title"
        const val COLUMN_SEASON_NUMBER_CHAPTERS = "numberChapters"

        // Sentencia SQL para crear tabla
        val CREATE_SERIE_TABLE = "CREATE TABLE $TABLE_SERIES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE TEXT, " +
                "$COLUMN_NUMBER_SEASONS INTEGER, " +
                "$COLUMN_NUMBER_CHAPTERS_PER_SEASON INTEGER, " +
                "$COLUMN_IMAGE TEXT)"

        // Sentencia SQL para crear tabla
        val CREATE_CHECKBOX_TABLE = "CREATE TABLE $TABLE_CHECKBOX (" +
                "$COLUMN_SERIE_ID INTEGER, " +
                "$COLUMN_CHECKBOX_ID INTEGER, " +
                "$COLUMN_GROUP_POSITION INTEGER, " +
                "$COLUMN_CHILD_POSITION INTEGER, " +
                "$COLUMN_CHECKED TEXT)"

        // Sentencia SQL para crear tabla
        val CREATE_SEASONS_TABLE = "CREATE TABLE $TABLE_SEASONS (" +
                "$COLUMN_SERIE_SEASON_ID INTEGER, " +
                "$COLUMN_SEASON_ID INTEGER, " +
                "$COLUMN_SEASON_TITLE INTEGER,  " +
                "$COLUMN_SEASON_NUMBER_CHAPTERS INTEGER)"
    }
}