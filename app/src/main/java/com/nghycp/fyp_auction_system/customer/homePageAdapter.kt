package com.nghycp.fyp_auction_system.customer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.FragmentUserHomePage
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentSaleshomeLayoutBinding

class homePageAdapter: RecyclerView.Adapter<homePageAdapter.HolderArtwork>{
    private val context: Context
    var artworkList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentSaleshomeLayoutBinding

    constructor(context: Context, artworkList: ArrayList<ModelArtwork>) {
        this.context = context
        this.artworkList = artworkList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentSaleshomeLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return artworkList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        //var name : TextView = binding.artName
        //var price : TextView = binding.artPrice
        var image : ImageView = binding.artImage
        //var description : TextView = binding.artDescription
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

        Glide.with(context).load(image).into(holder.image)
        holder.image.setOnClickListener {

            val fragment = artworkDetailsFragment()
            val args = Bundle()
            args.putString("id", id)
            args.putString("artDescription", description)
            args.putString("artAuthor",author)
            args.putString("artPrice", price)
            args.putString("artImage", image)
            args.putString("artName", name)
            fragment.setArguments(args)

           Navigation.findNavController(holder.image).navigate(R.id.action_fragmentUserHomePage_to_artworkDetailsFragment,args)


        }


    }

    init {
        artworkList = ArrayList()
    }
    }

