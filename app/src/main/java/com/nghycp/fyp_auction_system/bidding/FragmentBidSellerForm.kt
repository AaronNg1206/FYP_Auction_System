package com.nghycp.fyp_auction_system.bidding

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentBidSellerFormBinding
import java.lang.Exception

class FragmentBidSellerForm : Fragment() {

    private var _binding: FragmentBidSellerFormBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentBidSellerFormBinding.inflate(inflater,container,false)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnAdd.setOnClickListener {

            validateData()

        }

        binding.imageAdd.setOnClickListener {
            showImageAttchMenu()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private var name = ""
    private var desc = ""
    private var price = ""
    private var artist = ""
    private var min = ""
    private var img = ""

    private fun validateData() {

        name = binding.addName.text.toString().trim()
        desc = binding.addDesc.text.toString().trim()
        price = binding.addPrice.text.toString().trim()
        artist = binding.addArtist.text.toString().trim()
        min = binding.spinnerMinBid.selectedItem.toString().trim()
        //img = binding.imageAdd.toString().trim()

//        try {
//            Glide.with(this@FragmentBidSellerForm)
//                .load(img)
//                //.placeholder(R.drawable.imageAdd)
//                .into(binding.imageAdd)
//        } catch (e: Exception) {
//
//        }

        if(name.isEmpty()){
            binding.addName.error = "Enter Your Name"
        }else if(desc.isEmpty()){
            binding.addDesc.error = "Enter Description"
        }else if(price.isEmpty()){
            binding.addPrice.error = "Enter Price"
        }else if(artist.isEmpty()){
            binding.addArtist.error = "Enter Artist"
        }else if(min.isEmpty()){
            Toast.makeText(context, "Choose the min bid price", Toast.LENGTH_SHORT).show()
        }else {
            if (imageUri == null) {
                createProduct("")
            } else {
                uploadImage()
            }
        }

    }

    private fun showImageAttchMenu() {
        val popupMenu = PopupMenu(context, binding.imageAdd)
        popupMenu.menu.add(Menu.NONE, 0, 0, "Gallery")
        popupMenu.show()

        popupMenu.setOnMenuItemClickListener { item ->
            val id = item.itemId
            if (id == 0) {
                pickImageGallery()

            }
            true
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data!!.data

                binding.imageAdd.setImageURI(imageUri)
            } else {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun uploadImage() {
        val user = firebaseAuth.currentUser
        val uid = user!!.uid
        progressDialog.setMessage("Uploading Profile image")
        progressDialog.show()

        val filePathAndName = "ProfileImages/"+ firebaseAuth.uid

        val reference = Firebase.storage("gs://artwork-e6a68.appspot.com").getReference(filePathAndName)
        reference.putFile(imageUri!!)
            .addOnSuccessListener { takeSnapshot->
                val uriTask: Task<Uri> = takeSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val uploadedImageUrl = "${uriTask.result}"
//                val imageMap = mapOf("url" to uriTask.toString())
//
//                val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
//                    .getReference("Users").child(uid)
//                    ref.child("profileImage").setValue(imageMap)

                createProduct(uploadedImageUrl)

            }
            .addOnFailureListener { e->

                progressDialog.dismiss()
                Toast.makeText(context,"Failed to upload image due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }

    private fun createProduct(uploadedImageUrl: String) {

        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["name"] = name
        hashMap["desc"] = desc
        hashMap["price"] = price
        hashMap["artist"] = artist
        hashMap["min"] = min
        //hashMap["Artwork Image"] = salary
        hashMap["uid"] = "${firebaseAuth.uid}"
        if(imageUri != null){
            hashMap["profileImage"] = uploadedImageUrl
        }

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Product")
        ref.child(name)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Add Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_fragmentRegister_to_fragmentLogin)
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to add this artwork", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu, inflater)

        menu.findItem(R.id.action_logout).isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}