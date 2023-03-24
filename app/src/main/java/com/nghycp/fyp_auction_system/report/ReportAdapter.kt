package com.nghycp.fyp_auction_system.report

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.databinding.FragmentMonthlyReportLayoutBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReportAdapter(monthlyReportList: List<ModelReport>, context: Context)
    : RecyclerView.Adapter<ReportAdapter.HolderArtwork>(){
    private val context: Context

    var monthlyReportList: List<ModelReport>
    private lateinit var binding: FragmentMonthlyReportLayoutBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentMonthlyReportLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return monthlyReportList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.artName
        var price : TextView = binding.artPrice
        var image : ImageView = binding.artImage
    }

    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        //get data
        val model = monthlyReportList[position]
        val name = model.artName
        val image = model.artImage
        val price = model.artPrice

        holder.name.text = name
        holder.price.text= price
        Glide.with(context).load(image).into(holder.image)

    }
    init {
        this.monthlyReportList = monthlyReportList
        this.context = context
    }
}

