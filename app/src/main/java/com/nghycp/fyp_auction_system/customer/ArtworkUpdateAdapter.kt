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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkLayoutBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkUpdateBinding
import kotlinx.android.synthetic.main.fragment_login.view.*

class ArtworkUpdateAdapter: RecyclerView.Adapter<ArtworkUpdateAdapter.HolderArtwork>{
    private val context: Context
    var artworkList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentArtworkUpdateBinding

    constructor(context: Context, artworkList: ArrayList<ModelArtwork>) {
        this.context = context
        this.artworkList = artworkList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentArtworkUpdateBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return artworkList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.editTextProductName
        var price : TextView = binding.editTextPrice
        var author : TextView = binding.editTextAuthor
        var image : ImageView = binding.imageViewartwork
        var description : TextView = binding.editTextDescription
        var OpenGallery : Button = binding.buttonOpenGallery
        var buttonUpdate : Button = binding.buttonUpdate
    }
/*    class HolderArtwork(itemView: View) : RecyclerView.ViewHolder(itemView) {
     val name = itemView.findViewById<TextView>(R.id.editText_productName)
      val price = itemView.findViewById<TextView>(R.id.editTextPrice)
      val author = itemView.findViewById<TextView>(R.id.editTextAuthor)
      val image = itemView.findViewById<ImageView>(R.id.imageViewartwork)
      val description = itemView.findViewById<TextView>(R.id.editTextDescription)
      val buttonUpdate = itemView.findViewById<TextView>(R.id.buttonUpdate)
      val OpenGallery = itemView.findViewById<TextView>(R.id.button_openGallery)
  }*/
  override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
      //get data
      val model = artworkList[position]
      val author = model.artAuthor
      val description = model.artDescription
      val name = model.artName
      val image = model.artImage
      val price = model.artPrice

      //set data
      holder.name.text = name
      holder.price.text= price
      holder.author.text = author

      holder.description.text = description
      Glide.with(context).load(image).into(holder.image)
     holder.buttonUpdate.setOnClickListener{

     }
      holder.OpenGallery.setOnClickListener{

      }

  }

  init {
      artworkList = ArrayList()
  }
  }

