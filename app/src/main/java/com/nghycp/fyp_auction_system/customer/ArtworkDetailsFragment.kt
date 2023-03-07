package com.nghycp.fyp_auction_system.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDetailsBinding


class artworkDetailsFragment : Fragment() {

    private lateinit var artworkList: ArrayList<ModelArtwork>
    private var _binding : FragmentArtworkDetailsBinding? = null
    private lateinit var cartAdapter: cartAdapter
    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        _binding = FragmentArtworkDetailsBinding.inflate(inflater,container,false)

        val args = this.arguments

        val name= args?.get("artName")
        val nameTextView = binding.showProduct
        nameTextView.text = name.toString()

        val price= args?.get("artPrice")
        val priceTextView = binding.showPricing
        priceTextView.text = price.toString()

        val desc= args?.get("artDescription")
        val descTextView = binding.showDesc
        descTextView.text = desc.toString()

        val artist= args?.get("artAuthor")
        val artistTextView = binding.showAuthor
        artistTextView.text = artist.toString()

        val image= args?.get("artImage")
        //val imgTextView = binding.imageProduct

        Glide.with(this@artworkDetailsFragment)
            .load(image.toString())
            .placeholder(R.drawable.artwork_placeholder)
            .into(binding.imageProduct)


        binding.buttonAddToCart.setOnClickListener {
            addToCartProduct()
            findNavController().navigate(R.id.action_artworkDetailsFragment_to_addToCartFragment)
        }

        return binding.root

    }
    private fun addToCartProduct() {
        artworkList = ArrayList()
        val ref1 = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artCart")
        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")
        ref1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                artworkList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(ModelArtwork::class.java)

                    artworkList.add(model!!)
                }

                cartAdapter = cartAdapter(requireContext(),artworkList)

                recyclerView.adapter = cartAdapter


            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


    }
