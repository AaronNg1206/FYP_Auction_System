package com.nghycp.fyp_auction_system.customer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkLayoutBinding
import com.nghycp.fyp_auction_system.databinding.FragmentRecentAddLayoutBinding

class RecentAddAdapter: RecyclerView.Adapter<RecentAddAdapter.HolderArtwork>{
    private val context: Context
    var artworkList: ArrayList<ModelArtwork>
    //lateinit var recentAddList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentRecentAddLayoutBinding

    constructor(context: Context, artworkList: ArrayList<ModelArtwork>) {
        this.context = context
        this.artworkList = artworkList
       // this.recentAddList = recentAddList

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentRecentAddLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return artworkList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var author : TextView = binding.showAuthor
        var name : TextView = binding.showProduct
        var price : TextView = binding.showPricing
        var image : ImageView = binding.imageProduct
        var description : TextView = binding.showDesc
        var buttonRemove : Button = binding.buttonDelete
        var buttonUpdate : Button = binding.buttonUpdate
    }


    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        //get data
        val model = artworkList[position]
        val id = model.id
        val author = model.artAuthor
        val description = model.artDescription
        val name = model.artName
        val image = model.artImage
        val price = model.artPrice

        //set data
        holder.name.text = name
        holder.price.text= price
        holder.description.text = description
        holder.author.text = author
        Glide.with(context).load(image).into(holder.image)
        holder.buttonUpdate.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", model.id)
            //findNavController(itemView).navigate(R.id.action_recentAddFragment_to_customerUpdateArtwork, bundle)

        }



    }

    init {
        artworkList = ArrayList()
    }
    }

