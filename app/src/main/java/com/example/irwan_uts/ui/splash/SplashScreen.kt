package com.example.irwan_uts.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.ViewModelProvider
import com.example.irwan_uts.MainActivity
import com.example.irwan_uts.ui.auth.AuthActivity
import com.example.irwan_uts.R
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        viewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]

        coroutineScope.launch {
            delay(SPLASH_DELAY_MS)
            checkLoginStatus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            viewModel.getToken.observe(this@SplashScreen) { token ->
                if (token.isNotEmpty()) {
                    navigateToMainActivity()
                } else {
                    navigateToAuthActivity()
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val SPLASH_DELAY_MS = 3000L
    }
}
