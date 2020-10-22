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
    private val propertyRegex = ",".toRegex()
    private val cityRegex = "/".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialiseUI()
    }

    fun initialiseUI() {
//        val text = FileHelper.getTextFromResources(app, R.raw.monster_data)
        val fullText = getTextFromResources( R.raw.au_locations)
        //Log.i("FILES", text)
        parseFullTextToLines(fullText)
        setRecyclerView()
    }
    fun parseFullTextToLines(text: String){
        locationList =  ArrayList<Location>()                       // declare new locations array list
        val lines: List<String> =
            text.split(System.getProperty("line.separator"))        // parse and save lines into a list of String
        lines.forEach {
            parseLineToLocation(it) //pass each line to function
        }
    }
    fun parseLineToLocation(lineText : String){
        val (name, lat, long, timezone) = lineText.split(propertyRegex) //split object by comma "," to find name, lat, long, timezone in order
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

    fun setRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.rvLocationList) //call recycler view layout widget

        //specify fav icons
        val drawableFav: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_fav, null)
        val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null)

        // specify an adapter
        val mAdapter = RecyclerViewAdapter(
                context = applicationContext,
                locationList = locationList,
                drawable = drawable,
                drawableFav = drawableFav)
        recyclerView.setAdapter(mAdapter)
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