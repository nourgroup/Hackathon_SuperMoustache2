package com.ngp.goodbarber.ui.main

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.ngp.goodbarber.R
import com.ngp.goodbarber.mDialogFragment
import com.ngp.goodbarber.model.Product
import kotlinx.android.synthetic.main.activity_display_result.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class DisplayResult : AppCompatActivity() {
    private lateinit var recycler_view : RecyclerView
    private lateinit var tv : TextView
    private var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: RecyclerAdapter? = null

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
        product = intent.getStringExtra("nomProduit")!!
        //titre.text = intent.getStringExtra("nomProduit")
        dialog = mDialogFragment()
        dialog.show(fm, "mDialogFragment")
        HtmlText().execute()
    }

    private inner class HtmlText : AsyncTask<String, Int, ArrayList<Product>>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg urls: String): ArrayList<Product> {
            //Creating an empty arraylist.
            val products = ArrayList<Product>()
            var reference = ""
            try {
                doc2 = Jsoup.connect("https://www.google.com/search?sa=X&authuser=0&biw=1280&bih=619&tbm=shop&q=$product").get()

                slot1 = doc2!!.select("div[data-docid]")

                for(i in 0 until slot1!!.size){
                    if(slot1!![i].select("a").attr("href").toString().split("?")[0].split("/").size>=4){
                        reference = slot1!![i].select("a").attr("href").toString().split("?")[0].split("/")[3]
                    }else{
                        reference = "code introuvable!"
                    }
                    products.add(
                        Product(
                            nom = slot1!![i].select("a > h1,h2,h3,h4,h5,h6").text().toString(),
                            prix = slot1!![i].select("span > span[aria-hidden]").text().toString(),
                            Concurrent = slot1!![i].select("*:last-child a[target=_blank]").text().toString(),
                            "",
                            reference = reference,
                            ""
                        )
                    )
                }
            } catch (s: Exception) {
            }

            return products!!
        }

        override fun onPostExecute(result:  ArrayList<Product>) {
            adapter = RecyclerAdapter(result,this@DisplayResult)
            my_recycler_view.adapter = adapter
            dialog.close()
            titre.text = "nombre de résultat : " + result.size.toString()
        }
    }
}
