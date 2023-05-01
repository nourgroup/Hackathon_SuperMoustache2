package com.ngp.goodbarber.ui.main

import android.app.PendingIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngp.goodbarber.R
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.ngp.goodbarber.DisplayResult
import com.ngp.goodbarber.DisplayTrackedProduct
import com.ngp.goodbarber.model.Product
import kotlinx.android.synthetic.main.main_fragment.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class MainFragment : Fragment() {


    companion object {
        fun newInstance() = MainFragment()
    }


    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        //val doc = Jsoup.connect("https://www.google.com/shopping/product/r/FR/2677531610656823108?psb=1&prds=epd:0,prmr:1,cs:1&authuser=0&sa=X&ved=2ahUKEwj8vbSPzsjtAhWsEtMKHQwQCuUQsJMFegQIAhAR").get()

        /*utiliser Jsoup*/
        button.setOnClickListener {
            //message.text = name
            val intent = Intent(this@MainFragment.context, DisplayResult::class.java)
            // To pass any data to next activity
            intent.putExtra("nomProduit", etchercher.text.toString().replace(" ","+"))
            // start your next activity
            //intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
        button3.setOnClickListener {
            //message.text = name
            val intent1 = Intent(this@MainFragment.context, DisplayTrackedProduct::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent1)
        }
        if(!isOnline()) {
            Toast.makeText(this.context!!, "Tu n'es pas connecté", Toast.LENGTH_LONG).show()
        }
    }


    fun isOnline(): Boolean {
        val cm = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }


}
