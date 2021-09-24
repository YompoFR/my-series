package com.example.seriesorganizer

import android.content.Context
import android.graphics.Color
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_serie.view.*

class SerieAdapter(
        val context: Context,
        var listSerie: MutableList<Serie>,
        val itemClickListener: OnSerieClickListener) : RecyclerView.Adapter<SerieAdapter.SerieHolder>(),
        View.OnClickListener{

    interface OnSerieClickListener {
        fun onItemClick(id: Int, title: String,numberOfSeasons: Int , numberChaptersPerSeason: Int, image: String)
        fun onItemLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieAdapter.SerieHolder {
        return SerieHolder(LayoutInflater.from(context).inflate(R.layout.item_serie, parent, false))
    }

    override fun getItemCount(): Int = listSerie.size

    override fun onBindViewHolder(holder: SerieAdapter.SerieHolder, position: Int) {
        when (holder) {
            is SerieHolder -> holder.onBind(listSerie[position], position)
            else -> throw IllegalArgumentException("No se ha pasado el holder")
        }
    }

    inner class SerieHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private var actionMode : ActionMode? = null

        private val actionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                val inflater: MenuInflater = mode?.menuInflater!!
                inflater.inflate(R.menu.context_menu, menu)
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                actionMode = null
            }
        }

        fun onBind(serie: Serie, position: Int) {

            // Rellenamos los campos
            view.tvTitle.text = serie.title
            view.tvNumberOfSeasons.text = serie.numberSeasons.toString()
            Picasso.get().load(serie.image).into(view.ivCover)

            // Establecemos el click
            itemView.setOnClickListener {
                itemClickListener.onItemClick(serie.id, serie.title, serie.numberSeasons, serie.numberChaptersPerSeason, serie.image)
            }

            itemView.setOnLongClickListener {view ->
                when (actionMode) {
                    null -> {
                        // Start the CAB using the ActionMode.Callback defined above
                        actionMode = view?.startActionMode(actionModeCallback)!!
                        view.isSelected = true
                        if (view.isSelected){
                            view.setBackgroundColor(Color.GRAY)
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }
    override fun onClick(v: View?) {

    }
}