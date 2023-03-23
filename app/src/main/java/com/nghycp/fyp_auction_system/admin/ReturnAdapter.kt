package com.nghycp.fyp_auction_system.admin

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.protobuf.Value
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FirstshowreturnBinding
import com.nghycp.fyp_auction_system.history_return.ReturnRefund
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ReturnAdapter : RecyclerView.Adapter<ReturnAdapter.HolderBid> {

    private val context: Context
    var showReturnList: ArrayList<ModelReturn>
    private lateinit var binding: FirstshowreturnBinding

    constructor(context: Context, showReturnList: ArrayList<ModelReturn>){
        this.context = context
        this.showReturnList = showReturnList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid {
        binding = FirstshowreturnBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderBid(binding.root)
    }

    override fun getItemCount(): Int {
        return showReturnList.size
    }
    inner class HolderBid(itemView: View): RecyclerView.ViewHolder(itemView){
        var nameArt : TextView = binding.nameShow
        var imageArt : ImageView = binding.imgShow
        var priceArt : TextView = binding.showPrice
        var btnGo : Button = binding.btnGo
        var PID : TextView = binding.PIDSSHOW
        var reason : TextView = binding.reasonReturnShow
        var desc : TextView = binding.descReturnShow
    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {
        val model = showReturnList[position]
        val nameArt = model.nameArt
        val priceArt = model.priceArt
        val reason = model.reason
        val desc = model.desc
        val imageArt = model.imageArt
        val PID = model.PID


        holder.nameArt.text = nameArt
        holder.priceArt.text = priceArt
        holder.reason.text = reason
        holder.desc.text = desc
        holder.PID.text = PID
        Glide.with(context).load(imageArt).into(holder.imageArt)

        holder.btnGo.setOnClickListener {

            val fragment = AdminProceedRefund()
            var args = Bundle()
            args.putString("nameArt",nameArt)
            args.putString("priceArt",priceArt)
            args.putString("imageArt",imageArt)
            args.putString("reason",reason)
            args.putString("desc",desc)
            args.putString("PID",PID)
//            args.putString("",)
//            args.putString("",)
            fragment.setArguments(args)

            Navigation.findNavController(holder.btnGo).navigate(R.id.action_adminRefund_to_adminProceedRefund,args)

        }

    }

    init {
        showReturnList = ArrayList()
    }

}