package com.nghycp.fyp_auction_system.customer

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nghycp.fyp_auction_system.databinding.FragmentCustomerAddArtworkBinding
import kotlinx.android.synthetic.main.fragment_customer__add__artwork.*


class customer_Add_Artwork : Fragment() {

    private var _binding: FragmentCustomerAddArtworkBinding? = null

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentCustomerAddArtworkBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please Wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.imageAdd.setOnClickListener {
            showImageAttchMenu()
        }

        binding.buttonAddSelling.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private var artworkName = ""
    private var description = ""
    private var price = ""
    private var author = ""

    private fun validateData() {
            artworkName = binding.editTextProductName.text.toString().trim()
            description = binding.editTextDescription.text.toString().trim()
            price = binding.editTextPrice.text.toString().trim()
            author = binding.editTextAuthor.text.toString().trim()

            if(artworkName.isEmpty()){
                binding.editTextProductName.error = "Enter Your Artwork Product Name"
            }else if (description.isEmpty()){
                binding.editTextDescription.error = "Enter Your Description"
            }else if (price.isEmpty()){
                binding.editTextPrice.error = "Enter Your Artwork price"
            }else if (author.isEmpty()){
                binding.editTextAuthor.error = "Enter Author Name"
            }else {
                if (imageUri == null) {
                    addRecord("")
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
                selectImages()

            }
            true
        }
    }

    private fun selectImages() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        galleryActivityResultLauncher.launch(intent)
    }
    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { result ->

            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data?.clipData != null) {
                    // Multiple images selected
                    for (i in 0 until data.clipData!!.itemCount) {
                        imageUri = data.clipData!!.getItemAt(i).uri
                        // Do something with each selected image URI
                        //binding.imageAdd.setImageURI(imageUri)
                    }
                }
              /*  else {
                    // Single image selected
                    imageUri = data?.data
                    // Do something with selected image URI
                    binding.imageAdd.setImageURI(imageUri)
                }*/

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

                addRecord(uploadedImageUrl)

            }
            .addOnFailureListener { e->

                progressDialog.dismiss()
                Toast.makeText(context,"Failed to upload image due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }
    private fun addRecord(uploadedImageUrl: String) {
        progressDialog.show()

        val timestamp = System.currentTimeMillis()

        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["ArtName"] = artworkName
        hashMap["artDescription"] = description
        hashMap["artPrice"] = price
        hashMap["artAuthor"] = author
        hashMap["uid"] = "${firebaseAuth.uid}"
        if(imageUri != null){
            hashMap["artImage"] = uploadedImageUrl
        }
        val ref =
            Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("artwork")
        ref.child(artworkName)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Add Successful", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(this, customer_Show_Artwork::class.java))

            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to add this artwork", Toast.LENGTH_SHORT).show()
            }
    }


}