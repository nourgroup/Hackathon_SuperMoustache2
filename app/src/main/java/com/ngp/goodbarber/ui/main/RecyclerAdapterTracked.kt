package com.ngp.goodbarber.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngp.goodbarber.R
import com.ngp.goodbarber.model.Product

//import kotlinx.android.synthetic.main.card_layout.*

class RecyclerAdapterTracked(var arrayProduct : MutableList<Product>?, var activity : Activity): RecyclerView.Adapter<RecyclerAdapterTracked.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemNom: TextView
        var itemPrix: TextView
        var itemConcurrent: TextView
        var itemproduit: Button
        var itemreference: TextView

        init {
            itemNom = itemView.findViewById(R.id.itemnom)
            itemPrix = itemView.findViewById(R.id.itemprix)
            itemConcurrent = itemView.findViewById(R.id.itemconcurrent)
            itemproduit = itemView.findViewById(R.id.itemproduit)
            itemreference = itemView.findViewById(R.id.itemreference)
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.card_layout_tracked, viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemNom.text = arrayProduct!!.get(i).nom
        viewHolder.itemPrix.text = arrayProduct!!.get(i).prix+" €"
        viewHolder.itemConcurrent.text = arrayProduct!!.get(i).Concurrent
        viewHolder.itemproduit.setOnClickListener {
            //message.text = name
            val intent1 = Intent(activity, DisplayTrackedPrice::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent1.putExtra("reference", arrayProduct!![i].Concurrent)
            intent1.putExtra("nom", arrayProduct!![i].nom)
            activity.startActivity(intent1)
        }
        viewHolder.itemreference.text =  arrayProduct!!.get(i).gtin
    }
    override fun getItemCount(): Int {
        return arrayProduct!!.size
    }

    /**/

}