package com.example.newsspace.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsspace.R
import com.example.newsspace.dao.UserDao
import com.example.newsspace.models.User
import com.example.newsspace.views.viewmodels.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var viewModel: SignInViewModel
    private lateinit var mPreference: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initSignInButton()
        initSignInViewModel()
        initFirebaseAuth()

        mPreference = getPreferences(MODE_PRIVATE)

    }

    private fun initFirebaseAuth() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("344379595880-vual1i7l79s5e7p0bcbklqc4jbgaspl2.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = Firebase.auth
    }

    private fun initSignInViewModel() {
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
    }

    private fun initSignInButton() {
        val button = findViewById<Button>(R.id.signInButton)
        button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        viewModel.authenticatedUserLiveData?.observe(this, Observer {
            if (it != null) {
                updateUI(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        Log.d("thik hai","thik hai part 1")
        viewModel.signInWithGoogle(credential)
        Log.d("thik hai","thik hai part 2")
        viewModel.authenticatedUserLiveData?.observe(this, Observer {
            if (it != null) {
                val userdao= UserDao()
                userdao.addUser(it)
                updateUI(it)
            }
        })

    }

    private fun updateUI(user: User) {

        Log.d("thik hai","thik hai part 3")
        Log.d("thik hai name",user.userName.toString())
        val prefsEditor: SharedPreferences.Editor = mPreference.edit()
        val gson = Gson()
        val json = gson.toJson(user)
        Log.d("json cov",json.toString())
        prefsEditor.putString("myaccount", json)
        prefsEditor.apply()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}