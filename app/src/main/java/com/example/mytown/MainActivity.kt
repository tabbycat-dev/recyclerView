package com.example.mytown

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytown.data.Location
import com.example.mytown.utilities.FileHelper

class MainActivity : AppCompatActivity() {
    private lateinit var  locationList :  ArrayList<Location>
    private lateinit var rvLocation: RecyclerView
    private lateinit var mLayoutManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
//        val text = FileHelper.getTextFromResources(app, R.raw.monster_data)
        val text = FileHelper.getTextFromResources(applicationContext, R.raw.au_locations)
        //Log.i("FILES", text)
        parse(text)
        initialiseUI()
    }
    fun parse(text: String){
        val propertyRegex = ",".toRegex()
        val cityRegex = "/".toRegex()
        locationList =  ArrayList<Location>()
        val lines: List<String> =
            text.split(System.getProperty("line.separator"))
        lines.forEach {
            val (name, lat, long, timezone) = it.split(propertyRegex)
            var fav = false
            timezone.split(cityRegex)[1].apply {
                if (this.equals("Melbourne")){
                    fav = !fav
                }
            }
            val location = Location(name, timezone, long.toFloat(), lat.toFloat(), fav)
            locationList.add(location)
            //Log.i("FILES", "$location ${locationList.indexOf(location)}")
        }
        Log.i("FILES", "${locationList.size}")
    }

    fun initialiseUI() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //rvBooks.setHasFixedSize(true);

        // use a linear layout manager
        rvLocation = findViewById<RecyclerView>(R.id.rvLocationList)
        mLayoutManager = LinearLayoutManager(this)
        rvLocation.setLayoutManager(mLayoutManager)

        // specify an adapter (see also next example)
        val drawableFav: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_fav, null)
        val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_channel_foreground, null)

        val mAdapter = RecyclerViewAdapter(context = applicationContext, locationList = locationList, drawable = drawable, drawableFav = drawableFav)
        rvLocation.setAdapter(mAdapter)
    }

}