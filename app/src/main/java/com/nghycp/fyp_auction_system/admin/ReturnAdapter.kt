package com.nghycp.fyp_auction_system.admin

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
import com.nghycp.fyp_auction_system.databinding.AdminviewreturnBinding
import com.nghycp.fyp_auction_system.history_return.ReturnRefund

class ReturnAdapter : RecyclerView.Adapter<ReturnAdapter.HolderBid> {

    private val context: Context
    var showReturnList: ArrayList<ModelReturn>
    private lateinit var binding: AdminviewreturnBinding

    constructor(context: Context, showReturnList: ArrayList<ModelReturn>){
        this.context = context
        this.showReturnList = showReturnList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid {
        binding = AdminviewreturnBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderBid(binding.root)
    }

    override fun getItemCount(): Int {
        return showReturnList.size
    }
    inner class HolderBid(itemView: View): RecyclerView.ViewHolder(itemView){
        var nameArt : TextView = binding.returnName
        var imageArt : ImageView = binding.returnImg
        var priceArt : TextView = binding.returnPrice
        var reason : TextView = binding.reason
        var desc : TextView = binding.returnDesc
    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {
        val model = showReturnList[position]
        val nameArt = model.nameArt
        val priceArt = model.priceArt
        val reason = model.reason
        val desc = model.desc
        val imageArt = model.imageArt
        val status = model.status

        holder.nameArt.text = nameArt
        holder.priceArt.text = priceArt
        holder.reason.text = reason
        holder.desc.text = desc
        Glide.with(context).load(imageArt).into(holder.imageArt)

    }

    init {
        showReturnList = ArrayList()
    }

}