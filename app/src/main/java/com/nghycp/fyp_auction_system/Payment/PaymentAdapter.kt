package com.nghycp.fyp_auction_system.Payment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentPaymentLayoutBinding

class paymentAdapter : RecyclerView.Adapter<paymentAdapter.HolderArtwork>{
    private val context: Context
    var paymentList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentPaymentLayoutBinding

    constructor(context: Context, paymentList: ArrayList<ModelArtwork>) {
        this.context = context
        this.paymentList = paymentList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentPaymentLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        //binding1 = FragmentAddToCartBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderArtwork(binding.root)
        //return HolderArtwork(binding1.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return paymentList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.artName
        var price : TextView = binding.artPrice
        var image : ImageView = binding.artImage
        /* var description : TextView = binding.artDescription
         var author : TextView = binding.artAuthor
         var checkBox : CheckBox = binding.checkBox
         var buttonRemove : Button = binding1.buttonRemove
         var buttonCheckOut : Button = binding1.buttonCheckOut*/
    }
    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        //get data
        val model = paymentList[position]
        val author = model.artAuthor
        val description = model.artDescription
        val name = model.artName
        val image = model.artImage
        val price = model.artPrice
        val checkBox = model.isChecked


        //set data
        holder.name.text = name
        holder.price.text= price

        Glide.with(context).load(image).into(holder.image)


    }



    init {
        paymentList = ArrayList()
    }


}