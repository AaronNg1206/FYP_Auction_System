package com.nghycp.fyp_auction_system.history_return

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.databinding.RecyclerreturnBinding

class AdapterReturn : RecyclerView.Adapter<AdapterReturn.HolderBid> {

    private val context: Context
    var showReturnList: ArrayList<ModelReturnNN>
    private lateinit var binding: RecyclerreturnBinding

    constructor(context: Context, showReturnList: ArrayList<ModelReturnNN>) {
        this.context = context
        this.showReturnList = showReturnList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderBid {
        binding = RecyclerreturnBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderBid(binding.root)
    }

    override fun getItemCount(): Int {
        return showReturnList.size
    }

    inner class HolderBid(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameArt: TextView = binding.namePro
        var imageArt: ImageView = binding.imageView2
        var priceArt: TextView = binding.pricepro
        var status: TextView = binding.checkStatus
        // var refund : TextView = binding.refund
    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {
        val model = showReturnList[position]
        val nameArt = model.nameArt
        val priceArt = model.priceArt
        val imageArt = model.imageArt
        val status = model.status
        //val PID = model.PID
        //val refund = model.status

        // holder.refund.text = status
        holder.nameArt.text = nameArt
        holder.priceArt.text = priceArt
        holder.status.text = status
        Glide.with(context).load(imageArt).into(holder.imageArt)

        //showStatus(PID, status, priceArt)
    }

//    private fun showStatus(PID: String, status: String, priceArt: String) {
//
//        val ref =
//            Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                .getReference("Return")
//        ref.child(PID)
//            .addListenerForSingleValueEvent(object : ValueEventListener{
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    for (ds in snapshot.children) {
//
//                        if (status == "Approved") {
////                            binding.textView27.visibility = View.VISIBLE
////                            binding.refund.visibility = View.VISIBLE
//                            binding.refund.text = "Product had been successful refunded ${priceArt}"
//                        } else if (status == "Unsuccessful") {
////                            binding.textView27.visibility = View.VISIBLE
////                            binding.refund.visibility = View.VISIBLE
//                            binding.refund.text = "Unsuccessful to refund"
//                        } else if (status == "Pending") {
//                            binding.textView27.visibility = View.GONE
//                            binding.refund.visibility = View.GONE
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//            })
//
//    }

    init {
        showReturnList = ArrayList()
    }

}