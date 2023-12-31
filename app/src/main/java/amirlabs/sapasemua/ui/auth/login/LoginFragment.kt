package amirlabs.sapasemua.ui.auth.login

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentLoginBinding
import amirlabs.sapasemua.ui.auth.AuthContainerFragmentDirections
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.PrefsKey
import amirlabs.sapasemua.utils.getViewModel
import amirlabs.sapasemua.utils.logError
import amirlabs.sapasemua.utils.prefs
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import androidx.navigation.findNavController

class LoginFragment : DevFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override val vm: LoginViewModel by getViewModel()
    override val devView = this
    private val authNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_auth) }
    private val mainNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }

    override fun initData() {

    }

    override fun initUI() {
        binding.btnLogin.isEnabled = isVerified()
    }

    override fun initAction() {
        binding.etEmail.editText?.doAfterTextChanged {
            verifyEmail()
            binding.btnLogin.isEnabled = isVerified()
        }
        binding.etPassword.editText?.doAfterTextChanged { text ->
            verifyNewPassword(text.toString())
            binding.btnLogin.isEnabled = isVerified()
        }
        binding.tabRegister.setOnClickListener {
            authNavController?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
//        binding.tvForgetPassword.setOnClickListener {
//            authNavController?.navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
//        }
        binding.btnLogin.setOnClickListener {
            binding.btnLogin.startAnimation()
            vm.performLogin(
                binding.etEmail.editText?.text.toString(),
                binding.etPassword.editText?.text.toString()
            )
        }
    }

    override fun initObserver() {
        vm.loginStatus.observe(viewLifecycleOwner) {
            when (it) {
                is DevState.Loading -> {
                    binding.etEmail.isEnabled = false
                    binding.etPassword.isEnabled = false
                    binding.btnLogin.isClickable = false
                    binding.tabRegister.isEnabled = false
                }

                is DevState.Success -> {
                    binding.btnLogin.revertAnimation()
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    mainNavController?.navigate(AuthContainerFragmentDirections.actionAuthContainerFragmentToMenuContainerFragment())
                }

                is DevState.Failure -> {
                    binding.btnLogin.revertAnimation {
                        binding.btnLogin.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.sample, null)
                    }
                    binding.etEmail.isEnabled = true
                    binding.etPassword.isEnabled = true
                    binding.btnLogin.isClickable = true
                    binding.tabRegister.isEnabled = true
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    logError(it.message)
                }

                is DevState.Default -> {}
                is DevState.Empty -> {}
            }
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

    private fun isVerified(): Boolean {
        return binding.etEmail.error == null && binding.etEmail.editText?.text?.isNotEmpty() == true &&
                binding.etPassword.error == null && binding.etPassword.editText?.text?.isNotEmpty() == true
    }

    override fun onMessageError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}