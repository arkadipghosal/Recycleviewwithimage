package io.callstats.recyleviewwithimage

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.nex.nexcitizen.constant.Constant
import com.nex.nexcitizen.constant.Constant.base_url
import dmax.dialog.SpotsDialog
import io.callstats.recyleviewwithimage.setget.Bookdetailssetget
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var dialog: SpotsDialog? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var detaillist: RecyclerView
    internal var searcharray = ArrayList<Bookdetailssetget>()

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        linearLayoutManager = LinearLayoutManager(this)
        //val list = findViewById(R.id.list) as RecyclerView
        detaillist = findViewById(R.id.detaillist) as RecyclerView

        detaillist.layoutManager = LinearLayoutManager(this)

        showbirthresult();

    }

     fun showbirthresult() {
        dialog = SpotsDialog(this@MainActivity, "Please Wait..")
        // dialog.setMessage("Please Wait ...");
        dialog?.show()
        val url = base_url+"ccLAsEcOSq?indent=2"
       /* val request = HttpPost(url)
        request.setHeader("Content-Type", "application/json")*/
        val jsonObject = JSONObject()
       /* try {
            jsonObject.put("regid", regid)


            Log.v(TAG, jsonObject.toString())


        } catch (e: JSONException) {
            e.printStackTrace()
        }*/

        val queue = Volley.newRequestQueue(this@MainActivity)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,
            null,
            { response ->
                dialog?.dismiss()
                // Log.v("reponse", "" + response);
                Log.v(TAG, response.toString())
                //  Toast.makeText(Login.this, response.toString(), Toast.LENGTH_SHORT).show();

                //   String success = response.getString("result");

                try {


                    //Toast.makeText(getApplicationContext(),resilt,Toast.LENGTH_SHORT).show();


                    val contacts = response.getJSONArray("book_array")
                    for (i in 0 until contacts.length()) {
                        val c = contacts.getJSONObject(i)


                        val book_title = c.getString("book_title")
                        val image = c.getString("image")
                        val author = c.getString("author")


                        searcharray.add(
                            Bookdetailssetget(
                                book_title,
                                image,
                                author

                            )
                        )


                    }


                    var Bookdetailsadapter = Bookdetailsadapter(this, searcharray)
                    //list.setad


                    detaillist.setAdapter(Bookdetailsadapter)
                    detaillist.setLayoutManager(
                        LinearLayoutManager(
                            applicationContext,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    )


                    /*  Searchadapter=new Searchadapter(Birthcertificatepayment.this, R.layout.buildingplanshowpaymentadapter, searcharray);
                                list.setAdapter(Searchadapter);
                                textView2.setVisibility(View.GONE);*/

                    // Toasty.success(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG,true).show();
                    //  Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_SHORT).show();


                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, { error ->
                if (error != null) {
                    dialog?.dismiss()

                    Toast.makeText(
                        this@MainActivity,
                        "Something wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.v(TAG, error.message)
                    VolleyLog.d("Neelmani Karn", "Error: " + error.message)
                    // Log.v(TAG,error.getMessage());
                    //                    Log.v(TAG,error.getMessage());

                    /*  System.out.println(error.networkResponse.statusCode+"))))))))))))))))");
                    System.out.println(error);*/

                }
            })

        queue.add(jsonObjectRequest)

    }




    public class Bookdetailsadapter(
        var ctx: Context,
        private val rogerModelArrayList: ArrayList<Bookdetailssetget>
    ) :
        RecyclerView.Adapter<Bookdetailsadapter.MyViewHolder>() {


        // final lateinit var params: WindowManager.LayoutParams
        var LAYOUT_FLAG: Int = 0
        private val inflater: LayoutInflater


        lateinit var dialogview: Dialog
        lateinit var aList: ArrayList<List<String>>
        val TAG = "Birthceradapter"


        override fun getItemCount(): Int {
            return rogerModelArrayList.size
        }

        init {

            inflater = LayoutInflater.from(ctx)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Bookdetailsadapter.MyViewHolder {

            val view = inflater.inflate(R.layout.adapterlayout, parent, false)

            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: Bookdetailsadapter.MyViewHolder, position: Int) {


            // holder.regnotext.setText(String.valueOf(rogerModelArrayList.get(position).get_regid()));
            holder.author.text = rogerModelArrayList[position]._author
            holder.title.text = rogerModelArrayList[position]._book_title
            Glide.with(ctx)
                .load(rogerModelArrayList[position]._image)

                .into(holder.bookimage);








        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



            var author: TextView
            var title: TextView
            var bookimage: ImageView


            init {

                author = itemView.findViewById(R.id.author) as TextView
                title = itemView.findViewById(R.id.title) as TextView

                bookimage = itemView.findViewById(R.id.bookimage) as ImageView






            }

        }
    }
}