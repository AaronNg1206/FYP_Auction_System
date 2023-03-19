package com.nghycp.fyp_auction_system.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.databinding.BidnormalBinding

class ShowNormalAdapter: RecyclerView.Adapter<ShowNormalAdapter.HolderArtwork>{
    private val context: Context
    var artworkList: ArrayList<ModelNormal>
    private lateinit var binding: BidnormalBinding

    constructor(context: Context, artworkList: ArrayList<ModelNormal>) {
        this.context = context
        this.artworkList = artworkList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = BidnormalBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return artworkList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.artName
        var price : TextView = binding.artPrice
        var image : ImageView = binding.artImage
        var description : TextView = binding.artDescription
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

        //set data
        holder.name.text = name
        holder.price.text= price
        holder.description.text = description

        Glide.with(context).load(image).into(holder.image)

    }

    init {
        artworkList = ArrayList()
    }
}