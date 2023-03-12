package com.nghycp.fyp_auction_system.customer

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
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
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkUpdateBinding
import com.nghycp.fyp_auction_system.databinding.FragmentRecentAddBinding
import java.lang.Exception


class CustomerUpdateArtwork : Fragment() {

    private lateinit var binding: FragmentArtworkUpdateBinding
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var UpdateAdapter: ArtworkUpdateAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var recyclerView: RecyclerView
    private lateinit var progressDialog: ProgressDialog
    private var imageUri: Uri? = null
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
        showProduct()
        binding.buttonUpdate.setOnClickListener{
            validateData()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        artworkList = arrayListOf<ModelArtwork>()

    }
    private fun showProduct() {
        artworkList = ArrayList()

        val args = this.arguments
        val id = args?.getString("id")

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork").child(id.toString())
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {


                val author = snapshot.child("artAuthor").value as String?
                val desc = snapshot.child("artDescription").value as String?
                val name = snapshot.child("artName").value as String?
                val price = snapshot.child("artPrice").value as String?
                val image = "${snapshot.child("artImage").value}"



                binding.editTextProductName.setText(name)
                binding.editTextAuthor.setText(author)
                binding.editTextDescription.setText(desc)
                binding.editTextPrice.setText(price)
                try {
                    Glide.with(this@CustomerUpdateArtwork)
                        .load(image)
                        .placeholder(R.drawable.artwork_placeholder)
                        .into(binding.imageViewartwork)
                } catch (e: Exception) {

                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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