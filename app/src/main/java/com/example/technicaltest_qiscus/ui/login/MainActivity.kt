package com.example.technicaltest_qiscus.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.technicaltest_qiscus.R
import com.example.technicaltest_qiscus.databinding.ActivityMainBinding
import com.example.technicaltest_qiscus.ui.listchat.ListChatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModel()
    private val binding by viewBinding(ActivityMainBinding::bind)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Login to Qiscus"
        mainViewModel.start()

        val usernameStream = RxTextView.textChanges(binding.edUsername)
            .skipInitialValue()
            .map { username ->
                username.length < 3
            }
        usernameStream.subscribe {
            showUsernameMinimalAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.edPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 6
            }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it)
        }

        val nameStream = RxTextView.textChanges(binding.edPassword)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            showNameMinimalAlert(it)
        }

        val invalidFieldStream = Observable.combineLatest(
            usernameStream,
            passwordStream,
            nameStream,
            { usernameInvalid: Boolean, passwordInvalid: Boolean, nameInvalid: Boolean ->
                !usernameInvalid && !passwordInvalid && !nameInvalid
            })
        invalidFieldStream.subscribe { isValid ->
            if (isValid) {
                binding.btnLogin.isEnabled = true
                binding.btnLogin.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.purple_500
                    )
                )
                binding.btnLogin.setOnClickListener {
                    mainViewModel.login(
                        binding.edUsername.text.toString(),
                        binding.edPassword.text.toString(),
                        binding.edName.text.toString()
                    )
                }
            } else {
                binding.btnLogin.isEnabled = false
                binding.btnLogin.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.darker_grey
                    )
                )
            }
        }

        mainViewModel.userLogin.observe(this, { user ->
            if (user != null) {
                Toast.makeText(this, "Welcome ${user.name}", Toast.LENGTH_SHORT).show()
                goToHome()
            }
        })
        mainViewModel.currentUser.observe(this, { currentUser ->
            if (currentUser != null) {
                if (currentUser) {
                    goToHome()
                }
            }
        })
    }

    private fun goToHome() {
        val intent = Intent(this, ListChatActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showUsernameMinimalAlert(isNotValid: Boolean) {
        binding.edUsername.error = if (isNotValid) getString(R.string.username_not_valid) else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean) {
        binding.edPassword.error = if (isNotValid) getString(R.string.password_not_valid) else null
    }

    private fun showNameMinimalAlert(isNotValid: Boolean) {
        binding.edName.error = if (isNotValid) getString(R.string.name_not_valid) else null
    }
}