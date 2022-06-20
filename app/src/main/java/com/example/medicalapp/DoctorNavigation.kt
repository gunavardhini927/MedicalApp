package com.example.medicalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.medicalapp.doctorfragment.DrAppointmentFragment
import com.example.medicalapp.doctorfragment.DrHomeFragment
import com.example.medicalapp.doctorfragment.DrProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DoctorNavigation : AppCompatActivity() {
    private val drHomeFrag = DrHomeFragment()
    private val drProfileFrag = DrProfileFragment()
    private val drAppointment = DrAppointmentFragment()

    private lateinit var bottmnav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_navigation)
        bottmnav = findViewById(R.id.bottmDr)
        replaceFragment(drHomeFrag)

        bottmnav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.doc_home -> replaceFragment(drHomeFrag)
                R.id.doc_profile-> replaceFragment(drProfileFrag)
                R.id.doc_appointment -> replaceFragment(drAppointment)

            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.doc_frag_container, fragment)
            transaction.commit()
        }
    }
}