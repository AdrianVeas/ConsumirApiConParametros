package com.example.consumirapiconparametros

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onClick(view: View?) {
        val textView = findViewById<TextView>(R.id.txtresponse)
        textView.movementMethod = ScrollingMovementMethod()
        val url = "https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList"

        val request = object: StringRequest(
            Request.Method.GET,
            url, { response ->
                try {
                    textView.text = ""
                    val JArray = JSONArray(response.toString())

                    for (i in 0 until JArray.length()) {
                        val code: String = JArray.getJSONObject(i).getString("code").toString()
                        val name: String = JArray.getJSONObject(i).getString("name").toString()

                        textView.append("Code: $code \nName: $name \n\n")
                    }

                }catch (e: JSONException){
                    textView.text = e.message
                }
            },
            {error ->
                textView.text = error.message
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
            val headers = HashMap<String, String>()
            headers["Public-Merchant-Id"] = "b731f14fb3f64274a1d5411575c624fe"
            return headers}
        };

        MySingleton.getInstance(applicationContext).addToRequestQueue(request)
    }
}