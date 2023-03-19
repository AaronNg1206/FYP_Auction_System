package com.nghycp.fyp_auction_system.bidding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nghycp.fyp_auction_system.databinding.ProcessShowBinding

class ShowBidUserAdpter : RecyclerView.Adapter<ShowBidUserAdpter.HolderBid>{

    private val context: Context
    var bidShowArrayList: MutableList<ModelBidUser>
    private lateinit var binding: ProcessShowBinding

    constructor(context: Context, bidShowArrayList: MutableList<ModelBidUser>){
        this.context = context
        this.bidShowArrayList = bidShowArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid {
        binding = ProcessShowBinding.inflate(LayoutInflater.from(context), parent,false)

        return HolderBid(binding.root)
    }

    override fun getItemCount(): Int {
        return bidShowArrayList.size
    }

    inner class HolderBid(itemView: View): RecyclerView.ViewHolder(itemView){
        var userName : TextView = binding.showUser
        var price : TextView = binding.priceShow
    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {

        val model = bidShowArrayList[position]
        val userName = model.userName
        val price = model.price

        holder.userName.text = userName
        holder.price.text = price

    }

    init {
        bidShowArrayList = ArrayList()
    }

}