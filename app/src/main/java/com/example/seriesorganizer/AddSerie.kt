package com.example.seriesorganizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_serie.*
import java.lang.NumberFormatException

class AddSerie : AppCompatActivity() {

    val manageDatabase = ManageDatabase(this)

    // Variables to create "serie" object
    lateinit var title: String
    var numberSeasons = 0
    var numberChaptersPerSeason = 0
    lateinit var image: String
    lateinit var serie: Serie
    var defaultImage = "https://i.pinimg.com/originals/6d/e1/ae/6de1ae74243851c18d864cc898d7c0d0.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_serie)

        cleanFields() // We always clean fields

        btnRegister.setOnClickListener() {
            val toastText = this.getString(R.string.toast_btn_register)
            try {
                setFields()
                checkTitleAndRegister()
                cleanFields()
            } catch (e: NumberFormatException) { // To prevent an error when passing from string to int when we don't put a number
                Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
                cleanFields()
            }
        }
    }

    // Extract the values of fields and assing to variables
    fun setFields(){
        title = edtTitle.text.toString()
        numberSeasons = edtNumberOfSeasons.text.toString().toInt()
        numberChaptersPerSeason = edtNumberChaptersPerSeason.text.toString().toInt()
        image = edtImage.text.toString()
    }

    fun checkTitleAndRegister(){
        val toastText = this.getString(R.string.toast_set_title)
        if (title.isEmpty()) {
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
            cleanFields()
        }
        else { // Assign a default image in case that we dont put anything
            if (image.isEmpty()) image = defaultImage

            // Save serie in database
            manageDatabase.registerSerie(this, title, numberSeasons, numberChaptersPerSeason, image)
        }
    }

    fun cleanFields() {
        edtTitle.text.clear()
        edtNumberOfSeasons.text.clear()
        edtNumberChaptersPerSeason.text.clear()
        edtImage.text.clear()
    }
}