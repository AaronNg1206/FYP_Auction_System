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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.AdminviewreturnBinding
import com.nghycp.fyp_auction_system.history_return.ReturnRefund

class ReturnAdapter : RecyclerView.Adapter<ReturnAdapter.HolderBid> {

    private lateinit var firebaseAuth: FirebaseAuth


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
        var btnAcpt :Button = binding.btnAcpt
        var btnD :Button = binding.btnDenied
        var PID : TextView = binding.textView26
    }

    override fun onBindViewHolder(holder: HolderBid, position: Int) {
        val model = showReturnList[position]
        val nameArt = model.nameArt
        val priceArt = model.priceArt
        val reason = model.reason
        val desc = model.desc
        val imageArt = model.imageArt
        val PID = model.PID

        holder.PID.text = PID
        holder.nameArt.text = nameArt
        holder.priceArt.text = priceArt
        holder.reason.text = reason
        holder.desc.text = desc
        Glide.with(context).load(imageArt).into(holder.imageArt)

        holder.btnAcpt.setOnClickListener {

            firebaseAuth = FirebaseAuth.getInstance()

            val hashMap = HashMap<String, Any>()

            hashMap["status"] = "Approved"

            val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Return")
                ref.child(PID)
                    .updateChildren(hashMap)

                .addOnSuccessListener {
                    Toast.makeText(context,"Updated", Toast.LENGTH_SHORT).show()

                    binding.btnAcpt.isVisible = false
                    binding.btnDenied.isVisible = false

                }
                .addOnFailureListener {
                    Toast.makeText(context,"", Toast.LENGTH_SHORT).show()
                }

        }

        holder.btnD.setOnClickListener {

            firebaseAuth = FirebaseAuth.getInstance()

            val hashMap = HashMap<String, Any>()

            hashMap["status"] = "Unsuccessful"

            val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Return")
            ref.child(PID)
                .updateChildren(hashMap)

                .addOnSuccessListener {
                    Toast.makeText(context,"Updated", Toast.LENGTH_SHORT).show()
                    binding.btnAcpt.isVisible = false
                    binding.btnDenied.isVisible = false

                }
                .addOnFailureListener {
                    Toast.makeText(context,"", Toast.LENGTH_SHORT).show()
                }
        }

    }

    init {
        showReturnList = ArrayList()
    }

}