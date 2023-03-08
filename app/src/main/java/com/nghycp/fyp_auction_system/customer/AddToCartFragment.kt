package com.nghycp.fyp_auction_system.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAddToCartBinding
import kotlinx.android.synthetic.main.fragment_add_to_cart.*


class addToCartFragment : Fragment() {

    private lateinit var binding: FragmentAddToCartBinding
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var cartAdapter: cartAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerViewAddToCart: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentAddToCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        recyclerViewAddToCart = view.findViewById(R.id.RecyclerViewAddToCart)
        recyclerViewAddToCart.layoutManager = LinearLayoutManager(context)
        recyclerViewAddToCart.setHasFixedSize(true)

        artworkList = arrayListOf<ModelArtwork>()

        shoppingCart()

    }
    private fun shoppingCart() {
        artworkList = ArrayList()
        var total = 0.0
        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artCart")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                artworkList.clear()

                for (ds in snapshot.children){
                    val model = ds.getValue(ModelArtwork::class.java)
                    val artPrice = model?.artPrice?.toDoubleOrNull()
                    //artPrice = ds.getValue(Double::class.java).toString()

                    total += artPrice!!
                    /* val artworkPrice = ds.getValue(Double::class.java)
                      total += artworkPrice ?: 0.0
  */
                    artworkList.add(model!!)
                }

                    binding.textViewTotal.text = total.toString()
                //totalPriceTextView.text = "Total: $${String.format("%.2f", total)}"
                cartAdapter = cartAdapter(requireContext(),artworkList)

                recyclerViewAddToCart.adapter = cartAdapter


            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }



}