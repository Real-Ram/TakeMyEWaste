package com.example.takemyewaste

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.admin.AddEwasteDetails
import com.example.takemyewaste.databinding.ActivityAddDeliveryAgentsBinding
import com.example.takemyewaste.databinding.ActivityAddFaqBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddDeliveryAgents : AppCompatActivity() {

    //view binding
    private  lateinit var binding: ActivityAddDeliveryAgentsBinding

    //firbase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private  lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDeliveryAgentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, begin upload category
        binding.enterBtn.setOnClickListener {
            validateData()
        }
    }

    private var name = ""
    private var mobile = ""


    private fun validateData() {
        //validate data
        //get data
        name = binding.NameEt.text.toString().trim()
        mobile = binding.MobileEt.text.toString().trim()


        //validate data
        if(name.isEmpty()){
            Toast.makeText(this, "Enter Name of the Delivery Agent", Toast.LENGTH_SHORT).show()
        }
        else if(mobile.isEmpty()){
            Toast.makeText(this, "Enter Mobile of the Delivery Agent", Toast.LENGTH_SHORT).show()
        }
        else {
            addDeliveryAgentFirebase()
        }

    }

    private fun addDeliveryAgentFirebase() {
        //show progressdialog
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap = HashMap<String,Any>()
        hashMap["id"] = "$timestamp"
        hashMap["name"] = name
        hashMap["mobile"] = mobile
        hashMap["uid"] = "${firebaseAuth.uid}"

        //add to firebase db: Database Root > Categories > CategoryId > CategoryInfo
        val ref = FirebaseDatabase.getInstance().getReference("DeliveryAgents")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Added Successfully....", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AddDeliveryAgents, DeliveryAgents::class.java))
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}