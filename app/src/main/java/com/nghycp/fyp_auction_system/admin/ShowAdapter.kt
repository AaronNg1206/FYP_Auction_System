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
import com.nghycp.fyp_auction_system.bidding.FragmentBidProduct
import com.nghycp.fyp_auction_system.databinding.AdminBidShowBinding

class ShowAdapter :RecyclerView.Adapter<ShowAdapter.HolderBid> {

    private val context: Context
    var bidArrayList: ArrayList<ModedlBidAdmin>
    private lateinit var binding: AdminBidShowBinding

    constructor(context: Context, bidArrayList: ArrayList<ModedlBidAdmin>){
        this.context = context
        this.bidArrayList = bidArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid{
        binding = AdminBidShowBinding.inflate(LayoutInflater.from(context),parent,false)

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
        var btnApply : TextView = binding.btnViewMore
        var expDate : TextView = binding.expDate

    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {

        val model = bidArrayList[position]
        val artist = model.artist
        val desc = model.desc
        val name = model.name
        val price = model.price
        val img = model.profileImage
        val expDate = model.expDate
        val id = model.id

        holder.artist.text = artist
        holder.desc.text = desc
        holder.name.text = name
        holder.price.text = price
        holder.expDate.text = expDate
        //holder.
        Glide.with(context).load(img).into(holder.img)
        holder.btnApply.setOnClickListener {

            val fragment = AdminViewMore()
            val args = Bundle()
            args.putString("desc", desc)
            args.putString("artist", artist)
            args.putLong("expDate", expDate.toLong())
            args.putString("price", price)
            args.putString("img", img)
            args.putString("name", name)
            fragment.setArguments(args)

            Navigation.findNavController(holder.btnApply).navigate(R.id.action_adminShowBid_to_adminViewMore,args)
        }

    }

    init {
        bidArrayList = ArrayList()
    }

}