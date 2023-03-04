package com.nghycp.fyp_auction_system

import android.widget.ImageView
import android.widget.TextView
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.bidding.BidShowAdapter
import com.nghycp.fyp_auction_system.bidding.ModelBid
import com.nghycp.fyp_auction_system.customer.ArtworkAdapter
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkLayoutBinding
import com.nghycp.fyp_auction_system.databinding.FragmentCustomerAddArtworkBinding


class FragmentArtworkDisplay : Fragment() {

    private lateinit var binding:FragmentArtworkDisplayBinding
    private lateinit var artworkAdapter: ArtworkAdapter
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var artworkRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentArtworkDisplayBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        showProduct()
        return binding.root
    }

   /* override fun onViewCreated(view: View, savedIns tanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView, adapter, and list
        adapter = ArtworkAdapter()
        artworkList = mutableListOf()
        binding.RecyclerViewArtworkShow.adapter = adapter

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance()
        artworkRef = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("artwork")

        // Read artwork data from Firebase
        artworkRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                artworkList.clear()
                for (artworkSnapshot in snapshot.children) {
                    val artwork = artworkSnapshot.getValue(ModelArtwork::class.java)
                    if (artwork != null) {
                        artworkList.add(artwork)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read artwork data.", error.toException())
            }
        })
    }

    // Adapter for the RecyclerView
    inner class ArtworkAdapter : RecyclerView.Adapter<ArtworkViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworkViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = FragmentArtworkDisplayBinding.inflate(inflater, parent, false)
            return ArtworkViewHolder(binding)
        }
        override fun onBindViewHolder(holder: ArtworkViewHolder, position: Int) {
            val artwork = artworkList[position]
            holder.bind(artwork)
        }
        override fun getItemCount(): Int {
            return artworkList.size
        }
    }

    // ViewHolder for each item in the RecyclerView
    inner class ArtworkViewHolder(private val binding: FragmentArtworkDisplayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artwork: ModelArtwork) {
            Glide.with(binding..context).load(artwork.imageURL).into(binding.artworkImage)
            binding.artworkName.text = artwork.artworkName
            binding.artworkPrice.text = "$${artwork.price}"
        }
    }
*/
   private fun showProduct() {
       artworkList = ArrayList()

       val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
           .getReference("artwork")
       ref.addValueEventListener(object : ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               artworkList.clear()
               for (ds in snapshot.children){
                   val model = ds.getValue(ModelArtwork::class.java)

                   artworkList.add(model!!)
               }

               artworkAdapter = ArtworkAdapter(context!!,artworkList)

               binding.RecyclerViewArtworkShow.adapter = artworkAdapter
           }
           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }
       })
   }
    }

