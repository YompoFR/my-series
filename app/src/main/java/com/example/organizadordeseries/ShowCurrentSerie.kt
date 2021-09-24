package com.example.organizadordeseries

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_current_serie.*
import java.lang.Exception
import android.view.ActionMode
import android.widget.*


class ShowCurrentSerie : AppCompatActivity(){

    var manageDatabase = ManageDatabase(this)

    var header: MutableList<String> = mutableListOf()
    var body: MutableList<MutableList<CheckBox>> = mutableListOf()

    var id = 1
    var title = ""
    var numberOfSeasons = 1
    var numberChaptersPerSeason = 1
    var image: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_current_serie)

        try {
            checkIntentInfo() // Si el intent no está vacío, asignamos valor a las variables
            fill() // Rellenamos tanto el header como el body
            expandableShowCurrentSerie.setAdapter(ExpandableListAdapter(this, id, header, body)) // Establecemos el adapter de la lista expandible

        } catch (e: Exception){
            val toastText = this.getString(R.string.exception)
            e.printStackTrace()
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
    }

    // Función para comprobar si el intent tiene datos
    private fun checkIntentInfo() {
        if (intent.extras != null) {
            id = intent.getIntExtra("id", 1)
            title = intent.getStringExtra("title").toString()
            numberOfSeasons = intent.getIntExtra("numberOfSeasons", 1).toInt()
            numberChaptersPerSeason = intent.getIntExtra("numberChaptersPerSeason", 1).toInt()
            image = intent.getStringExtra("image").toString()

            Picasso.get().load(image).into(imageSerie) // Rellenamos la imagen
        }
    }

    // Rellenamos tanto el header como el body
    private fun fill(){
        var seasonTitle = this.getString(R.string.fill_season) + " "
        var chapterTitle = this.getString(R.string.fill_chapter) + " "

        for(i in 1..numberOfSeasons){
            header.add(seasonTitle + i)
            var seasonTitle = (seasonTitle + i)

            manageDatabase.saveSeasons(id, i, seasonTitle, numberChaptersPerSeason)

            var checkBoxList : MutableList<CheckBox> = mutableListOf()
            for(j in 1..numberChaptersPerSeason){
                var checkBox = CheckBox(this)
                checkBox.text = chapterTitle + j
                checkBox.id = ("" + i + j).toInt() // Para que sea temporada 1 capitulo 13 = 113
                checkBox.isSelected = manageDatabase.getCheckBoxState(this, id , checkBox.id) // Recogemos los datos si ese checkbox está marcado
                checkBoxList.add(checkBox) // Añadimos el checkbox a la lista de checkbox
            }
            body.add(checkBoxList)
        }
    }
}