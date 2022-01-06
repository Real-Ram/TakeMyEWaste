package com.example.takemyewaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.takemyewaste.databinding.ActivityLoginBinding
import com.example.takemyewaste.databinding.ActivityProfileDataBinding
import com.example.takemyewaste.user.UpdateDataActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class ProfileData : AppCompatActivity() {

    private lateinit var binding: ActivityProfileDataBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        loadProfileData()

        binding.updateBtn.setOnClickListener {
            startActivity(Intent(this, UpdateDataActivity::class.java))
        }
    }

    private fun loadProfileData() {

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseAuth.uid!!)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    //get data from firebase
                    val email = "${snapshot.child("email").value}"
                    val name = "${snapshot.child("name").value}"
                    val mobile = "${snapshot.child("mobile").value}"
                    val profileImage = "${snapshot.child("profileImage").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val uid = "${snapshot.child("uid").value}"
                    val userType = "${snapshot.child("userType").value}"

                    //set data in app from firebase
                    binding.nameEt.text = name
                    binding.mobileEt.text = mobile
                    binding.emailEt.text = email
                    binding.userEt.text = userType

                    try {

                        Glide.with(this@ProfileData)
                            .load(profileImage)
                            .placeholder(R.drawable.ic_person_gray)
                            .into(binding.profileIv)

                    } catch (e: Exception) {

                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
}