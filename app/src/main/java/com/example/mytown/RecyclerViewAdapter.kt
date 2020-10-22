package com.example.mytown

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mytown.data.Location

class RecyclerViewAdapter(
        val context: Context,
        val locationList: ArrayList<Location>,
        val drawableFav: Drawable?,
        val drawable: Drawable?
        ): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()

{
    override fun getItemCount() = locationList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.location_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = locationList[position]
        with(holder) {
            locationNameTv?.let {
                it.text = location.locationName
            }
            timezoneTv?.text = location.timezone
            if (location.favorite){
                iconIV?.setImageDrawable(drawableFav)
            }else{
                iconIV?.setImageDrawable(drawable)
            }

        }
    }

    inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        val locationNameTv = itemView.findViewById<TextView>(R.id.locationNameTv)
        val timezoneTv = itemView.findViewById<TextView>(R.id.timezoneTv)
        val iconIV = itemView.findViewById<ImageView>(R.id.imageTv)
    }
}