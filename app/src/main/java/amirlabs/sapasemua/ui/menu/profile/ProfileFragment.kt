package amirlabs.sapasemua.ui.menu.profile

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentProfileBinding
import amirlabs.sapasemua.ui.menu.MenuContainerFragmentDirections
import amirlabs.sapasemua.utils.prefs
import androidx.navigation.findNavController

class ProfileFragment : DevFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val mainNavController by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }
    override fun initData() {

    }

    override fun initUI() {
        binding.btnSignOut.setOnClickListener {
            prefs().clear()
            mainNavController?.navigate(MenuContainerFragmentDirections.actionMenuContainerFragmentToAuthContainerFragment())
        }
    }

    override fun initAction() {

    }

    override fun initObserver() {

    }
}
