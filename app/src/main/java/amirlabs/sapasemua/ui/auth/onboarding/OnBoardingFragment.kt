package amirlabs.sapasemua.ui.auth.onboarding

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentOnBoardingBinding
import amirlabs.sapasemua.utils.PrefsKey
import amirlabs.sapasemua.utils.prefs
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2

class OnBoardingFragment : DevFragment<FragmentOnBoardingBinding>(R.layout.fragment_on_boarding) {
    private val authNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_auth) }
    private val mainNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }
    private lateinit var adapter: OnBoardingAdapter
    override fun initData() {
        adapter = OnBoardingAdapter{

        }
//        if (prefs().getBoolean(PrefsKey.IS_LOGGED_IN, false)) {
//            mainNavController?.navigate(AuthContainerFragmentDirections.actionAuthContainerFragmentToMenuContainerFragment())
////            authNavController?.navigate(WalkthroughFragmentDirections.actionWalkthroughFragmentToRegisterFragment())
//        } else if (!prefs().getBoolean(PrefsKey.IS_WALKTHROUGH, true)) {
//            authNavController?.navigate(WalkthroughFragmentDirections.actionWalkthroughFragmentToLoginFragment())
//        }

//        if (!prefs().getBoolean(PrefsKey.IS_WALKTHROUGH, false)) {
//            authNavController?.navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
//        }
    }

    override fun initUI() {
        binding.vpBoarding.adapter = adapter
        binding.tabIndicator.setViewPager2(binding.vpBoarding)
    }

    override fun initAction() {
        binding.vpBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnNext.isVisible = position != adapter.itemCount - 1
                binding.btnStartContainer.isVisible = position == adapter.itemCount - 1
                binding.btnStart.setOnClickListener {
                    prefs().setBoolean(PrefsKey.IS_WALKTHROUGH, false)
                    authNavController?.navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToLoginFragment())
                }
                binding.btnNext.setOnClickListener {
                    binding.vpBoarding.setCurrentItem(binding.vpBoarding.currentItem + 1, true)
                }
            }
        })
    }

    override fun initObserver() {

    }
}