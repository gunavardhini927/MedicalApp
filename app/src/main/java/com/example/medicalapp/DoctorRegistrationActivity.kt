package com.example.medicalapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class DoctorRegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorRegistrationBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var filePath: Uri
    private lateinit var imageUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_registration)

        auth = Firebase.auth

        binding.upload = resources.getString(R.string.upload_image)
        binding.drName = resources.getString(R.string.enter_full_name)
        binding.drEmailId = resources.getString(R.string.enter_email_id)
        binding.drMedicalID = resources.getString(R.string.doctor_medical_id)
        binding.qualification = resources.getString(R.string.qualification)
        binding.department = resources.getString(R.string.department)
        binding.experience = resources.getString(R.string.experience)
        binding.drPassword = resources.getString(R.string.create_password)
        binding.drPasswordText = resources.getString(R.string.password_text)
        binding.drPhone = resources.getString(R.string.enter_phone_no)
        binding.drSaveAndContinue = resources.getString(R.string.submit)



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
        binding.imageView.setOnClickListener({
                addImage()
        })

        binding.uploadImage.setOnClickListener({
            uploadImage()
        }
        )
        binding.IdDrSaveAndContinue.setOnClickListener {
            registerUser()
        }
    }
    private fun uploadImage() {
        val pd = ProgressDialog(this)
        pd.setTitle("Uploading")
        pd.show()

        val randomKey: String = UUID.randomUUID().toString()
        val imageRef: StorageReference =
            FirebaseStorage.getInstance().reference.child("Doctor profile Image/$randomKey")
         val uploadTask = imageRef.putFile(filePath)
            .addOnProgressListener { task ->
                val progress = (100.0 * task.bytesTransferred) / task.totalByteCount
                pd.setMessage("Uploaded ${progress.toInt()}%")
            }

        uploadTask.continueWithTask() { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }

            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    pd.dismiss()
                    imageUrl = task.result.toString()
                }
            }
            .addOnSuccessListener {
                pd.dismiss()
                Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { task ->
                pd.dismiss()
                Toast.makeText(this, task.message, Toast.LENGTH_SHORT).show()
            }
    }
    private fun addImage() {
        val i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Choose image"), 71)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 71 && resultCode == Activity.RESULT_OK && data != null) {
            filePath = data.data!!
            val imageUri = data.data
            binding.imageView.setImageURI(imageUri)
            imageUrl = imageUri.toString()
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
                    User2.profileImgUrl=imageUrl.trim()
                    val dept: String = binding.spinner.getSelectedItem().toString()
                    User2.department=dept

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





