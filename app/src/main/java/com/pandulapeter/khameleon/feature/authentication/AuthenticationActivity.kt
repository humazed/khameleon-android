package com.pandulapeter.khameleon.feature.authentication

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.pandulapeter.khameleon.AuthenticationActivityBinding
import com.pandulapeter.khameleon.R
import com.pandulapeter.khameleon.data.repository.UserRepository
import com.pandulapeter.khameleon.feature.home.HomeActivity
import com.pandulapeter.khameleon.feature.shared.KhameleonActivity
import com.pandulapeter.khameleon.util.showSnackbar
import org.koin.android.ext.android.inject


class AuthenticationActivity : KhameleonActivity<AuthenticationActivityBinding>(R.layout.activity_authentication) {

    companion object {
        private const val AUTHENTICATION_REQUEST = 435
    }

    private val userRepository by inject<UserRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Authentication)
        super.onCreate(savedInstanceState)
        if (userRepository.getSignedInUser() == null) {
            setTitle(R.string.authentication)
            binding.signInButton.setSize(SignInButton.SIZE_WIDE)
            binding.signInButton.setOnClickListener { startActivityForResult(userRepository.getSignInIntent(), AUTHENTICATION_REQUEST) }
        } else {
            startHomeScreen()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == AUTHENTICATION_REQUEST) {
            try {
                userRepository.signIn(data)
            } catch (exception: ApiException) {
                binding.root.showSnackbar(getString(R.string.something_went_wrong_reason, GoogleSignInStatusCodes.getStatusCodeString(exception.statusCode)))
            }
            if (userRepository.getSignedInUser() != null) {
                startHomeScreen()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}