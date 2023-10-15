package amirlabs.sapasemua.ui.auth.register

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentRegisterBinding
import amirlabs.sapasemua.ui.auth.AuthContainerFragmentDirections
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import androidx.navigation.findNavController

class RegisterFragment: DevFragment<FragmentRegisterBinding>(R.layout.fragment_register){
    private val authNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_auth) }
    private val mainNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }
    override fun initData() {
    }

    override fun initUI() {
    }

    override fun initAction() {
        verifyFullName()
        verifyEmail()
        verifyNewPassword("")

        binding.etName.editText?.doAfterTextChanged {
            verifyFullName()
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.etEmail.editText?.doAfterTextChanged {
            verifyEmail()
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.etPassword.editText?.doAfterTextChanged { text ->
            verifyNewPassword(text.toString())
            verifyConfirmPassword()

            binding.btnRegister.isEnabled = isVerified()
        }
        binding.etConfirmPassword.editText?.doAfterTextChanged {
            verifyConfirmPassword()
            binding.btnRegister.isEnabled = isVerified()
        }
        binding.tabLogin.setOnClickListener {
            authNavController?.popBackStack()
        }
        binding.btnRegister.setOnClickListener {
            binding.btnRegister.startAnimation()
            Handler(Looper.getMainLooper()).postDelayed({
                mainNavController?.navigate(AuthContainerFragmentDirections.actionAuthContainerFragmentToMenuContainerFragment())
            },3000)
//            vm.performRegister(
//                binding.etFullName.editText?.text.toString(),
//                binding.etEmail.editText?.text.toString(),
//                binding.etNewPassword.editText?.text.toString()
//            )
        }
    }

    override fun initObserver() {
    }


    private fun verifyFullName() {
        if ((binding.etName.editText?.text?.length ?: 0) < 3) {
            binding.etName.error = "Minimal terdiri dari 3 huruf"
        } else {
            binding.etName.error = null
        }
    }

    private fun verifyEmail() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.editText?.text.toString()).matches()) {
            binding.etEmail.error = "Format Email tidak sesuai"
        } else {
            binding.etEmail.error = null
        }
    }

    private fun verifyNewPassword(text: String) {
        if (text.length < 8 || text == text.lowercase() || text == text.uppercase()) {
            binding.etPassword.error = "Password terdiri dari 8 kata, huruf kecil dan besar"
        } else {
            binding.etPassword.error = null
        }
    }

    private fun verifyConfirmPassword() {
        if (binding.etConfirmPassword.editText?.text.toString() == binding.etPassword.editText?.text.toString()) {
            binding.etConfirmPassword.error = null
        } else {
            binding.etConfirmPassword.error = "Password tidak sama"
        }
    }

    private fun isVerified(): Boolean {
        return binding.etName.error == null &&
                binding.etEmail.error == null &&
                binding.etPassword.error == null &&
                binding.etConfirmPassword.error == null
    }
}