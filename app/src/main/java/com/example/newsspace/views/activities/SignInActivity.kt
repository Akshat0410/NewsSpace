package com.example.newsspace.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsspace.R
import com.example.newsspace.dao.UserDao
import com.example.newsspace.models.User
import com.example.newsspace.views.MainActivity
import com.example.newsspace.views.viewmodels.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var viewModel: SignInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initFirebaseAuth()
        initSignInViewModel()
        initSignInButton()


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
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
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
        if(auth.currentUser!=null){
            updateUI(auth.currentUser)
        }

    }

    override fun onResume() {
        super.onResume()
        if(auth.currentUser!=null){
            updateUI(auth.currentUser)
        }
    }

    override fun onRestart() {
        super.onRestart()
        if(auth.currentUser!=null){
            updateUI(auth.currentUser)
        }
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
        viewModel.signInWithGoogle(credential)
        viewModel.authenticatedUserLiveData?.observe(this, Observer {
            if (it != null) {
                val userdao= UserDao()
                userdao.addUser(it)
                updateUISignUp(it)
            }
        })

    }

    private fun updateUISignUp(it: User) {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun updateUI(user: FirebaseUser?) {
        if(user!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}