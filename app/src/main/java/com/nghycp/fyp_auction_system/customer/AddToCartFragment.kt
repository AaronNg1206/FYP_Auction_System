package com.nghycp.fyp_auction_system.customer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentAddToCartBinding
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class addToCartFragment : Fragment() {

    private lateinit var binding: FragmentAddToCartBinding
    private lateinit var addToCartList: ArrayList<ModelArtwork>

    private lateinit var cartAdapter: cartAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var recyclerViewAddToCart: RecyclerView
    //private lateinit var listRemove : ArrayList<Int>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseAuth = FirebaseAuth.getInstance()
        recyclerViewAddToCart = view.findViewById(R.id.RecyclerViewArtCart)
        recyclerViewAddToCart.layoutManager = LinearLayoutManager(context)
        recyclerViewAddToCart.setHasFixedSize(true)

        addToCartList = arrayListOf<ModelArtwork>()

        shoppingCart()
        binding.buttonCheckOut.setOnClickListener{
            val checkedItems = cartAdapter.getCheckedItems()
            CheckoutCart(checkedItems)
        }

        binding.buttonRemove.setOnClickListener {
            val checkedItems = cartAdapter.getCheckedItems()
            //ca.removeCheckedItems(requireContext())
            removeCart(checkedItems)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        addToCartList = ArrayList()
        binding = FragmentAddToCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun removeCart(checkedItems :List<ModelArtwork>) {
        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artCart")
        for (itemRemove in checkedItems){
            ref.child(itemRemove.id).removeValue()
                .addOnSuccessListener{
                    Toast.makeText(context,"Remove Successful", Toast.LENGTH_SHORT).show()
                }
                .addOnCanceledListener {  ->

            Toast.makeText(context,"Failed to remove this artwork", Toast.LENGTH_SHORT).show()
        }

        }
        Toast.makeText(context,"Successful Remove ",Toast.LENGTH_SHORT).show()

    }



    private fun CheckoutCart(checkedItems :List<ModelArtwork>) {
        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Checkout")
        ref.removeValue()
        val hashMap = HashMap<String, Any>()

        for (itemCheckOut in checkedItems){
            hashMap["artName"] = itemCheckOut.artName
            hashMap["artImage"] = itemCheckOut.artImage
            hashMap["artPrice"] = itemCheckOut.artPrice
            hashMap["id"]= itemCheckOut.id
            hashMap["uid"] = "${firebaseAuth.uid}"

            ref.child(itemCheckOut.id)
                .setValue(hashMap)
                .addOnSuccessListener{
                    Toast.makeText(context," Proceed to checkout", Toast.LENGTH_SHORT).show()

                }
                .addOnCanceledListener {  ->
                    Toast.makeText(context,"Failed to remove this artwork", Toast.LENGTH_SHORT).show()
                }

        }
        findNavController().navigate(R.id.action_addToCartFragment_to_paymentFragment)
        //Toast.makeText(context,"Successful Remove ",Toast.LENGTH_SHORT).show()

    }
    private fun shoppingCart() {
        addToCartList = ArrayList()
        var total = 0.0

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artCart")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                addToCartList.clear()
                total = 0.0;

                for (ds in snapshot.children){
                    val model = ds.getValue(ModelArtwork::class.java)

                    model?.id = ds.key!!
                    val artPrice = model?.artPrice?.toDoubleOrNull()
                    total += artPrice!!
                    binding.textViewTotal.text = total.toString()
                    addToCartList.add(model!!)
                }

                cartAdapter = cartAdapter(context!!,addToCartList)
                recyclerViewAddToCart.adapter = cartAdapter


            }
            override fun onCancelled(error: DatabaseError) {
                try {

                }catch (e: Exception){
                    Log.d("c",e.toString())
                }

            }

        })
    }



}