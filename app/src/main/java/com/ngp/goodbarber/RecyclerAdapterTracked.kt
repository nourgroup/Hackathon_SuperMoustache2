package com.ngp.goodbarber

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ngp.goodbarber.model.Product
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

//import kotlinx.android.synthetic.main.card_layout.*

class RecyclerAdapter(var arrayProduct : MutableList<Product>? , var activity : Activity): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

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
                .inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemNom.text = arrayProduct!!.get(i).nom
        viewHolder.itemPrix.text = arrayProduct!!.get(i).prix
        viewHolder.itemConcurrent.text = arrayProduct!!.get(i).Concurrent
        viewHolder.itemproduit.setOnClickListener {
            Toast.makeText(activity,"clicked",Toast.LENGTH_SHORT).show()
            InsertData(arrayProduct!![i])
        }
        viewHolder.itemreference.text =  arrayProduct!!.get(i).gtin
    }
    override fun getItemCount(): Int {
        return arrayProduct!!.size
    }

    /**/
    fun InsertData(product: Product?) {
        var client = OkHttpClient()
        var request= OkHttpRequest(client)
        val url = "https://www.ng-plus.com/pandroid/hackathon/addproduct.php?id_product_google="+ product!!.gtin +"&price="+product!!.prix.split(" ")[0]+"&id_product="+product.nom+"&reference="+product.reference

        //val url = "http://api.plos.org/search?q=title:%22Drosophila%22%20and%20body:%22RNA%22&fl=id,abstract&wt=json&indent=on"
        request.GET(url, object: Callback {
            override fun onResponse(call: Call?, response: Response) {
                val responseData = response.body()?.string()
                activity?.runOnUiThread{
                    try {
                        var response : String
                        var json = JSONArray(responseData)
                        lateinit var mJSONObject : JSONObject

                        mJSONObject = json.getJSONObject(0)
                        response = mJSONObject.getString("response")
                        if(response.equals("OK")){
                            /*TODO interface*/
                            //mOnChangedData.ChangedData(n_produit,fprix as Double,fquantite as Int)
                            //mOnChangedData.ChangedData(n_produit,fprix.toDouble(),fquantite.toInt());

                        }

                        //Log.i("OKKO",response+":"+n_produit+":"+fprix+":"+fquantite)
                    } catch (e: JSONException) {
                        e.printStackTrace()

                    }
                }
                //Toast.makeText(Re.context,"bien inseré",Toast.LENGTH_LONG).show()
                //msgerreur.text = "bien inseré"

                /*TODO will be updated when the number is actualised on DB
                use for that adapter.notifyDataSetChanged() and interface to send OK state to listproduct.java.
                * */

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Activity Failure.")

            }


        })
    }
}