package com.example.irwan_uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.irwan_uts.ui.home.Home
import com.example.irwan_uts.ui.profile.Profile
import com.example.irwan_uts.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupBottomNavigationBar()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_container, Home(), Home::class.java.simpleName)
                .commit()
        }

        binding.bottomNavigationBar.setOnItemSelectedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupBottomNavigationBar() {
        binding.apply {
            bottomNavigationBar.background = null
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val tempFragment: Fragment = when (item.itemId) {
            R.id.menu_home -> Home()
            R.id.menu_profile -> Profile()
            else -> Home()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, tempFragment)
            .commit()
        return true
    }
}
