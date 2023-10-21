package amirlabs.sapasemua.ui.menu.module

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentModuleBinding
import androidx.navigation.NavController
import androidx.navigation.findNavController

class ModuleFragment : DevFragment<FragmentModuleBinding>(R.layout.fragment_module) {
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    override fun initData() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        binding.btnAddModule.setOnClickListener {
            menuNavController?.navigate(ModuleFragmentDirections.actionModuleFragmentToAddModuleFragment())
        }
    }

    override fun initObserver() {

    }
}