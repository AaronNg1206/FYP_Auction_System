package com.nghycp.fyp_auction_system.Payment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentPaymentLayoutBinding
import kotlinx.android.synthetic.main.fragment_artwork_layout.*

class paymentAdapter : RecyclerView.Adapter<paymentAdapter.HolderArtwork>{
    private val context: Context
    var paymentList: ArrayList<ModelArtwork>
    private lateinit var binding: FragmentPaymentLayoutBinding
    val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("paid")
    private lateinit var firebaseAuth: FirebaseAuth
    constructor(context: Context, paymentList: ArrayList<ModelArtwork>) {
        this.context = context
        this.paymentList = paymentList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderArtwork {
        binding = FragmentPaymentLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        firebaseAuth = FirebaseAuth.getInstance()
        return HolderArtwork(binding.root)
    }
    override fun getItemCount(): Int {
        //number of items in list
        return paymentList.size
    }
    inner class HolderArtwork(itemView: View): RecyclerView.ViewHolder(itemView){
        var name : TextView = binding.artName
        var price : TextView = binding.artPrice
        var image : ImageView = binding.artImage
    }
    override fun onBindViewHolder(holder: HolderArtwork, position: Int) {
        //get data
        val model = paymentList[position]
        val name = model.artName
        val image = model.artImage
        val price = model.artPrice

        //set data
        holder.name.text = name
        holder.price.text= price
        Glide.with(context).load(image).into(holder.image)

    }



    init {
        paymentList = ArrayList()
    }


}