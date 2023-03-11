package com.nghycp.fyp_auction_system.customer

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDetailsBinding
import kotlinx.android.synthetic.main.fragment_artwork_details.*
import kotlinx.android.synthetic.main.fragment_artwork_layout.*


class artworkDetailsFragment : Fragment() {

    private lateinit var artworkList: ArrayList<ModelArtwork>
    private var _binding : FragmentArtworkDetailsBinding? = null

    private lateinit var recyclerView: RecyclerView
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private var imageUri: Uri? = null
    private var ref =
        Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artCart")
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddToCart.setOnClickListener {
            addRecord()
            //findNavController().navigate(R.id.action_artworkDetailsFragment_to_addToCartFragment2)
            //findNavController().navigate(R.id.action_artworkDetailsFragment_to_addToCartFragment2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true)
        _binding = FragmentArtworkDetailsBinding.inflate(inflater,container,false)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val args = this.arguments

        val id = args?.get("id")
        Log.d("abcde",id.toString())

        val artName= args?.get("artName")
        val nameTextView = binding.showProduct
        nameTextView.text = artName.toString()

        val artPrice= args?.get("artPrice")
        val priceTextView = binding.showPricing
        priceTextView.text = artPrice.toString()

        val artDescription= args?.get("artDescription")
        val descTextView = binding.showDesc
        descTextView.text = artDescription.toString()

        val artAuthor= args?.get("artAuthor")
        val artistTextView = binding.showAuthor
        artistTextView.text = artAuthor.toString()

        val image= args?.get("artImage")
        //val imgTextView = binding.imageProduct

        Glide.with(this@artworkDetailsFragment)
            .load(image.toString())
            .placeholder(R.drawable.artwork_placeholder)
            .into(binding.imageProduct)




        return binding.root

    }
    private var artworkName = ""
    private var description = ""
    private var price = ""
    private var author = ""
    private var newId = ""
    private var image =""

    private fun addRecord() {
        artworkName = binding.showAuthor.text.toString().trim()
        description = binding.showDesc.text.toString().trim()
        price = binding.showPricing.text.toString().trim()
        author = binding.showAuthor.text.toString().trim()
        val args = this.arguments
        val image= args?.get("artImage").toString()
        val id= args?.get("id").toString()
      /*  Log.d("dcba",args?.get("artImage").toString())
        Log.d("abcd",id.toString())*/
        Glide.with(this@artworkDetailsFragment).load(image)
        progressDialog.show()



        val hashMap = HashMap<String, Any>()
        hashMap["id"] = id
       // Log.d("abc",id)
        hashMap["artName"] = artworkName
        hashMap["artDescription"] = description
        hashMap["artPrice"] = price
        hashMap["artAuthor"] = author
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["artImage"] = image

        val newId = ref.push().key!!
        ref.child(newId)
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
    }
