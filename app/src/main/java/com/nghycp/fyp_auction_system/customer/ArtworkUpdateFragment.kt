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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDetailsBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkUpdateBinding
import com.nghycp.fyp_auction_system.databinding.FragmentRecentAddBinding
import kotlinx.android.synthetic.main.fragment_artwork_update.*
import kotlinx.android.synthetic.main.fragment_artwork_update.view.*
import java.lang.Exception


class CustomerUpdateArtwork : Fragment() {

    private lateinit var binding: FragmentArtworkUpdateBinding
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var UpdateAdapter: ArtworkUpdateAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var recyclerView: RecyclerView
    private lateinit var progressDialog: ProgressDialog
    private var imageUri: Uri? = null
    private var ref =
        Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArtworkUpdateBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val args = this.arguments

        var id = args?.get("id").toString()

        id = binding.editTextProductName.toString()

        val artName= args?.get("artName")
       // val nameTextView = binding.editTextProductName
        //editText_productName.text = artName.text()

        val artPrice= args?.get("artPrice")
       // val priceTextView = binding.editTextPrice
       // priceTextView.text = artPrice.toString()

        val artDescription= args?.get("artDescription")
        //val descTextView = binding.editTextDescription
       // descTextView.text = artDescription.toString()

        val artAuthor= args?.get("artAuthor")
        //val artistTextView = binding.editTextAuthor
      //  artistTextView.text = artAuthor.toString()

        val image= args?.get("artImage")
        //val imgTextView = binding.imageProduct

        Glide.with(this@CustomerUpdateArtwork)
            .load(image.toString())
            .placeholder(R.drawable.artwork_placeholder)
            .into(binding.imageViewartwork)

        binding.buttonUpdate.setOnClickListener{
            validateData()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artworkList = arrayListOf<ModelArtwork>()

    }


    private var name = ""
    private var desc = ""
    private var author = ""
    private var price = ""


    private fun validateData(){

        name = binding.editTextProductName.text.toString().trim()
        desc = binding.editTextDescription.text.toString().trim()
        author = binding.editTextAuthor.text.toString().trim()
        price = binding.editTextPrice.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(context, "Enter artwork name", Toast.LENGTH_SHORT).show()
        } else if (desc.isEmpty()) {
            Toast.makeText(context, "Please enter description", Toast.LENGTH_SHORT).show()
        } else if (author.isEmpty()) {
            Toast.makeText(context, "Please enter author Name", Toast.LENGTH_SHORT).show()
        }else if (price.isEmpty()) {
            Toast.makeText(context, "Please enter artwork Price", Toast.LENGTH_SHORT).show()
        }else {
            if (imageUri == null) {
                UpdateProduct("")
            } else {
                //uploadImage()
            }
        }


    }

    private fun UpdateProduct(uploadedImageUrl: String) {
        progressDialog.dismiss()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Updating profile...")
        progressDialog.show()

        val hashMap = HashMap<String, Any>()
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["artName"] = name
        hashMap["artDescription"] = desc
        hashMap["artAuthor"] = author
        hashMap["artPrice"] = price
        if(imageUri != null){
            hashMap["artImage"] = uploadedImageUrl
        }

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")
        ref.child(id.toString())
            .updateChildren(hashMap)

            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Artwork updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to update artwork", Toast.LENGTH_SHORT).show()
            }

    }
}