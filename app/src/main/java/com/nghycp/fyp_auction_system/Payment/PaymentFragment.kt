package com.nghycp.fyp_auction_system.Payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class paymentFragment : Fragment() {


    private lateinit var binding: FragmentPaymentBinding
    private lateinit var paymentList: ArrayList<ModelArtwork>
    private lateinit var paymentAdapter: paymentAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var RecyclerViewPayment: RecyclerView
    val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("Checkout")
    val ref1 = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
        .getReference("paid")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        shoppingCart()
        binding = FragmentPaymentBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        RecyclerViewPayment = view.findViewById(R.id.RecyclerViewPayment)
        RecyclerViewPayment.layoutManager = LinearLayoutManager(context)
        RecyclerViewPayment.setHasFixedSize(true)
        binding.buttonCreditCard.setOnClickListener{
            findNavController().navigate(R.id.action_paymentFragment_to_creditCardFragment)
        }
        binding.buttonPay.setOnClickListener{
            PaiedProcess()
        }
        paymentList = arrayListOf<ModelArtwork>()

        binding = FragmentPaymentBinding.bind(requireView())

    }
    private fun shoppingCart() {
        paymentList = ArrayList()
        var total = 0.0
        var subTotal = 0.0
        var shippingFee = 0.0

        ref.addValueEventListener(object : ValueEventListener {
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

                paymentAdapter = paymentAdapter(context!!,paymentList)
                RecyclerViewPayment.adapter = paymentAdapter


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
        /*     paymentList = ArrayList()
             ref.addValueEventListener(object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {
                     paymentList.clear()
                     for (ds in snapshot.children){
                         val model = ds.getValue(ModelArtwork::class.java)
                         paymentList.add(model!!)
                     }
                 }
                 override fun onCancelled(error: DatabaseError) {
                     try {
                     }catch (e: Exception){
                         Log.d("c",e.toString())
                     }
                 }
             })

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
             findNavController().navigate(R.id.action_addToCartFragment_to_paymentFragment)*/
    }


}