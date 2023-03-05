package com.nghycp.fyp_auction_system.customer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.FragmentArtworkDisplay
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.BidLayoutBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDetailsBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding
import kotlinx.android.synthetic.main.fragment_view_purchase.view.*

class ArtworkAdapter : RecyclerView.Adapter<ArtworkAdapter.HolderArtwork> {

    private val context: Context
    var artworkList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentArtworkDetailsBinding

    constructor(context:Context, artworkList: ArrayList<ModelArtwork>){
        this.context = context
        this.artworkList = artworkList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork{
        binding = FragmentArtworkDetailsBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderArtwork(binding.root)
    }

    override fun getItemCount(): Int {
        return artworkList.size
    }

    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){

        var author : TextView = binding.showAuthor
        var description : TextView = binding.showDesc
        var name : TextView = binding.showProduct
        var price : TextView = binding.showPricing
        var imageProduct : ImageView = binding.imageProduct
        var buttonAddToCart : Button = binding.buttonAddToCart

    }

    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        val model = artworkList[position]
        val author = model.author
        val description = model.description
        val name = model.artworkName
        val price = model.price
        val imageProduct = model.artworkImage

        holder.author.text = author
        holder.description.text = description
        holder.name.text = name
        holder.price.text = price
        Glide.with(context).load(imageProduct).into(holder.imageProduct)
        holder.buttonAddToCart.setOnClickListener {

            val fragment = FragmentArtworkDisplay()
            val args = Bundle()
            args.putString("description", description)
            args.putString("author", author)
            args.putString("price", price)
            args.putString("img", imageProduct)
            args.putString("name", name)
            fragment.arguments = args

            Navigation.findNavController(holder.buttonAddToCart).navigate(R.id.action_auction_to_fragmentBidProduct,args)
        }

    }

    init {
        artworkList = ArrayList()
    }


}