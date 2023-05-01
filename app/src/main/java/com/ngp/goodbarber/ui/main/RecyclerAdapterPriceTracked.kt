package com.ngp.goodbarber.ui.main

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngp.goodbarber.R
import com.ngp.goodbarber.model.Product

//import kotlinx.android.synthetic.main.card_layout.*

class RecyclerAdapterPriceTracked(var arrayProduct : MutableList<Product>?, var activity : Activity): RecyclerView.Adapter<RecyclerAdapterPriceTracked.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemNom: TextView
        var itemPrix: TextView
        var itemConcurrent: TextView

        var itemreference: TextView

        init {
            itemNom = itemView.findViewById(R.id.itemnom)
            itemPrix = itemView.findViewById(R.id.itemprix)
            itemConcurrent = itemView.findViewById(R.id.itemconcurrent)
            itemreference = itemView.findViewById(R.id.itemreference)
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_layout_price_tracked, viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemNom.text = arrayProduct!!.get(i).nom
        viewHolder.itemPrix.text = arrayProduct!!.get(i).prix+" €"
        viewHolder.itemConcurrent.text = arrayProduct!!.get(i).Concurrent

        viewHolder.itemreference.text =  arrayProduct!!.get(i).gtin
    }
    override fun getItemCount(): Int {
        return arrayProduct!!.size
    }

    /**/

}