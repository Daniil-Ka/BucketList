package com.tms.bucketlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.AuthUI.IdpConfig.EmailBuilder
import com.firebase.ui.auth.AuthUI.TAG
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthActivity : AppCompatActivity() {
    // https://developer.android.com/training/basics/intents/result
    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
                res -> this.onSignInResult(res)
        }

    //Debug
    private val authTAG = "AuthTAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (isLogin()) {
            GlobalScope.launch { onLogin(currentUser!!) }
        }
        else {
            emailLink()
        }
    }

    /** вход успешен */
    private suspend fun onLogin(user: FirebaseUser) {
        val token = getJWT() ?: throw NullPointerException("token is null")
        Log.d(authTAG, token.signInProvider.toString())

        val i = Intent(this@AuthActivity, MainActivity::class.java)
        startActivity(i)
    }

    private val currentUser get() = FirebaseAuth.getInstance().currentUser

    private fun isLogin() : Boolean = currentUser != null

    private suspend fun getJWT() : GetTokenResult? {

        val token = currentUser?.getIdToken(true)?.await()
        return token
    }

    /** начать вход */
    private fun createSignInIntent() {
        val providers = arrayListOf(EmailBuilder().build(), IdpConfig.GoogleBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            // успешно авторизовались
            val user = currentUser
            Log.d("Auth", "success $user")

            GlobalScope.launch {  }

        } else {
            // Не получилось авторизоваться
            val response = result.idpResponse
            if (response == null){
                // пользователь сам отменил
            }
            val error = response?.error?.errorCode
            Log.d("Error", error.toString())
            Toast.makeText(this, "Error $error", Toast.LENGTH_SHORT).show()
        }
    }

    /** выйти из аккаунта */
    fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.d("Auth", "Выход из аккаунта")
            }
    }

    /** удалить аккаунт */
    fun delete() {
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(authTAG, "Аккаунт удалён")
                }
            }
    }

    fun emailLink() {
        val minimumVersion = "30";
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName( "com.tms.bucketlist", true, minimumVersion)
            //https://firebase.google.com/docs/auth/android/email-link-auth?authuser=0&hl=en#send_an_authentication_link_to_the_users_email_address
            .setHandleCodeInApp(true) // это должно быть так
            .setUrl("https://tmsbucketlist.page.link") // This URL needs to be whitelisted
            .setDynamicLinkDomain("tmsbucketlist.page.link")
            .build()

        val providers = listOf(
            IdpConfig.GoogleBuilder().build(),
            EmailBuilder()
                .enableEmailLinkSignIn()
                .setActionCodeSettings(actionCodeSettings)
                .build()
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            //.setLogo(R.drawable.my_great_logo)
            //.setTheme(R.style.MySuperAppTheme)
            .build()
        signInLauncher.launch(signInIntent)
    }

    fun catchEmailLink() {
        val providers: List<IdpConfig> = emptyList()

        if (AuthUI.canHandleIntent(intent)) {
            val extras = intent.extras ?: return
            val link = extras.getString("email_link_sign_in")
            if (link != null) {
                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setEmailLink(link)
                    .setAvailableProviders(providers)
                    .build()
                signInLauncher.launch(signInIntent)
            }
        }
    }
}