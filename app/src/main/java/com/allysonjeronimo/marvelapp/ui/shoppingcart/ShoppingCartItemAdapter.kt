package com.allysonjeronimo.marvelapp.ui.shoppingcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.extensions.currencyFormat
import com.allysonjeronimo.marvelapp.extensions.load
import kotlinx.android.synthetic.main.shopping_cart_item_item.view.*
import java.util.*
import kotlin.concurrent.schedule

class ShoppingCartItemAdapter(
    private val items:List<ShoppingCartItem>,
    private val onItemRemoveListener:(ShoppingCartItem) -> Unit
    ) : RecyclerView.Adapter<ShoppingCartItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.shopping_cart_item_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.button_delete.setOnClickListener {
            removeItem(position)
        }
    }

    private fun removeItem(position:Int){
        notifyItemRemoved(position)
        items.toMutableList().removeAt(position)
        onItemRemoveListener(items[position])
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        private val imageCover = itemView.image_item_cover
        private val textTitle = itemView.text_item_title
        private val textSubtotal = itemView.text_item_subtotal
        private val textNumber = itemView.text_item_number

        fun bind(item:ShoppingCartItem){
            textTitle.text = item.comic.title
            textNumber.text = itemView.context.resources.getString(R.string.comic_details_number, item.comic.issueNumber.toString())
            textSubtotal.text = item.subtotal().currencyFormat()
            imageCover.load("${item.comic.thumbnailPath}/portrait_small.${item.comic.thumbnailExtension}")
        }
    }
}