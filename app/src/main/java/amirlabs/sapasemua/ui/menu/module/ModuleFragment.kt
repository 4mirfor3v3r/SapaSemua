package amirlabs.sapasemua.ui.menu.module

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentModuleBinding
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.getViewModel
import amirlabs.sapasemua.utils.logError
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController

class ModuleFragment : DevFragment<FragmentModuleBinding>(R.layout.fragment_module) {
    override val vm: ModuleViewModel by getViewModel()
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private lateinit var adapter: ModuleAdapter
    override fun initData() {
        adapter = ModuleAdapter {
            menuNavController?.navigate(
                ModuleFragmentDirections.actionModuleFragmentToSubModuleFragment(
                    it.id
                )
            )
        }
    }

    override fun initUI() {
        binding.rvModule.adapter = adapter
    }

    override fun initAction() {
        binding.btnAddModule.setOnClickListener {
            menuNavController?.navigate(ModuleFragmentDirections.actionModuleFragmentToAddModuleFragment())
        }
        vm.getAllModule()
    }

    override fun initObserver() {
        vm.modules.observe(viewLifecycleOwner) {
            when (it) {
                is DevState.Loading -> {
                }

                is DevState.Success -> {
                    adapter.updateList(it.data)
                }

                is DevState.Failure -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    logError(it.message)
                }

                is DevState.Default -> {}
                is DevState.Empty -> {}
            }
        }
    }
}