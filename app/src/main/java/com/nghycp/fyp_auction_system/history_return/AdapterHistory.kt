package com.nghycp.fyp_auction_system.history_return

import android.content.Context
import android.media.Image
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
import com.nghycp.fyp_auction_system.databinding.RecyclerviewPurchaseBinding
import kotlinx.android.synthetic.main.fragment_return_refund.view.*

class AdapterHistory : RecyclerView.Adapter<AdapterHistory.HolderBid>{

    private val context: Context
    var showArrayList: ArrayList<ModelShow>
    private lateinit var binding: RecyclerviewPurchaseBinding

    constructor(context: Context, showArrayList: ArrayList<ModelShow>){
        this.context = context
        this.showArrayList = showArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid {
        binding = RecyclerviewPurchaseBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderBid(binding.root)
    }

    override fun getItemCount(): Int {
        return showArrayList.size
    }
    inner class HolderBid(itemView: View): RecyclerView.ViewHolder(itemView){
        var artName : TextView = binding.nameShow
        var artImage : ImageView = binding.returnImg
        var artPrice : TextView = binding.priceShow
        var btnGo : Button = binding.btnReturn
        var status : TextView = binding.status
    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {
        val model = showArrayList[position]
        val artName = model.artName
        val artPrice = model.artPrice
        val artImage = model.artImage
        val status = model.status

        holder.artName.text = artName
        holder.status.text = status
        holder.artPrice.text = artPrice
        Glide.with(context).load(artImage).into(holder.artImage)

        holder.btnGo.setOnClickListener {
            val fragment = ReturnRefund()
            val args = Bundle()
            args.putString("status", status)
            args.putString("artImage",artImage)
            args.putString("artName",artName)
            args.putString("artPrice",artPrice)
            fragment.setArguments(args)

            Navigation.findNavController(holder.btnGo).navigate(R.id.action_viewPurchase_to_returnRefund,args)
        }

    }

    init {
        showArrayList = ArrayList()
    }

}