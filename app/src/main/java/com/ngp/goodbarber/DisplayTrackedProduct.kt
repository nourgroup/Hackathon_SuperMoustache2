package com.ngp.goodbarber

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import com.ngp.goodbarber.model.Product


import kotlinx.android.synthetic.main.activity_display_result.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class DisplayResult : AppCompatActivity() {
    private lateinit var recycler_view : RecyclerView
    private lateinit var tv : TextView
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var adapter: RecyclerAdapter

    private lateinit var dialog : mDialogFragment

    var doc : Document? = null
    var doc2 : Document? = null
    var slot1: Elements? = null
    var slot2 : Elements? = null
    var slot3 : Elements? = null
    lateinit var fm: FragmentManager

    var product = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_result)

        layoutManager = LinearLayoutManager(this)
        my_recycler_view.layoutManager = layoutManager

        fm = supportFragmentManager
        product = intent.getStringExtra("nomProduit")
        //titre.text = intent.getStringExtra("nomProduit")
        dialog = mDialogFragment()
        dialog.show(fm, "mDialogFragment")
        HtmlText().execute()
    }

    private inner class HtmlText : AsyncTask<String, Int, ArrayList<Product>>() {
        override fun doInBackground(vararg urls: String): ArrayList<Product> {
            val arrayList = ArrayList<Product>()//Creating an empty arraylist.

            try {
                doc2 = Jsoup.connect("https://www.google.com/search?sa=X&authuser=0&biw=1280&bih=619&tbm=shop&q="+product).get()

                slot1 = doc2!!.select("div[data-docid]")

                //name = slot1!!.eachText().toString()
                for(i in 0..(slot1!!.size-1)){
                    arrayList.add(Product(slot1!![i].select("a > h1,h2,h3,h4,h5,h6").text().toString(),slot1!![i].select("span > span[aria-hidden]").text().toString(),slot1!![i].select("*:last-child a[target=_blank]").text().toString(),"","",""))
                    /*recuperer à chaque le gtin qui est l'identifiant produit*/
                    doc = Jsoup.connect("https://www.google.com/"+slot1!![i].select("a").attr("href").toString()).get()
                    /*TODO prendre soit les references soit gtin soit */
                    slot2 = doc!!.select("table > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2)")
                    //tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2)
                    //slot2 = doc!!.select("table > tbody > tr > td:nth-child(2)")
                    arrayList[i].gtin = slot2!!.text()
                }

            } catch (s: Exception) {
            }

            return arrayList!!
        }

        override fun onPostExecute(result:  ArrayList<Product>) {
            adapter = RecyclerAdapter(result,this@DisplayResult)
            my_recycler_view.adapter = adapter
            dialog.close()
            titre.text = "nombre de résultat : " + result.size.toString()
        }
    }
}
