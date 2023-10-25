package amirlabs.sapasemua.ui.menu.home

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.base.DevViewModel
import amirlabs.sapasemua.databinding.FragmentHomeBinding
import amirlabs.sapasemua.utils.getViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.bumptech.glide.Glide

class HomeFragment : DevFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val vm: HomeViewModel by getViewModel()
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    override fun initData() {

    }

    override fun initUI() {
        binding.tvTitle.text = vm.user?.name
        Glide.with(this)
            .load(vm.user?.avatar)
            .error(R.drawable.ic_profile)
            .into(binding.ivProfile)
    }

    override fun initAction() {
        binding.btnTranslate.setOnClickListener {
            menuNavController?.navigate(HomeFragmentDirections.actionHomeFragmentToTranslateFragment())
        }
    }

    override fun initObserver() {

    }
}