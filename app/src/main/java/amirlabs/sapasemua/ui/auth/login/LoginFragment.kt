package amirlabs.sapasemua.ui.auth.login

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentLoginBinding
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.getViewModel
import android.util.Patterns
import android.widget.Toast
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

    }

    override fun initAction() {
        verifyEmail()
        verifyNewPassword("")
//        binding.etEmail.editText?.doAfterTextChanged {
//            verifyEmail()
//            binding.btnLogin.isEnabled = isVerified()
//        }
//        binding.etPassword.editText?.doAfterTextChanged { text ->
//            verifyNewPassword(text.toString())
//            binding.btnLogin.isEnabled = isVerified()
//        }
//        binding.tvRegister.setOnClickListener {
//            authNavController?.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
//        }
//        binding.tvForgetPassword.setOnClickListener {
//            authNavController?.navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
//        }
//        binding.btnLogin.setOnClickListener {
//                vm.performLogin(
//                    binding.etEmail.editText?.text.toString(),
//                    binding.etPassword.editText?.text.toString()
//                )
//        }
    }

    override fun initObserver() {
        vm.loginStatus.observe(viewLifecycleOwner){
            when(it){
                is DevState.Loading -> {
//                    binding.btnBack.isEnabled = false
//                    binding.etEmail.isEnabled = false
//                    binding.etPassword.isEnabled = false
//                    binding.btnLogin.isEnabled = false
//                    binding.cbRememberMe.isEnabled = false
//                    binding.tvForgetPassword.isEnabled = false
//                    binding.tvRegister.isEnabled = false
                }
                is DevState.Success -> {
//                    prefs().setBoolean(PrefsKey.IS_LOGGED_IN, binding.cbRememberMe.isChecked)
//                    prefs().setString(PrefsKey.USER_ID, it.data.id?:"")
//                    prefs().setString(PrefsKey.USER_NAME, it.data.name.orEmpty())
//                    prefs().setString(PrefsKey.USER_EMAIL, it.data.email.orEmpty())
//                    if (it.data.profileUrl is String?) {
//                        prefs().setString(PrefsKey.USER_PROFILE_IMAGE, it.data.profileUrl.orEmpty())
//                    }
//
//                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
//                    mainNavController?.navigate(AuthContainerFragmentDirections.actionAuthContainerFragmentToMenuContainerFragment())
                }
                is DevState.Failure -> {
//                    binding.btnBack.isEnabled = true
//                    binding.etEmail.isEnabled = true
//                    binding.etPassword.isEnabled = true
//                    binding.btnLogin.isEnabled = true
//                    binding.cbRememberMe.isEnabled = true
//                    binding.tvForgetPassword.isEnabled = true
//                    binding.tvRegister.isEnabled = true
//
//                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
//                    logError(it.message)
                }
                is DevState.Default -> {}
                is DevState.Empty ->{}
            }
        }
    }

    private fun verifyEmail() {
//        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.editText?.text.toString()).matches()) {
//            binding.etEmail.error = getString(R.string.error_email_format)
//        } else {
//            binding.etEmail.error = null
//        }
    }

    private fun verifyNewPassword(text: String) {
//        if (text.length < 8 || text == text.lowercase() || text == text.uppercase()) {
//            binding.etPassword.error = getString(R.string.error_password_format)
//        } else {
//            binding.etPassword.error = null
//        }
    }

//    private fun isVerified(): Boolean {
//        return binding.etEmail.error == null &&
//                binding.etPassword.error == null
//    }

    override fun onMessageError(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}