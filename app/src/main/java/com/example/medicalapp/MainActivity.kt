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

        binding.bookDr= resources.getString(R.string.book_your_doctor_now)
        binding.loginAs= resources.getString(R.string.login_as)
        binding.doctor=resources.getString(R.string.doctor)
        binding.or = resources.getString(R.string.or)
        binding.patient= resources.getString(R.string.patient)

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