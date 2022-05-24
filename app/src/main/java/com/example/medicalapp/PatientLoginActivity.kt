package com.example.medicalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.medicalapp.databinding.ActivityPatientLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PatientLoginActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityPatientLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_patient_login)
        auth = Firebase.auth

        binding.ptMedico = "Medico"
        binding.ptEmail = "Enter Email Id"
        binding.ptPassword = "Enter Password"
        binding.ptLogin = "LOGIN"
        binding.ptNoAccountText = "You don't have Medico account yet?"
        binding.ptSignUpNow = "SIGN UP NOW"

        binding.IdPtSignUpNow.setOnClickListener {
            val intent = Intent(this, Patient_Registration_Activity::class.java)
            startActivity(intent)
        }

        binding.IdPatientLogin.setOnClickListener {
            if (binding.patientEmail.text.toString()
                    .trim() == "" || binding.patientPassword.text.toString().trim() == ""
            ) {
                Toast.makeText(
                    this,
                    "Enter User Credentials",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                auth.signInWithEmailAndPassword(
                    binding.patientEmail.text.toString().trim(),
                    binding.patientPassword.text.toString().trim()
                ).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val intent = Intent(this@PatientLoginActivity, DoctorsList::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Invalid User Credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}