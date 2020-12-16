package com.ngp.goodbarber.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngp.goodbarber.R
import android.widget.MultiAutoCompleteTextView
import android.R.id.edit
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.AsyncTask
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ngp.goodbarber.model.Product
import kotlinx.android.synthetic.main.main_fragment.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net.URL


class MainFragment : Fragment() {
    var doc1: Document? = null
    var doc2 : Document? = null
    var slot1:Elements? = null
    var slot2 : Elements? = null


    companion object {
        fun newInstance() = MainFragment()
    }

    private val COUNTRIES = mutableListOf<String>("Belgium", "France", "Italy", "Germany", "Spain")
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var adapter : ArrayAdapter<String> = ArrayAdapter<String>(this.context!!,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES)

        multiAutoCompleteTextView.setAdapter(adapter)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        //val doc = Jsoup.connect("https://www.google.com/shopping/product/r/FR/2677531610656823108?psb=1&prds=epd:0,prmr:1,cs:1&authuser=0&sa=X&ved=2ahUKEwj8vbSPzsjtAhWsEtMKHQwQCuUQsJMFegQIAhAR").get()

        //message.text = a.text()
        button.setOnClickListener {
            HtmlText().execute()
        }
        if(!isOnline()) {
            Toast.makeText(this.context!!, "tu n'es pas connecté", Toast.LENGTH_LONG).show()
        }



    }

    private inner class HtmlText : AsyncTask<URL, Int, String>() {
        override fun doInBackground(vararg urls: URL): String {

            var name : String? = "test"
            var indice : Int = -1
            var nombreResultat : Int = 0
            /** */
            try {
                doc2 = Jsoup.connect("https://www.google.com/search?sa=X&authuser=0&biw=1280&bih=619&tbm=shop&sxsrf=ALeKk03u0qZW0qBRsgCBmyWS9eAO32QGhg%3A1607811319602&psb=1&q=air+force&oq=air+force&gs_lcp=Cgtwcm9kdWN0cy1jYxADMgQIIxAnMgQIIxAnMgIIADICCAAyAggAMgIIADICCAAyAggAMgIIADICCAA6BQgAEMADOgoIABCxAxCDARBDOgQIABBDOggIABCxAxCDAToKCAAQsQMQgwEQCkoFCDYSATFQxb0CWN_MAmDb0AJoAHAAeACAAb4BiAHcCZIBAzQuN5gBAKABAaoBD3Byb2R1Y3RzLWNjLXdpesABAQ&sclient=products-cc").get()
                //name = doc2!!.select("div > div > div > div:nth-child(1) > a > div > div > div > span").text()
                slot1 = doc2!!.select("div > div > div > div")
                name = slot1!!.eachText().toString()
                /*for(i in 1..(slot1!!.size)){
                    //name = slot1!!.get(i).select("a").text();
                    //.sh-np__product-title.translate-content
                    //https://www.tutorialspoint.com/jsoup/jsoup_use_selector.htm
                    nombreResultat++
                    name = nombreResultat.toString()
                }*/

                /*if(name.contains("air")){
                    indice = name.indexOf("air force")
                }*/
            //https://www.google.com/shopping/product/r/FR/2677531610656823108?psb=1&prds=epd:0,prmr:1,cs:1&authuser=0&sa=X&ved=2ahUKEwj8vbSPzsjtAhWsEtMKHQwQCuUQsJMFegQIAhAR
            } catch (s: Exception) {
            }

            return name!!
        }

        override fun onPostExecute(result: String) {
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show()
            var e = "";
            for( a in parse(result,"€",10))
                e+="("+a+")"
            message.text = e
        }
    }
    fun isOnline(): Boolean {
        val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    fun parse(code : String, delimiteur : String, nombreCaractere : Int): ArrayList<String>{
        val arrayList = ArrayList<String>()//Creating an empty arraylist.
        var tab = code.split(" ")
        for(i in 0..(tab.size-1)){
            if(tab[i].equals(delimiteur)){
                arrayList.add(tab[i-2]+"-"+tab[i-1]+"-"+tab[i])
            }
        }

        return arrayList;
    }

}
