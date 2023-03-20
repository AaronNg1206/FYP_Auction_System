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
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkInsertBinding

import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ArtworkInsertFragment : Fragment() {

    private var _binding: FragmentArtworkInsertBinding? = null
    private lateinit var artworkList: ArrayList<ModelArtwork>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private val ref =
        Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtworkInsertBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
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
        val view = binding.root
            return view
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private var artworkName = ""
    private var description = ""
    private var price = ""
    private var author = ""



    private fun validateData() {
        artworkList = ArrayList()
        artworkName = binding.editTextProductName.text.toString().trim()
        description = binding.editTextDescription1.text.toString().trim()
        price = binding.editTextPrice.text.toString().trim()
        author = binding.editTextAuthor.text.toString().trim()




        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                artworkList.clear()
                for (ds in snapshot.children) {
                    val model = ds.getValue(ModelArtwork::class.java)
                    artworkList.add(model!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        if(artworkName.isEmpty()){
            binding.editTextProductName.error = "Enter Your Artwork Product Name"
        }
        else if (description.isEmpty()){
            binding.editTextDescription1.error = "Enter Your Description"
        }else if (price.isEmpty()){
            binding.editTextPrice.error = "Enter Your Artwork price"
        }else if (author.isEmpty()){
            binding.editTextAuthor.error = "Enter Author Name"
        }else {
            if (imageUri == null) {
                addRecord("")
            }
            else {
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

        val hashMap = HashMap<String, Any>()
        val newID = ref.push().key!!
        hashMap["artName"] = artworkName
        hashMap["artDescription"] = description
        hashMap["artPrice"] = price
        hashMap["artAuthor"] = author
        hashMap["id"]= newID
        hashMap["uid"] = "${firebaseAuth.uid}"
        if(imageUri != null){
            hashMap["artImage"] = uploadedImageUrl
        }
        ref.child(newID)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Add Successful", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to add this artwork", Toast.LENGTH_SHORT).show()
            }

        findNavController().navigate(R.id.action_artworkInsertFragment_to_adminHomePage)
    }


}