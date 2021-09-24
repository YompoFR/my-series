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

        btnAdd.setOnClickListener(){
            val intent = Intent(this, AddSerie::class.java)
            startActivity(intent)
        }

        btnMySeries.setOnClickListener(){
            val intent = Intent(this, MySeries::class.java)
            startActivity(intent)
        }

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