package com.ngp.goodbarber.ui.main

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import com.ngp.goodbarber.R
import com.ngp.goodbarber.mDialogFragment
import com.ngp.goodbarber.model.Product
import com.ngp.goodbarber.services.OkHttpRequest
import kotlinx.android.synthetic.main.activity_display_result.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class DisplayTrackedPrice : AppCompatActivity() {
    private lateinit var recycler_view : RecyclerView
    private lateinit var tv : TextView
    private var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var adapter: RecyclerAdapterPriceTracked

    private lateinit var dialog : mDialogFragment

    lateinit var fm: FragmentManager

    var product = ""
    var reference = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_result)

        layoutManager = LinearLayoutManager(this)
        my_recycler_view.layoutManager = layoutManager

        fm = supportFragmentManager
        product = intent.getStringExtra("nom")!!
        reference = intent.getStringExtra("reference")!!
        Toast.makeText(this@DisplayTrackedPrice,reference+",",Toast.LENGTH_LONG).show()
        dialog = mDialogFragment()
        dialog.show(fm, "mDialogFragment")
        SelectData(product,reference)
    }

/**************************************/
fun SelectData(product : String,reference : String) {
    var client = OkHttpClient()
    var request= OkHttpRequest(client)
    val url = "https://www.ng-plus.com/pandroid/hackathon/gettrackedproducts.php?id_product="+product+"&reference="+reference

    var array_product : ArrayList<Product> = ArrayList()
    //val url = "http://api.plos.org/search?q=title:%22Drosophila%22%20and%20body:%22RNA%22&fl=id,abstract&wt=json&indent=on"
    request.GET(url, object: Callback {
        override fun onResponse(call: Call?, response: Response) {
            val responseData = response.body()?.string()
            runOnUiThread{
                try {
                    var response : String
                    var json = JSONArray(responseData)
                    lateinit var mJSONObject : JSONObject

                    //mJSONObject = json.getJSONObject(0)
                    //array_product!!.add(Product(mJSONObject.getString("response"),mJSONObject.getString("response"),mJSONObject.getString("response"),mJSONObject.getString("response"),mJSONObject.getString("response"),mJSONObject.getString("response")))

                    for(j in 0..(json.length()-1)){
                        mJSONObject = json.getJSONObject(j);
                        array_product?.add(Product(mJSONObject.getString("id_product"),mJSONObject.getString("price"),mJSONObject.getString("id_product_google"),mJSONObject.getString("reference"),mJSONObject.getString("date_creation"),mJSONObject.getString("date_update")))
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                adapter = RecyclerAdapterPriceTracked(array_product,this@DisplayTrackedPrice)
                my_recycler_view.adapter = adapter
                dialog.close()
            }
        }

        override fun onFailure(call: Call?, e: IOException?) {
            println("Activity Failure.")
        }
    })
}
}
