package com.example.organizadordeseries

import android.app.Notification
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_series.*
import android.view.ActionMode


class MySeries : AppCompatActivity(), SerieAdapter.OnSerieClickListener {

    // Lista estática
    companion object {
        var listSerie = mutableListOf<Serie>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_series)

        initRecycler() // Inicamos el reclycler para que nos muestre la lista
        val toastText = this.getString(R.string.toast_number_of_series) + " "
        Toast.makeText(this, "$toastText $listSerie.size", Toast.LENGTH_SHORT).show()
    }

    fun initRecycler() {
        rvSerie.layoutManager = LinearLayoutManager(this)
        rvSerie.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        var adapter = SerieAdapter(this, listSerie, this)
        rvSerie.adapter = adapter
    }

    override fun onItemClick(
        id: Int,
        title: String,
        numberOfSeasons: Int,
        numberChaptersPerSeason: Int,
        image: String
    ) { // Pasamos todos los parámetros a la activity para que nos muestre las temporadas
        val intent = Intent(this, ShowCurrentSerie::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("numberOfSeasons", numberOfSeasons)
        intent.putExtra("numberChaptersPerSeason", numberChaptersPerSeason)
        intent.putExtra("image", image)
        startActivity(intent)
    }

    override fun onItemLongClick(position: Int) {
        TODO("Not yet implemented")
    }
}