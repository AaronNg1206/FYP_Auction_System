package com.nghycp.fyp_auction_system.Payment

import android.app.ProgressDialog
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
import com.nghycp.fyp_auction_system.customer.ModelArtwork

import com.nghycp.fyp_auction_system.databinding.FragmentPaymentBinding
import kotlinx.android.synthetic.main.fragment_artwork_layout.*

class paymentFragment : Fragment() {


    private lateinit var binding: FragmentPaymentBinding
    private lateinit var paymentList: ArrayList<ModelArtwork>
    private lateinit var paidList: ArrayList<ModelArtwork>
    private lateinit var paymentAdapter: paymentAdapter
    //private lateinit var paidAdapter: paidAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var RecyclerViewPayment: RecyclerView
    private lateinit var progressDialog: ProgressDialog


    val database = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val artCartRef = database.getReference("artCart")
    val checkoutRef = database.getReference("Checkout")
    val paidRef = database.getReference("paid")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        shoppingCart()



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        RecyclerViewPayment = view.findViewById(R.id.RecyclerViewPayment)
        RecyclerViewPayment.layoutManager = LinearLayoutManager(context)
        RecyclerViewPayment.setHasFixedSize(true)

        val args = this.arguments
        val CardHolderName= args?.get("cardHolderName")
        val cardHolder = binding.textViewCardHolderName
        cardHolder.text = CardHolderName.toString()

        binding.buttonCreditCard.setOnClickListener{
            findNavController().navigate(R.id.action_paymentFragment_to_creditCardFragment)
        }
        binding.buttonPay.setOnClickListener{
            if (CardHolderName != null) {
                PaiedProcess()
            }else{
                Toast.makeText(context,"Select your payment method", Toast.LENGTH_SHORT).show()
            }

        }
        paymentList = arrayListOf<ModelArtwork>()
        paidList = arrayListOf<ModelArtwork>()
        binding = FragmentPaymentBinding.bind(requireView())
    }

    private fun shoppingCart() {
        paymentList = ArrayList()
        var total = 0.0
        var subTotal = 0.0
        var shippingFee = 0.0
        checkoutRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                paymentList.clear()
                total = 0.0
                subTotal = 0.0
                for (ds in snapshot.children){
                    val model = ds.getValue(ModelArtwork::class.java)
                        model?.id = ds.key!!
                      val artPrice = model?.artPrice?.toDoubleOrNull()
                       total += artPrice!!
                       binding.textViewTotalPrice.text = total.toString()
                       shippingFee = when (total) {
                           in 1.0..4999.0 -> 20.0
                           in 5000.0..10000.0 -> 50.0
                           in 10000.1..90000.0 -> 100.0
                           else -> throw IllegalArgumentException("Invalid artwork price")
                       }
                       subTotal = shippingFee + total
                       binding.textViewShipingFee.text = shippingFee.toString()
                       binding.textViewSubtotal.text = subTotal.toString()
                    paymentList.add(model!!)
                }
                val context = context
                if (context != null) {
                    paymentAdapter = paymentAdapter(context!!, paymentList)
                    RecyclerViewPayment.adapter = paymentAdapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                try {
                }catch (e: Exception){
                    Log.d("c",e.toString())
                }
            }
        })
    }

    private fun PaiedProcess() {
        val currentTime = System.currentTimeMillis()
        checkoutRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    val name = data.child("artName").getValue(String::class.java)
                    val image = data.child("artImage").getValue(String::class.java)
                    val price = data.child("artPrice").getValue(String::class.java)
                    val uid = data.child("uid").getValue(String::class.java)

                    //auto date generate


                    // Create a HashMap of the data
                    val artworkData = HashMap<String, Any>()
                    artworkData["artName"] = name!!
                    artworkData["artImage"] = image!!
                    artworkData["artPrice"] = price!!
                    artworkData["uid"] = uid!!
                    artworkData["timestamp"] = "${currentTime}"
                    artworkData["status"] = "Completed"

                    // Add the data to the paid database
                    val newID = paidRef.push().key!!
                    artworkData["PID"] = newID
                    paidRef.child(newID).setValue(artworkData)

                    // Remove the data from the checkout database

                    checkoutRef.child(data.key!!).removeValue()
                    artCartRef.child(data.key!!).removeValue()

                    Toast.makeText(context,"Payment Was successful", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.fragmentUserHomePage)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle any errors
            }
        })

    }
    }