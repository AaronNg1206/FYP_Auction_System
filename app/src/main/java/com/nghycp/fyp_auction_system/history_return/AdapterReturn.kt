package com.nghycp.fyp_auction_system.history_return

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nghycp.fyp_auction_system.databinding.RecyclerreturnBinding

class AdapterReturn : RecyclerView.Adapter<AdapterReturn.HolderBid> {

    private val context: Context
    var showReturnList: ArrayList<ModelReturnNN>
    private lateinit var binding: RecyclerreturnBinding

    constructor(context: Context, showReturnList: ArrayList<ModelReturnNN>){
        this.context = context
        this.showReturnList = showReturnList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid {
        binding = RecyclerreturnBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderBid(binding.root)
    }

    override fun getItemCount(): Int {
        return showReturnList.size
    }
    inner class HolderBid(itemView: View): RecyclerView.ViewHolder(itemView){
        var nameArt : TextView = binding.namePro
        var imageArt : ImageView = binding.imageView2
        var priceArt : TextView = binding.pricepro
        var status : TextView = binding.checkStatus
    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {
        val model = showReturnList[position]
        val nameArt = model.nameArt
        val priceArt = model.priceArt
        val imageArt = model.imageArt
        val status = model.status

        holder.nameArt.text = nameArt
        holder.priceArt.text = priceArt
        holder.status.text = status
        Glide.with(context).load(imageArt).into(holder.imageArt)

    }

    init {
        showReturnList = ArrayList()
    }

}