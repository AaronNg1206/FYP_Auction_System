package com.nghycp.fyp_auction_system.bidding

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
import com.nghycp.fyp_auction_system.databinding.BidLayoutBinding

class ProcessAdapter : RecyclerView.Adapter<ProcessAdapter.HolderBid>{

    private val context: Context
    var bidArrayList: ArrayList<ModelBid>
    private lateinit var binding: BidLayoutBinding

    constructor(context: Context, bidArrayList: ArrayList<ModelBid>){
        this.context = context
        this.bidArrayList = bidArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid{
        binding = BidLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return HolderBid(binding.root)
    }

    override fun getItemCount(): Int {
        return bidArrayList.size
    }

    inner class HolderBid(itemView: View): RecyclerView.ViewHolder(itemView){

        var artist : TextView = binding.showArtist
        var desc : TextView = binding.showDesc
        var name : TextView = binding.nameShow
        var price : TextView = binding.showPrice
        var img : ImageView = binding.imgShow
        var btnApply : TextView = binding.btnApply
        var expDate : TextView = binding.expDate
        var btnGo : Button = binding.btnGo

    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {

        val model = bidArrayList[position]
        val artist = model.artist
        val desc = model.desc
        val name = model.name
        val price = model.price
        val img = model.profileImage
        val expDate = model.expDate

        holder.artist.text = artist
        holder.desc.text = desc
        holder.name.text = name
        holder.price.text = price
        holder.expDate.text = expDate
        //holder.
        Glide.with(context).load(img).into(holder.img)

        holder.btnGo.setOnClickListener {
            val fragment = FragmentBidProcess()
            val args = Bundle()
            args.putString("name", name)
            args.putString("img", img)
            args.putLong("expDate", expDate.toLong())
            fragment.setArguments(args)

            Navigation.findNavController(holder.btnGo).navigate(R.id.action_auction_to_fragmentBidProcess,args)
        }

    }

    init {
        bidArrayList = ArrayList()
    }

}