package amirlabs.sapasemua.ui.menu.home

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.base.DevViewModel
import amirlabs.sapasemua.databinding.FragmentHomeBinding
import amirlabs.sapasemua.utils.getViewModel
import com.bumptech.glide.Glide

class HomeFragment : DevFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val vm: HomeViewModel by getViewModel()
    override fun initData() {

    }

    override fun initUI() {
        binding.tvTitle.text = vm.user?.name
        Glide.with(this)
            .load(vm.user?.avatar)
            .into(binding.ivProfile)
    }

    override fun initAction() {

    }

    override fun initObserver() {

    }
}