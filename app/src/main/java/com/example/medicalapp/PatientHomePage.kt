package com.example.medicalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.medicalapp.databinding.ActivityPatientHomePageBinding

class PatientHomePage : AppCompatActivity() {

    private lateinit var binding: ActivityPatientHomePageBinding
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityPatientHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=Navigation.findNavController(this,R.id.fragment_Container)
        setupWithNavController(binding.bottomNavigation,navController)


    }
}
