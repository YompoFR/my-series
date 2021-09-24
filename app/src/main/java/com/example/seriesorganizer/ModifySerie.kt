package com.example.seriesorganizer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_series.*

class ModifySerie : AppCompatActivity(), SerieAdapter.OnSerieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_serie)

        initRecycler()
    }

    fun initRecycler() {
        rvSerie.layoutManager = LinearLayoutManager(this)
        rvSerie.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        val adapter = SerieAdapter(this, MySeries.listSerie, this)
        rvSerie.adapter = adapter
    }

    override fun onItemLongClick(position: Int) {
        Toast.makeText(this, "Has selecionado el elemento $position", Toast.LENGTH_SHORT).show()
    }


    override fun onItemClick(id: Int, title: String, numberOfSeasons: Int, numberChaptersPerSeason: Int, image: String) {
        val intent = Intent(this, EditData::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("numberOfSeasons", numberOfSeasons)
        intent.putExtra("numberChaptersPerSeason", numberChaptersPerSeason)
        intent.putExtra("image", image)
        startActivity(intent)
    }

    override fun onResume() {
        initRecycler()
        super.onResume()
    }

    override fun onRestart() {
        initRecycler()
        super.onRestart()
    }
}