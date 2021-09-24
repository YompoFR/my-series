package com.example.seriesorganizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_current_serie.*
import java.lang.Exception
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
            checkIntentInfo() // If intent is not empty, we assing the values to variables
            fill() // Fill header and body
            expandableShowCurrentSerie.setAdapter(ExpandableListAdapter(this, id, header, body)) // Set adapter from expandable list adapter

        } catch (e: Exception){
            val toastText = this.getString(R.string.exception)
            e.printStackTrace()
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }
    }

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

    // Fill header and body
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
                checkBox.id = ("" + i + j).toInt() // To have season 1 chapter 13 = 133, to change appropriate checkbox data easily
                checkBox.isSelected = manageDatabase.getCheckBoxState(this, id , checkBox.id) // Get data if checkbox is checked
                checkBoxList.add(checkBox) // Add checkbox to checkbox list
            }
            body.add(checkBoxList)
        }
    }
}