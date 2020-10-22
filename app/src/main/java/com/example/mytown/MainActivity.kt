package com.example.mytown

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytown.data.Location

class MainActivity : AppCompatActivity() {
    private lateinit var locationList :  ArrayList<Location>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
//        val text = FileHelper.getTextFromResources(app, R.raw.monster_data)
        val text = getTextFromResources( R.raw.au_locations)
        //Log.i("FILES", text)
        parse(text)
        setRecyclerView()
    }
    fun parse(text: String){
        val propertyRegex = ",".toRegex()
        val cityRegex = "/".toRegex()
        locationList =  ArrayList<Location>()                       // declare new locations array list
        val lines: List<String> =
            text.split(System.getProperty("line.separator"))        // parse and save lines into a list of String
        lines.forEach {                                             //loop over each line in List,
            val (name, lat, long, timezone) = it.split(propertyRegex) //split object by comma "," to find name, lat, long, timezone in order
            var fav = false                                         //set fav to false
            timezone.split(cityRegex)[1].apply {                    // split timezone with "/" to find city name
                if (this.equals("Melbourne")){                      //for Aus/Melbourne timezone, set fav to true
                    fav = !fav
                }
            }
            val location = Location(name, timezone, long.toFloat(), lat.toFloat(), fav)//create a new location object
            locationList.add(location)                                                 //add location to lists of locations
            //Log.i("FILES", "$location ${locationList.indexOf(location)}")
        }
        Log.i("FILES", "${locationList.size}")
    }

    fun setRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.rvLocationList) //call recycler view layout widget

        //specify image icon (fav or not)
        val drawableFav: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_fav, null)
        val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null)

        // specify an adapter
        val mAdapter = RecyclerViewAdapter(context = applicationContext, locationList = locationList, drawable = drawable, drawableFav = drawableFav)//{
//            showToast("${it.latitude} ,${it.longitude} ") //click on each row will trigger showToast func
//        }
        recyclerView.setAdapter(mAdapter)
    }

    private fun showToast(text: String) {
        //Toast.makeText(applicationContext,text,Toast.LENGTH_SHORT).show()
    }

    //  file helper getTextFromResources
    fun getTextFromResources( resourceId: Int): String {
        return applicationContext.resources.openRawResource(resourceId).use {
            it.bufferedReader().use {
                it.readText()
            }
        }
    }

}