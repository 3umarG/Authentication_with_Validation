package com.example.validationdemo

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.example.validationdemo.databinding.ActivityMainBinding

class RefisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnKeyListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextPassword.onFocusChangeListener = this
        binding.editTextEmail.onFocusChangeListener = this
        binding.editTextConfirmPassword.onFocusChangeListener = this
        binding.editTextFullName.onFocusChangeListener = this


    }

    private fun validateFullName(et: EditText = binding.editTextFullName): Boolean {
        var errorMess: String? = null
        val text = et.text.toString().trim()
        if (text.isEmpty()) {
            errorMess = "Full Name is required"
        }

        // There is an error
        if (errorMess != null) {
            binding.inputLayoutFullName.apply {
                isErrorEnabled = true
                error = errorMess
            }
        } else {
            binding.inputLayoutFullName.boxStrokeColor = Color.parseColor("#00C400")
        }
        return errorMess == null

    }

    private fun validateEmailAddress(et: EditText = binding.editTextEmail): Boolean {
        var errorMess: String? = null
        val text = et.text.toString().trim()
        if (text.isEmpty()) {
            errorMess = "Email is required"
        } else if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            errorMess = "Email Address is invalid"
        }

        // There is an error
        if (errorMess != null) {
            binding.inputLayoutEmail.apply {
                isErrorEnabled = true
                error = errorMess
            }
        } else {
            binding.inputLayoutEmail.boxStrokeColor = Color.parseColor("#00C400")
        }
        return errorMess == null
    }

    private fun validatePassword(et: EditText = binding.editTextPassword): Boolean {
        var errorMess: String? = null
        val text = et.text.toString().trim()
        if (text.isEmpty()) {
            errorMess = "Password is required"
        } else if (text.length < 6) {
            errorMess = "Password must be more than 6 characters"
        }
        // There is an error
        if (errorMess != null) {
            binding.inputLayoutPassword.apply {
                isErrorEnabled = true
                error = errorMess
            }
        }
        return errorMess == null
    }

    private fun validateConfirmPassword(et: EditText = binding.editTextConfirmPassword): Boolean {
        var errorMess: String? = null
        val text = et.text.toString().trim()
        if (text.isEmpty()) {
            errorMess = "Confirm Password is required"
        } else if (text.length < 6) {
            errorMess = "Confirm Password must be more than 6 characters"
        }

        // There is an error
        if (errorMess != null) {
            binding.inputLayoutConfirmPassword.apply {
                isErrorEnabled = true
                error = errorMess
            }
        }
        return errorMess == null
    }

    private fun validatePasswordAndConfirmPassword(
        password: EditText = binding.editTextPassword,
        cPassword: EditText = binding.editTextConfirmPassword
    ): Boolean {
        var errorMess: String? = null
        val passValue = password.text.toString().trim()
        val confPassValue = cPassword.text.toString().trim()
        if (passValue != confPassValue) {
            errorMess = "Confirm Password doesn't match with Password"
        }

        // There is an error
        if (errorMess != null) {
            binding.inputLayoutConfirmPassword.apply {
                isErrorEnabled = true
                error = errorMess
            }
        }

        return errorMess == null
    }


    override fun onClick(view: View?) {

    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.editTextFullName -> {
                    if (hasFocus) {
                        // When the view is hasFocus that mean the user write NOW !!
                        if (binding.inputLayoutFullName.isErrorEnabled) {
                            binding.inputLayoutFullName.isErrorEnabled = false
                        }
                    } else {
                        // You exit and finish write from this field
                        validateFullName()
                    }
                }
                R.id.editTextEmail -> {
                    if (hasFocus) {
                        if (binding.inputLayoutEmail.isErrorEnabled) {
                            binding.inputLayoutEmail.isErrorEnabled = false
                        }
                    } else {
                        if (validateEmailAddress()) {
                            // TODO: do another validation of email uniqueness from backend...
                        }
                    }
                }
                R.id.editTextPassword -> {
                    if (hasFocus) {
                        if (binding.inputLayoutPassword.isErrorEnabled) {
                            binding.inputLayoutPassword.isErrorEnabled = false
                        }
                    } else {
                        if (
                            validatePassword()
//                            && binding.editTextConfirmPassword.text!!.isNotEmpty()
                            && validateConfirmPassword()
                            && validatePasswordAndConfirmPassword()
                        ) {
                            if (binding.inputLayoutConfirmPassword.isErrorEnabled) {
                                binding.inputLayoutConfirmPassword.isErrorEnabled = false
                            }
                        }
                    }
                }
                R.id.editTextConfirmPassword -> {
                    if (hasFocus) {
                        if (binding.inputLayoutConfirmPassword.isErrorEnabled) {
                            binding.inputLayoutConfirmPassword.isErrorEnabled = false
                        }
                    } else {
                        if (
                            validatePassword()
                            && validateConfirmPassword()
                            && validatePasswordAndConfirmPassword()
                            // TODO: this might be deleted !!!!!!!!
                            && binding.editTextPassword.text!!.isNotEmpty()
                        ) {
                            if (binding.inputLayoutPassword.isErrorEnabled) {
                                binding.inputLayoutPassword.isErrorEnabled = false
                            }
                            binding.apply {
                                inputLayoutConfirmPassword.apply {
                                    setStartIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                    setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                                }
                                inputLayoutPassword.apply {
                                    setStartIconDrawable(R.drawable.ic_baseline_check_circle_24)
                                    setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        return false
    }
}