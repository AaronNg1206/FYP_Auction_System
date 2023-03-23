package com.nghycp.fyp_auction_system.Payment

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
import com.nghycp.fyp_auction_system.customer.ArtworkAdapter
import com.nghycp.fyp_auction_system.customer.ModelArtwork
import com.nghycp.fyp_auction_system.databinding.FragmentCreditCardBinding
import kotlinx.android.synthetic.main.fragment_add_to_cart_layout.*
import kotlinx.android.synthetic.main.fragment_credit_card.*
import kotlinx.android.synthetic.main.fragment_credit_card_layout.*
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_return_refund.*


class creditCardFragment : Fragment() {

    private lateinit var binding: FragmentCreditCardBinding
    private lateinit var CreditCardList: ArrayList<ModelCreditCard>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var creditCardAdapter: CreditCardAdapter
    private lateinit var recyclerViewCreditCard: RecyclerView
    private lateinit var progressDialog: ProgressDialog



    private val ref =
        Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("creditCard")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        binding = FragmentCreditCardBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)


        recyclerViewCreditCard = view.findViewById(R.id.recyclerViewCreditCard)
        recyclerViewCreditCard.layoutManager = LinearLayoutManager(context)
        recyclerViewCreditCard.setHasFixedSize(true)
        displayCreditCard()
        binding.addCardBtnCardAddBottomSheet.setOnClickListener{
            validateData()
        }
        binding.buttonProceedPayment.setOnClickListener{
            val checkedItems = creditCardAdapter.getCheckedItems()
            bringBackCardDetail(checkedItems)
        }
        CreditCardList = arrayListOf<ModelCreditCard>()

        binding = FragmentCreditCardBinding.bind(view)
    }
    private var cardHolderName = ""
    private var cardNumber = ""
    private var expDate = ""
    private var cvv = ""

    private fun validateData() {
        CreditCardList = ArrayList()
        cardHolderName = binding.nameEtCardAddBottomSheet.text.toString().trim()
        cardNumber = binding.cardNumberCardAddBottomSheet.text.toString().trim()
        expDate = binding.expCardAddBottomSheet.text.toString().trim()
        cvv = binding.cvvCardAddBottomSheet.text.toString().trim()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CreditCardList.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(ModelCreditCard::class.java)
                    CreditCardList.add(model!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        if(cardHolderName.isEmpty()){
            binding.nameEtCardAddBottomSheet.error = "Provide Card Holder Name"
        }
        else if (cardNumber.isEmpty()){
            binding.cardNumberCardAddBottomSheet.error = "Enter Your Description"
        }else if (expDate.isEmpty()){
            binding.expCardAddBottomSheet.error = "Enter Your Artwork price"
        }else if (cvv.isEmpty()){
            binding.cvvCardAddBottomSheet.error = "Enter Author Name"
        }else {
            addCreditCard()
        }
    }
    private fun addCreditCard() {
        progressDialog.show()
        val hashMap = HashMap<String, Any>()
        val newID = ref.push().key!!
        hashMap["cardHolderName"] = cardHolderName
        hashMap["cardNumber"] = cardNumber
        hashMap["expDate"] = expDate
        hashMap["cvv"] = cvv
        hashMap["id"]= newID
        hashMap["uid"] = "${firebaseAuth.uid}"
        ref.child("${firebaseAuth.uid}")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Add Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to add this artwork", Toast.LENGTH_SHORT).show()
            }
    }
    private fun displayCreditCard(){
        CreditCardList = ArrayList()



        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                CreditCardList.clear()
                for (ds in snapshot.children){
                    val model = ds.getValue(ModelCreditCard::class.java)

                    CreditCardList.add(model!!)
                }
                val context = context
                if (context != null) {
                    creditCardAdapter = CreditCardAdapter(context!!, CreditCardList)

                    recyclerViewCreditCard.adapter = creditCardAdapter
                }

            }
            override fun onCancelled(error: DatabaseError) {
                try{

                }catch(e: Exception) {
                    Log.d("ccc",e.toString())
                }
            }

        })
    }
    private fun bringBackCardDetail(checkedItems :List<ModelCreditCard>) {
        for (itemCheckOut in checkedItems) {
            val args = Bundle()
            args.putString("cardHolderName", itemCheckOut.cardHolderName)
            args.putString("cardNumber", itemCheckOut.cardNumber)

            val navController = view?.let { Navigation.findNavController(it) }
            navController?.navigate(R.id.action_creditCardFragment_to_paymentFragment, args)
        }
    }
}