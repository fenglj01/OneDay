package com.knight.oneday.views.choice

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.knight.oneday.R

class WheelStringAdapter(private val contents: List<String>) :
    WheelView.WheelAdapter<WheelStringViewHolder>() {
    override fun getItemCount(): Int = contents.size

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        viewType: Int
    ): WheelStringViewHolder {
        return WheelStringViewHolder(inflater.inflate(R.layout.hour_view, null, false))
    }

    override fun onBindViewHolder(holder: WheelStringViewHolder, position: Int) {
        holder.stringTv.text = contents[position]
    }
}

class WheelStringViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val stringTv: TextView = view.findViewById(R.id.hour_tv)
}