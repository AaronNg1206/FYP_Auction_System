package com.nghycp.fyp_auction_system.report

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkLayoutBinding
import com.nghycp.fyp_auction_system.databinding.FragmentMonthlyBasedBinding

class ReportAdapter: RecyclerView.Adapter<ReportAdapter.HolderArtwork>{
    private val context: Context
    var artworkList: ArrayList<ModelReport>
    private lateinit var binding: FragmentMonthlyBasedBinding
    constructor(context: Context, artworkList: ArrayList<ModelReport>) {
        this.context = context
        this.artworkList = artworkList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentMonthlyBasedBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return artworkList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
/*        var name : TextView = binding.artName
        var price : TextView = binding.artPrice
        var image : Ima`geView = binding.artImage
        var description : TextView = binding.artDescription*/
    }
    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        //get data
        val model = artworkList[position]
        val author = model.artAuthor
        val description = model.artDescription
        val name = model.artName
        val image = model.artImage
        val price = model.artPrice
        val id = model.id
        val date = model.date
    }
    init {
        artworkList = ArrayList()
    }
}

