package amirlabs.sapasemua.ui.menu.forum

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentForumBinding
import androidx.navigation.NavController
import androidx.navigation.findNavController

class ForumFragment : DevFragment<FragmentForumBinding>(R.layout.fragment_forum) {
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    override fun initData() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        binding.tvViewAll.setOnClickListener {
            menuNavController?.navigate(ForumFragmentDirections.actionForumFragmentToForumListFragment())
        }
        binding.btnCreateReport.setOnClickListener{
            menuNavController?.navigate(ForumFragmentDirections.actionForumFragmentToCreateDiscussionFragment())
        }
    }

    override fun initObserver() {

    }
}