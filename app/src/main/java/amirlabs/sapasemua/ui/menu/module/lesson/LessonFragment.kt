package amirlabs.sapasemua.ui.menu.module.lesson

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentLessonBinding
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.getViewModel
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

class LessonFragment : DevFragment<FragmentLessonBinding>(R.layout.fragment_lesson) {
    override val vm: LessonViewModel by getViewModel()
    private val args: LessonFragmentArgs by navArgs()
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    override fun initData() {

    }

    override fun initUI() {
    }

    override fun initAction() {
        vm.getLessonById(args.lessonId)
        binding.btnBack.setOnClickListener {
            menuNavController?.popBackStack()
        }
        binding.videoLesson.setOnPreparedListener {
            val videoRatio = it.videoWidth / it.videoHeight.toFloat()
            val screenRatio = binding.videoLesson.width / binding.videoLesson.height.toFloat()
            val scaleX = videoRatio / screenRatio
            if (scaleX >= 1f) {
                binding.videoLesson.scaleX = scaleX
            } else {
                binding.videoLesson.scaleY = 1f / scaleX
            }
            it.isLooping = true }
    }

    override fun initObserver() {
        vm.lesson.observe(viewLifecycleOwner){
            when(it){
                is DevState.Success -> {
                    binding.tvLessonTitle.text = it.data.name
                }
                is DevState.Failure -> {
                }
                is DevState.Loading -> {
                }
                else -> {}
            }
        }
        vm.video.observe(viewLifecycleOwner){
            binding.videoLesson.setVideoURI(it.toUri())
            binding.videoLesson.start()
        }
    }


}