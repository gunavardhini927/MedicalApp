package com.example.medicalapp.doctorList

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalapp.*
import com.example.medicalapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class AllDoctorsList : AppCompatActivity(),DoctorItemData{
    private lateinit var ref: DatabaseReference
    private lateinit var RecyclerView: RecyclerView
    private lateinit var DoctorArrayList: ArrayList<UserProfileTwo>
    private lateinit var doctorAdapter:DoctorAdapter;
    var firebaseUser: FirebaseUser? = null
    private var Id_tv_name_one: TextView? = null
    private var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_doctors_list)

        Id_tv_name_one = findViewById(R.id.Id_tv_name_one)

        uid = intent.getStringExtra("message_key")!!

        firebaseUser = FirebaseAuth.getInstance().currentUser
        ref = FirebaseDatabase.getInstance().reference.child("Doctors")

        RecyclerView = findViewById(R.id.list)

        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)
        DoctorArrayList = arrayListOf<UserProfileTwo>()



        var intent: Intent = getIntent()
        var str = intent.getStringExtra("message_key")
        val query: Query =
            FirebaseDatabase.getInstance().getReference("Doctors").orderByChild("department")
                .equalTo(str)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    val yourModel: UserProfileTwo? =
                        childSnapshot.getValue(UserProfileTwo::class.java)
                    DoctorArrayList.add(yourModel!!)
                    //user!!.setUID(userSnapshot.key)
                }


                RecyclerView.adapter= DoctorAdapter(DoctorArrayList,this@AllDoctorsList)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AllDoctorsList,error.message,Toast.LENGTH_LONG).show()
            }
        })
    }
    override  fun handleDoctorItemData(item: UserProfileTwo){
        super.handleDoctorItemData(item)
        val bundle = Bundle()
        bundle.putSerializable("doctor", item)
        val intent = Intent(this,SchedulePage::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }
    }


