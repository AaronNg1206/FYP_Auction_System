package com.nghycp.fyp_auction_system.usermanagement

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.nghycp.fyp_auction_system.R
import com.nghycp.fyp_auction_system.databinding.FragmentUserProfileBinding
import java.lang.Exception


class FragmentUserProfile : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private val binding get() = _binding!!

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = FragmentUserProfileBinding.inflate(inflater,container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(context)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        val user = firebaseAuth.currentUser
        val uid = user!!.uid

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users").child(uid)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value as String?
                val age = snapshot.child("age").value as String?
                val country = snapshot.child("country").value as String?
                val phone = snapshot.child("phone").value as String?
                val email = snapshot.child("email").value as String?
                val profileImage = "${snapshot.child("profileImage").value}"
                //val image = snapshot.child("profileImage").child("url").value as String?

                binding.Name.setText(name)
                binding.Name1.setText(name)
                binding.Age.setText(age)
                binding.Country.setText(country)
                binding.Phone.setText(phone)
                binding.Email.setText(email)

                try {
                    Glide.with(this@FragmentUserProfile)
                        .load(profileImage)
                        .placeholder(R.drawable.user)
                        .into(binding.imageViewPicture)
                } catch (e: Exception) {

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.btnUpdateProfile.setOnClickListener {
            validateData()
        }

        binding.imageViewPicture.setOnClickListener {
            showImageAttchMenu()
        }

        return binding.root
    }

    private fun showImageAttchMenu() {
        val popupMenu = PopupMenu(context, binding.imageViewPicture)
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

                binding.imageViewPicture.setImageURI(imageUri)
            } else {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
   )

//    private fun pickImageCamera() {
//        val values = ContentValues()
//        values.put(MediaStore.Images.Media.TITLE, "Temp_Title")
//        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp_Description")
//
//        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
//
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//        cameraActivityResultLauncher.launch(intent)
//    }
//
//    private val cameraActivityResultLauncher = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult(),
//        ActivityResultCallback<ActivityResult> { result ->
//
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data = result.data
//
//                binding.imageViewPicture.setImageURI(imageUri)
//            } else {
//                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
//            }
//        }
//    )

    private var name = ""
    private var age = ""
    private var country = ""
    private var phone = ""
    private var email = ""

    private fun validateData(){

        name = binding.Name.text.toString().trim()
        age = binding.Age.text.toString().trim()
        country = binding.Country.text.toString().trim()
        phone = binding.Phone.text.toString().trim()
        email = binding.Email.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(context, "Enter name", Toast.LENGTH_SHORT).show()
        } else if (phone.isEmpty()) {
            Toast.makeText(context, "Please enter phone Number", Toast.LENGTH_SHORT).show()
        } else if (age.isEmpty()) {
            Toast.makeText(context, "Please enter Age", Toast.LENGTH_SHORT).show()
        }else if (country.isEmpty()) {
            Toast.makeText(context, "Please enter country", Toast.LENGTH_SHORT).show()
        }else if (email.isEmpty()) {
            Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show()
        }else {
            if (imageUri == null) {
                editUser("")
            } else {
                uploadImage()
            }
        }

    }

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

                editUser(uploadedImageUrl)

            }
            .addOnFailureListener { e->

                progressDialog.dismiss()
                Toast.makeText(context,"Failed to upload image due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }

    private fun editUser(uploadedImageUrl: String) {
        progressDialog.dismiss()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Updating profile...")
        progressDialog.show()

        val hashMap = HashMap<String, Any>()
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["name"] = name
        hashMap["age"] = age
        hashMap["country"] = country
        hashMap["phone"] = phone
        hashMap["email"] = email
        if(imageUri != null){
            hashMap["profileImage"] = uploadedImageUrl
        }

        val ref = Firebase.database("https://artwork-e6a68-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)

            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Profile updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(context,"Failed to update profile", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        // log out action not visible in edit profile page
        menu.findItem(R.id.action_logout).isVisible = false
    }

}