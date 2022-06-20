package com.example.medicalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import com.example.medicalapp.databinding.ActivityPatientRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Patient_Registration_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientRegistrationBinding

    private lateinit var auth: FirebaseAuth

    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_registration)
        auth = Firebase.auth

        binding.ptMedico = resources.getString(R.string.medico)
        binding.ptName = resources.getString(R.string.enter_full_name)
        binding.ptEmailId = resources.getString(R.string.enter_email_id)
        binding.ptPassword = resources.getString(R.string.create_password)
        binding.ptPasswordText = resources.getString(R.string.password_text)
        binding.ptPhone= resources.getString(R.string.enter_phone_no)
        binding.ptDOB = resources.getString(R.string.dob)
        binding.ptSaveAndContinue = resources.getString(R.string.submit)

        binding.IdPtSaveAndContinue.setOnClickListener {
            val intSelectButton: Int = binding.genderRadio.checkedRadioButtonId
            radioButton =findViewById(intSelectButton)
            registerUser()
        }
    }


    private fun registerUser() {
        if(binding.IdPtFullName.text.toString().trim().isEmpty()){
            binding.IdPtFullName.setError("Please enter Full Name")
            binding.IdPtFullName.requestFocus()
        }
        else if(binding.IdPtEmailId.text.toString().trim().isEmpty()){
            binding.IdPtEmailId.setError("Please enter your email ")
            binding.IdPtEmailId.requestFocus()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(binding.IdPtEmailId.text.toString().trim()).matches()){
            binding.IdPtEmailId.setError("Email pattern is not matchecd")
            binding.IdPtEmailId.requestFocus()
        }
        else if(binding.ptPhoneNo.text!!.length<10){
            binding.ptPhoneNo.setError("Number can't be less than 10 digits")
            binding.ptPhoneNo.requestFocus()
        }
        else if(binding.ptDob.text.toString().length < 6){
            binding.ptDob.setError("dob cannot be empty")
            binding.ptDob.requestFocus()
        }
        else{
            auth.createUserWithEmailAndPassword(binding.IdPtEmailId.text.toString().trim(), binding.IdPtCreatePassword.text.toString().trim()).addOnCompleteListener(
            ) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "User registered successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val fireBaseData= FirebaseDatabase.getInstance()


                    val User1=UserProfile();
                    User1.fullname=binding.IdPtFullName.text.toString().trim();
                    User1.email=binding.IdPtEmailId.text.toString().trim();
                    User1.dob=binding.ptDob.text.toString().trim()
                    User1.phoneNumber=binding.ptPhoneNo.text.toString().trim()
                    fireBaseData.getReference("Users").child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(User1).addOnCompleteListener(this){
                        if(it.isSuccessful){
                            Toast.makeText(
                                this,
                                "User data saved successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else{
                            Toast.makeText(
                                this,
                                "User data not saved due to error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    val intent = Intent(this, PatientLoginActivity::class.java)
                    startActivity(intent)
                } else {

                    Toast.makeText(
                        this,
                        "registration failed ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
        
        

        