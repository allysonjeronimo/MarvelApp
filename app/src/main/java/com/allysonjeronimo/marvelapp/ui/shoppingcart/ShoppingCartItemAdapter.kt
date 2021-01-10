package com.allysonjeronimo.marvelapp.ui.shoppingcart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.extensions.currencyFormat
import com.allysonjeronimo.marvelapp.extensions.load
import kotlinx.android.synthetic.main.shopping_cart_item_item.view.*

class ShoppingCartItemAdapter(
    private val items:List<ShoppingCartItem>,
    private val onItemRemoveListener:(ShoppingCartItem) -> Unit,
    private val onItemClickListener: (ShoppingCartItem) -> Unit
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
        holder.itemView.setOnClickListener {
            onItemClickListener(items[position])
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
        private val viewBorder = itemView.view_border_image
        private val textTitle = itemView.text_item_title
        private val textSubtotal = itemView.text_item_subtotal
        private val textSubtotalDiscount = itemView.text_item_subtotal_discount
        private val textNumber = itemView.text_item_number

        fun bind(item:ShoppingCartItem){
            textTitle.text = item.comic.title
            textNumber.text = itemView.context.resources.getString(R.string.comic_details_number, item.comic.issueNumber.toString())
            textSubtotal.text = item.subtotal().currencyFormat()
            imageCover.load("${item.comic.thumbnailPath}/portrait_small.${item.comic.thumbnailExtension}")

            if(item.comic.rare){
                viewBorder.visibility = View.VISIBLE
            }
            else{
                viewBorder.visibility = View.GONE
            }

            if(item.discount != 0.0){
                textSubtotalDiscount.text = item.subtotalWithDiscount().currencyFormat()
                textSubtotalDiscount.visibility = View.VISIBLE
                textSubtotal.paintFlags = textSubtotal.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            else{
                textSubtotalDiscount.visibility = View.GONE
                textSubtotal.paintFlags = textSubtotal.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
}