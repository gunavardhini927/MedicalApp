package com.example.medicalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.medicalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.bookDr= "Book Your Doctor Now!!"
        binding.loginAs= "LOGIN AS"
        binding.doctor=" DOCTOR"
        binding.or = "OR"
        binding.patient= "PATIENT"

        binding.IdDoctorBtn.setOnClickListener{
            val intent = Intent(this,DoctorLoginActivity::class.java)
            startActivity(intent)
        }

        binding.IdPatientBtn.setOnClickListener{
            val intent = Intent(this,PatientLoginActivity::class.java)
            startActivity(intent)
        }
    }

}