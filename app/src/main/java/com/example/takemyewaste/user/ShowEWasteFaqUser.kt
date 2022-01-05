package com.example.takemyewaste.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.takemyewaste.ModelFaqs
import com.example.takemyewaste.databinding.ActivityShowEwasteFaqUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowEWasteFaqUser : AppCompatActivity() {

    //View binding
    private lateinit var binding: ActivityShowEwasteFaqUserBinding

    //Firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold categories
    private lateinit var faqArrayList: ArrayList<ModelFaqs>

    //adapter
    private lateinit var adapterFaqsUser: AdapterFaqsUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowEwasteFaqUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        loadCategories()

    }

    private fun loadCategories() {
        //init arraylisdt
        faqArrayList = ArrayList()

        //get all categories from firebase database....Firebase DB >Categories
        val ref = FirebaseDatabase.getInstance().getReference("EwasteFaq")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                faqArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelFaqs::class.java)

                    //add to arraylist
                    faqArrayList.add(model!!)
                }
                //setup adapter
                adapterFaqsUser = AdapterFaqsUser(this@ShowEWasteFaqUser, faqArrayList)

                //set adapter to recyclerview
                binding.faqsRv1.adapter = adapterFaqsUser
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}