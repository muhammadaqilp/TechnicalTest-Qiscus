package com.example.technicaltest_qiscus.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.technicaltest_qiscus.R
import com.example.technicaltest_qiscus.databinding.ActivityMainBinding
import com.example.technicaltest_qiscus.ui.listchat.ListChatActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.start()

        binding.btnLogin.setOnClickListener {
            mainViewModel.login("Qwerty987", "Qwerty987", "Qwerty987")
        }

        mainViewModel.userLogin.observe(this, { user ->
            if (user != null) {
                Toast.makeText(this, "Welcome ${user.name}", Toast.LENGTH_SHORT).show()
            }
        })
        mainViewModel.currentUser.observe(this, { currentUser ->
            if (currentUser != null) {
                if (currentUser){
                    val intent = Intent(this, ListChatActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}