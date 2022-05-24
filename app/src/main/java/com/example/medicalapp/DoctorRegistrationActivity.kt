package com.example.medicalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import com.example.medicalapp.databinding.ActivityDoctorLoginBinding
import com.example.medicalapp.databinding.ActivityDoctorRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class DoctorRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorRegistrationBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_registration)

        auth = Firebase.auth

        binding.drMedico = "Medico"
        binding.drName = "Enter Full Name"
        binding.drEmailId = "Enter Email Id"
        binding.drMedicalID = "Enter Medical Id"
        binding.qualification = "Enter Qualification"
        binding.department = "Select Department"
        binding.experience = "Enter Experience"
        binding.drPassword = "Create Password"
        binding.drPasswordText="(Password Should be at least Min 8 chars, 1 number, 1 symbol. With combination of both upper and lower case alphabets)"
        binding.drPhone = "Enter Phone Number"
        binding.drSaveAndContinue = "SAVE AND CONTINUE"

        //val spinner = findViewById<Spinner>(R.id.spinner)

        val Departments = resources.getStringArray(R.array.Departments)
        if (binding.spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, Departments
            )
            binding.spinner.adapter = adapter

            binding.spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    Toast.makeText(
                        this@DoctorRegistrationActivity,
                        getString(R.string.selected_item) + " " +
                                "" + Departments[position], Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }

        }
        binding.IdDrSaveAndContinue.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        if (binding.IdRegDocFullName.text.toString().trim().isEmpty()) {
            binding.IdRegDocFullName.setError("Please enter Full Name");
            binding.IdRegDocFullName.requestFocus();
        } else if (binding.docMedicalId.text.toString().trim().isEmpty()) {
            binding.docMedicalId.setError("Please enter your Medical Id ")
            binding.docMedicalId.requestFocus()
        } else if (binding.IdRegDocEmail.text.toString().trim().isEmpty()) {
            binding.IdRegDocEmail.setError("Please enter your email ")
            binding.IdRegDocEmail.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.IdRegDocEmail.text.toString().trim())
                .matches()
        ) {
            binding.IdRegDocEmail.setError("Email pattern is not matchecd")
            binding.IdRegDocEmail.requestFocus()
        } else if (binding.IdRegDocPhoneno.text!!.length < 10) {
            binding.IdRegDocPhoneno.setError("Number can't be less than 10 digits")
            binding.IdRegDocPhoneno.requestFocus()
        } else if (binding.IdRegDocPasssword.text.toString().trim().isEmpty()) {
            binding.IdRegDocPasssword.setError("password can't be empty")
            binding.IdRegDocPasssword.requestFocus()
        } else if (binding.docQualification.text.toString().trim().isEmpty()) {
            binding.docQualification.setError("Qualification can't be empty")
            binding.docQualification.requestFocus()
        } else if (binding.docExperience.text.toString().trim().isEmpty()) {
            binding.docExperience.setError("Experience can't be empty")
            binding.docExperience.requestFocus()
        }
        else {
            auth.createUserWithEmailAndPassword(
                binding.IdRegDocEmail.text.toString().trim(),
                binding.IdRegDocPasssword.text.toString().trim()
            ).addOnCompleteListener(
            ) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "User registered successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    val fireBaseData = FirebaseDatabase.getInstance()
                    val User2 = UserProfileTwo();
                    User2.fullname = binding.IdRegDocFullName.text.toString().trim();
                    User2.email = binding.IdRegDocEmail.text.toString().trim();
                    User2.qualification = binding.docQualification.text.toString().trim()
                    User2.phoneNumber = binding.IdRegDocPhoneno.text.toString().trim()
                    User2.medicalid = binding.docMedicalId.text.toString().trim()
                    User2.experience = binding.docExperience.text.toString().trim()

                    fireBaseData.getReference("Doctors")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(User2)
                        .addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    this,
                                    "User data saved successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    "User data not saved due to error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    val intent = Intent(this, DoctorLoginActivity::class.java)
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





