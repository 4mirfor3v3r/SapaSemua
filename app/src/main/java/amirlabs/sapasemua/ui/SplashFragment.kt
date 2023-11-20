package amirlabs.sapasemua.ui

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentSplashBinding
import android.os.Handler
import android.os.Looper
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

@Suppress("DEPRECATION")
class SplashFragment : DevFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    private val mainNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }
    override fun initData() {

    }

    override fun initUI() {
    }

    override fun initAction() {
            Handler(Looper.getMainLooper()).postDelayed({
                mainNavController?.navigate(SplashFragmentDirections.actionSplashFragmentToAuthContainerFragment())
            }, 1200)
    }

    override fun initObserver() {
    }
}