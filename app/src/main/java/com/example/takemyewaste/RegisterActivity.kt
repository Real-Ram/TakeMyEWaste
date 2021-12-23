package com.example.takemyewaste

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.takemyewaste.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivityRegisterBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progree dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //sign in button
        binding.signinBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //register button
        binding.registerBtn.setOnClickListener {
            /*Steps
            * 1 input data
            * 2 Validate data
            * 3 Create Account - Firebase Auth
            * 4 Save User Info - Firebase Database
            * */
            validateData()
        }
    }

    private var name = ""
    private var mobile = ""
    private var email = ""
    private var password = ""

    private fun validateData() {
        //input data
        name = binding.nameEt.text.toString().trim()
        mobile = binding.mobileEt.text.toString().trim()
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()
        val cPassword = binding.cpasswordEt.text.toString().trim()

        //validate data
        if (name.isEmpty()) {
            Toast.makeText(this, "Enter Your Name....", Toast.LENGTH_SHORT).show()
        }
        else if (!Pattern.compile("[6-9][0-9]{9}").matcher(mobile).matches()) {
            Toast.makeText(this, "Invalid Mobile Pattern....", Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email Pattern....", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Password....", Toast.LENGTH_SHORT).show()
        }
        else if (cPassword.isEmpty()) {
            Toast.makeText(this, "Enter Confirm Password....", Toast.LENGTH_SHORT).show()
        }
        else if (password != cPassword) {
            Toast.makeText(this, "Password does not match....", Toast.LENGTH_SHORT).show()
        }
        else {
            createUserAccount()
        }
    }

    private fun createUserAccount() {
        //Account Create Firebase Auth

        //Progress Dialog
        progressDialog.setMessage("Creating Account....")
        progressDialog.show()

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            //Success
            .addOnSuccessListener {
                updateUserInfo()
            }
            //failure
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed creating account due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        //Save User data - Firebase Database
        progressDialog.setMessage("Saving User Info....")

        //timestamp
        val timestamp = System.currentTimeMillis()

        //get user uid, since user is already register
        val uid = firebaseAuth.uid

        //setup data in db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["mobile"] = mobile
        hashMap["userType"] = "user"
        hashMap["timestamp"] = timestamp

        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                //On Success goes to Dashboard
                progressDialog.dismiss()
                Toast.makeText(this, "Account Created....", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, DashboardUserActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}