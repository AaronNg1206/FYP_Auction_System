package com.nghycp.fyp_auction_system.customer

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDetailsBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkDisplayBinding
import com.nghycp.fyp_auction_system.databinding.FragmentArtworkUpdateBinding
import com.nghycp.fyp_auction_system.databinding.FragmentRecentAddBinding
import kotlinx.android.synthetic.main.fragment_artwork_update.*
import kotlinx.android.synthetic.main.fragment_artwork_update.view.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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



        val artName= args?.get("artName")
        val nameTextView = binding.textViewName
        nameTextView.text = artName.toString()

        val artPrice= args?.get("artPrice")
        val priceTextView = binding.textViewPrice
        priceTextView.text = artPrice.toString()

        val artDescription= args?.get("artDescription")
        val descTextView = binding.textViewDesc
        descTextView.text = artDescription.toString()

        val artAuthor= args?.get("artAuthor")
        val artistTextView = binding.textViewAuthor
        artistTextView.text = artAuthor.toString()

        val image= args?.get("artImage")
        //val imgTextView = binding.imageProduct

        Glide.with(this@CustomerUpdateArtwork)
            .load(image.toString())
            .placeholder(R.drawable.artwork_placeholder)
            .into(binding.imageViewartwork)

        binding.buttonUpdate.setOnClickListener{

            UpdateProduct("")
        }
        binding.buttonOpenGallery.setOnClickListener {
            showImageAttchMenu()
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

    private fun showImageAttchMenu() {
        val popupMenu = PopupMenu(context, binding.buttonOpenGallery)
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

                binding.imageViewartwork.setImageURI(imageUri)
                uploadImage()
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
                UpdateProduct(uploadedImageUrl)
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to upload image due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }



    private fun UpdateProduct(uploadedImageUrl: String) {
        progressDialog.dismiss()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Updating profile...")
        progressDialog.show()

        name = binding.editTextProductName.text.toString()
        desc = binding.editTextDescription.text.toString().trim()
        author = binding.editTextAuthor.text.toString().trim()
        price = binding.editTextPrice.text.toString().trim()

        val args = this.arguments

        if (name.isEmpty()) {
            val getName= args?.get("artName")
            name = getName.toString()
        }
        if (desc.isEmpty()) {
            val getDesc = args?.get("artDescription")
            desc = getDesc.toString()
        }
        if (author.isEmpty()) {
                var getAuthor = args?.get("artAuthor")
                author = getAuthor.toString()
            }
        if (price.isEmpty()) {
            var getPrice = args?.get("artPrice")
            price = getPrice.toString()
        }

        val hashMap = HashMap<String, Any>()

        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["artName"] = name
        hashMap["artDescription"] = desc
        hashMap["artAuthor"] = author
        hashMap["artPrice"] = price

        if (uploadedImageUrl == ""){
            val image= args?.get("artImage")
            Log.d("updateOriImage", image.toString())
            hashMap["artImage"] = image.toString()
        }else{
            hashMap["artImage"] = uploadedImageUrl
        }

        var id = args?.get("id").toString()

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("artwork")
        ref.child(id)
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