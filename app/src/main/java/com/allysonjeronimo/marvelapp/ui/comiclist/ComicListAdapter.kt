package com.allysonjeronimo.marvelapp.ui.comiclist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.network.entity.ComicData
import com.allysonjeronimo.marvelapp.extensions.currencyFormat
import com.allysonjeronimo.marvelapp.extensions.load
import kotlinx.android.synthetic.main.comic_item.view.*

class ComicListAdapter(
    private val comics:List<Comic>,
    private val onClickListener: (Comic) -> Unit
) : RecyclerView.Adapter<ComicListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comics[position])
        holder.itemView.setOnClickListener {
            onClickListener(comics[position])
        }
    }

    override fun getItemCount() = comics.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val textTitle = itemView.text_title
        private val imageCover = itemView.image_cover
        private val textPrice = itemView.text_price
        private val viewRare = itemView.view_rare
        private val viewBorder = itemView.view_border_image

        fun bind(comic:Comic){
            textTitle.text = comic.title
            textPrice.text = comic.price.currencyFormat()
            imageCover.load("${comic.thumbnailPath}/portrait_xlarge.${comic.thumbnailExtension}")
            if(comic.rare){
                viewRare.visibility = View.VISIBLE
                viewBorder.visibility = View.VISIBLE
            }
            else {
                viewRare.visibility = View.GONE
                viewBorder.visibility = View.GONE
            }
        }
    }
}