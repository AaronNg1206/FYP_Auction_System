package com.nghycp.fyp_auction_system.customer

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkLayoutBinding
import com.nghycp.fyp_auction_system.databinding.FragmentRecentAddLayoutBinding

class RecentAddAdapter: RecyclerView.Adapter<RecentAddAdapter.HolderArtwork>{
    private val context: Context
    var artworkList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentRecentAddLayoutBinding

    constructor(context: Context, artworkList: ArrayList<ModelArtwork>) {
        this.context = context
        this.artworkList = artworkList

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
            val fragment = artworkDetailsFragment()
            val args = Bundle()
            args.putString("id", id)
            args.putString("artDescription", description)
            args.putString("artAuthor",author)
            args.putString("artPrice", price)
            args.putString("artImage", image)
            args.putString("artName", name)
            fragment.setArguments(args)

            Navigation.findNavController(holder.image).navigate(R.id.action_recentAddFragment2_to_customerUpdateArtwork,args)
            //Navigation.findNavController().navigate(R.id.action_recentAddFragment2_to_customerUpdateArtwork, bundle)
        }
        holder.buttonRemove.setOnClickListener {
            val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("artwork")
            Log.d("cc",id)
            ref.child(id).removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Artwork removed successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to remove artwork", Toast.LENGTH_SHORT).show()
                }
        }



    }
    init {
        artworkList = ArrayList()
    }
    }

