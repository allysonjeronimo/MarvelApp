package com.allysonjeronimo.marvelapp.ui.comiclist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.entity.Comic
import com.allysonjeronimo.marvelapp.extensions.currencyFormat
import com.allysonjeronimo.marvelapp.extensions.load
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comic_item.view.*

class ComicListAdapter(
    private val comics:List<Comic>
) : RecyclerView.Adapter<ComicListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comics[position])
    }

    override fun getItemCount() = comics.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val textTitle = itemView.text_title
        private val imageCover = itemView.image_cover
        private val textPrice = itemView.text_price

        fun bind(comic:Comic){
            textTitle.text = comic.title
            textPrice.text = comic.firstPrice().currencyFormat().toString()
            Log.i(ComicListAdapter::class.simpleName, "${comic.thumbnail.path}/portrait_xlarge.${comic.thumbnail.extension}")
            imageCover.load("${comic.thumbnail.path}/portrait_xlarge.${comic.thumbnail.extension}")
        }
    }
}