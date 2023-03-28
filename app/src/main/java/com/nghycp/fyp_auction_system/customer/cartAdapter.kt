package com.nghycp.fyp_auction_system.customer

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAddToCartBinding
import com.nghycp.fyp_auction_system.databinding.FragmentAddToCartLayoutBinding
import kotlinx.android.synthetic.main.fragment_add_to_cart_layout.*
import kotlinx.android.synthetic.main.fragment_add_to_cart_layout.view.*

class cartAdapter : RecyclerView.Adapter<cartAdapter.HolderArtwork>{
    private val context: Context
    private val checkedCheckBox = mutableListOf<ModelArtwork>()
    var addToCartList : ArrayList<ModelArtwork>
    private lateinit var binding: FragmentAddToCartLayoutBinding
    //private lateinit var binding1: FragmentAddToCartBinding

    constructor(context: Context, addToCartList: ArrayList<ModelArtwork>) {
        this.context = context
        this.addToCartList = addToCartList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentAddToCartLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        //binding1 = FragmentAddToCartBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderArtwork(binding.root)
        //return HolderArtwork(binding1.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return addToCartList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.artName
        var price : TextView = binding.artPrice
        var image : ImageView = binding.artImage
        var description : TextView = binding.artDescription
        var author : TextView = binding.artAuthor
        var checkBox : CheckBox = binding.checkBox
        //var buttonRemove : Button = binding1.buttonRemove
       // var buttonCheckOut : Button = binding1.buttonCheckOut
    }
    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        //get data
        val model = addToCartList[position]
        val author = model.artAuthor
        val description = model.artDescription
        val name = model.artName
        val image = model.artImage
        val price = model.artPrice
        val checkBox = model.isChecked

        //set data
        holder.name.text = name
        holder.price.text= price
        holder.description.text = description
        holder.author.text = author

        Glide.with(context).load(image).into(holder.image)
        holder.checkBox.isChecked = checkBox

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            model.isChecked = isChecked
            if (isChecked) {

                checkedCheckBox.add(model)
            } else {
                checkedCheckBox.remove(model)
            }
        }


    }



    init {
        addToCartList = ArrayList()
    }
    fun getCheckedItems(): List<ModelArtwork> {
        return checkedCheckBox.toList()
    }

}