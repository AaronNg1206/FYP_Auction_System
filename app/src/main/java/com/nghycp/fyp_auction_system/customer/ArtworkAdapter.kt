package com.nghycp.fyp_auction_system.customer

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.FragmentArtworkDisplay
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.bidding.FragmentBidProduct
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDetailsBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkLayoutBinding

class ArtworkAdapter: RecyclerView.Adapter<ArtworkAdapter.HolderArtwork>{
    private val context: Context
    var artworkList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentArtworkLayoutBinding

    constructor(context: Context, artworkList: ArrayList<ModelArtwork>) {
        this.context = context
        this.artworkList = artworkList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentArtworkLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return artworkList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.artworkName
        //var author : TextView = binding.
        //var description : TextView = binding.
        var price : TextView = binding.artworkPrice
        var image : ImageView = binding.artworkImage
        //var applybtn: Button = binding.

    }
    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        //get data
        val model = artworkList[position]
        val author = model.author
        val description = model.description
        val name = model.artworkName
        val image = model.artworkImage
        val price = model.price

        //set data
        holder.name.text = name
        holder.price.text= price
        //holder.author.text = author
        //holder.description.text = description
        Glide.with(context).load(image).into(holder.image)
        holder.image.setOnClickListener {

            val fragment = artworkDetailsFragment()
            val args = Bundle()
            args.putString("artDescription", description)
            args.putString("artAuthor",author)
            args.putString("artPrice", price)
            args.putString("artImage", image)
            args.putString("artName", name)
            fragment.setArguments(args)

            Navigation.findNavController(holder.image).navigate(R.id.action_fragmentArtworkDisplay_to_artworkDetailsFragment,args)
        }


    }

    init {
        artworkList = ArrayList()
    }
    }

