package com.example.seriesorganizer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var manageDatabase = ManageDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manageDatabase.readDataBase()

        // Creamos el botón para ir a la actividad "Añadir serie"
        btnAdd.setOnClickListener(){
            val intent = Intent(this, AddSerie::class.java)
            startActivity(intent)
        }

        // Creamos el botón para ir a la actividad "Seguimiento"
        btnMySeries.setOnClickListener(){
            val intent = Intent(this, MySeries::class.java)
            startActivity(intent)
        }

        // Creamos el botón para ir a la actividad "Modificar serie"
        btnModify.setOnClickListener(){
            val intent = Intent(this, ModifySerie::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        manageDatabase.reloadDatabase()
        super.onResume()
    }
}