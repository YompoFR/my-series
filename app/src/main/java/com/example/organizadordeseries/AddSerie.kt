package com.example.organizadordeseries

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_serie.*
import java.lang.NumberFormatException
import com.example.organizadordeseries.Utilities

class AddSerie : AppCompatActivity() {

    val manageDatabase = ManageDatabase(this)

    // Variables para crear el objeto serie
    lateinit var title: String
    var numberSeasons = 0
    var numberChaptersPerSeason = 0
    lateinit var image: String
    lateinit var serie: Serie
    var defaultImage = "https://i.pinimg.com/originals/6d/e1/ae/6de1ae74243851c18d864cc898d7c0d0.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_serie)

        cleanFields() // Limpiamos los campos siempre

        // Establecemos el botón de registrar
        btnRegister.setOnClickListener() {
            try {
                setFields()
                checkTitleAndRegister()
                cleanFields()
            } catch (e: NumberFormatException) { // Para evitar el error de pasar de String a Int al no poner número de temporadas
                Toast.makeText(this, "No puede haber campos vacíos", Toast.LENGTH_SHORT).show()
                cleanFields()
            }
        }
    }

    // Extraemos el valor de los campos y lo asignamos a las variables
    fun setFields(){
        title = edtTitle.text.toString()
        numberSeasons = edtNumberOfSeasons.text.toString().toInt()
        numberChaptersPerSeason = edtNumberChaptersPerSeason.text.toString().toInt()
        image = edtImage.text.toString()
    }

    // Comprobamos el título
    fun checkTitleAndRegister(){
        if (title.isEmpty()) {
            Toast.makeText(this, "Es obligatorio poner un título", Toast.LENGTH_SHORT).show()
            cleanFields()
        }
        else { // Asignamos una imagen por defecto en caso de que no pongamos ninguna
            if (image.isEmpty()) image = defaultImage

            // Registramos la serie en la base de datos
            manageDatabase.registerSerie(this, title, numberSeasons, numberChaptersPerSeason, image)
        }
    }

    // Función para limpiar los campos
    fun cleanFields() {
        edtTitle.text.clear()
        edtNumberOfSeasons.text.clear()
        edtNumberChaptersPerSeason.text.clear()
        edtImage.text.clear()
    }
}