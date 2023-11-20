package amirlabs.sapasemua.ui.menu.module.lesson

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentLessonBinding
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.getViewModel
import android.net.Uri
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_ENDED
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

class LessonFragment : DevFragment<FragmentLessonBinding>(R.layout.fragment_lesson) {
    override val vm: LessonViewModel by getViewModel()
    private val args: LessonFragmentArgs by navArgs()
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }

    private var player: ExoPlayer?= null
    // Create a data source factory.
    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

    override fun initData() {

    }

    override fun initUI() {
//        initPlayer()
    }

    override fun initAction() {
        vm.getLessonById(args.lessonId)
        binding.btnBack.setOnClickListener {
            menuNavController?.popBackStack()
        }
    }

    override fun initObserver() {
        vm.lesson.observe(viewLifecycleOwner){
            when(it){
                is DevState.Success -> {
                    binding.tvLessonTitle.text = it.data.name
                    initPlayer(it.data.video?:"")
                }
                is DevState.Failure -> {
                }
                is DevState.Loading -> {
                }
                else -> {}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    override fun onResume() {
        super.onResume()
        play()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    @OptIn(UnstableApi::class) private fun initPlayer(mediaUrl: String){
        player = ExoPlayer.Builder(requireContext())
            .build()
            .apply {
                setMediaSource(getProgressiveMediaSource(mediaUrl))
                prepare()
                addListener(playerListener)
            }
    }
    @OptIn(UnstableApi::class) private fun getProgressiveMediaSource(mediaUrl: String): MediaSource {
        // Create a Regular media source pointing to a playlist uri.
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(mediaUrl)))
    }
    private val playerListener = object: Player.Listener {
        @OptIn(UnstableApi::class) override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            when(playbackState){
                STATE_ENDED -> restartPlayer()
                STATE_READY -> {
                    binding.videoLesson.player = player
                    binding.videoLesson.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    play()
                }
            }
        }
    }
    private fun releasePlayer(){
        player?.apply {
            playWhenReady = false
            release()
        }
        player = null
    }
    private fun pause(){
        player?.playWhenReady = false
    }
    private fun play(){
        player?.playWhenReady = true
    }
    private fun restartPlayer(){
        player?.seekTo(0)
        player?.playWhenReady = true
    }

}