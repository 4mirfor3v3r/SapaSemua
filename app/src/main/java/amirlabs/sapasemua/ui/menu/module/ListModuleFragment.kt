package amirlabs.sapasemua.ui.menu.module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentListModuleBinding
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.getViewModel
import amirlabs.sapasemua.utils.logError
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController

class ListModuleFragment : DevFragment<FragmentListModuleBinding>(R.layout.fragment_list_module) {
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
        vm.getAllModule()
    }

    override fun initObserver() {
        vm.modules.observe(viewLifecycleOwner) {
            when (it) {
                is DevState.Loading -> {
                    binding.msvModule.showLoadingLayout()
                }
                is DevState.Empty -> {
                    binding.msvModule.showEmptyLayout()
                }
                is DevState.Success -> {
                    binding.msvModule.showDefaultLayout()
                    adapter.updateList(it.data)
                }

                is DevState.Failure -> {
                    binding.msvModule.showErrorLayout()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    logError(it.message)
                }

                is DevState.Default -> {

                }
            }
        }
    }
}