package com.example.takemyewaste.admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.takemyewaste.databinding.ActivityAddFaqBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddFaq : AppCompatActivity() {

    //view binding
    private  lateinit var binding: ActivityAddFaqBinding

    //firbase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private  lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFaqBinding.inflate(layoutInflater)
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

    private var question = ""
    private var answer = ""


    private fun validateData() {
        //validate data
        //get data
        question = binding.QueEt.text.toString().trim()
        answer = binding.AnsEt.text.toString().trim()


        //validate data
        if(question.isEmpty()){
            Toast.makeText(this, "Enter Question", Toast.LENGTH_SHORT).show()
        }
        else if(answer.isEmpty()){
            Toast.makeText(this, "Enter Answer", Toast.LENGTH_SHORT).show()
        }
        else {
            addFaqFirebase()
        }

    }

    private fun addFaqFirebase() {
        //show progressdialog
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap = HashMap<String,Any>()
        hashMap["id"] = "$timestamp"
        hashMap["question"] = question
        hashMap["answer"] = answer
        hashMap["uid"] = "${firebaseAuth.uid}"

        //add to firebase db: Database Root > Categories > CategoryId > CategoryInfo
        val ref = FirebaseDatabase.getInstance().getReference("EwasteFaq")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Added Successfully....", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@AddFaq, AddEwasteDetails::class.java))
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}