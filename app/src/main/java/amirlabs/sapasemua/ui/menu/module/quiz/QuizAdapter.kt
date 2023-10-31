package amirlabs.sapasemua.ui.menu.module.quiz

import amirlabs.sapasemua.R
import amirlabs.sapasemua.data.model.Quiz
import amirlabs.sapasemua.databinding.ItemFragmentQuizBinding
import amirlabs.sapasemua.ui.menu.module.list_quiz.ListQuizAdapter
import android.annotation.SuppressLint
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class QuizAdapter(
    private val onItemClick: (Quiz) -> Unit,
    private val onOption1Clicked: (Quiz, Int) -> Unit,
    private val onOption2Clicked: (Quiz, Int) -> Unit,
    private val onOption3Clicked: (Quiz, Int) -> Unit,
    private val onOption4Clicked: (Quiz, Int) -> Unit
) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {
    val listData = ArrayList<Quiz>()
    private val listVideo = ArrayList<File>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFragmentQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position], position)
    }

    override fun getItemCount(): Int = listData.size

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.binding.videoQuiz.start()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.binding.videoQuiz.pause()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Quiz>) {
        listVideo.clear()
        listVideo.addAll(list.map { base64ToVideo(it.attachment ?: "", it.id ?: "") })

        list.forEach { it.attachment = null }
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    private fun base64ToVideo(base64: String, quizId: String): File {
        val decodedBytes: ByteArray = Base64.decode(base64, Base64.DEFAULT)
        val dir = File.createTempFile(quizId, ".mp4")
        try {
            val out = FileOutputStream(dir)
            out.write(decodedBytes)
            out.close()
        } catch (e: Exception) {
            Log.e("Error", e.toString())
        }
        return dir
    }

    inner class ViewHolder(val binding: ItemFragmentQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Quiz, position: Int) {
            with(binding) {
                tvQuestion.text = data.question
                tvOption1.text = data.option1
                tvOption2.text = data.option2
                tvOption3.text = data.option3
                tvOption4.text = data.option4
                videoQuiz.setOnPreparedListener {
                    val videoRatio = it.videoWidth / it.videoHeight.toFloat()
                    val screenRatio = videoQuiz.width / videoQuiz.height.toFloat()
                    val scaleX = videoRatio / screenRatio
                    if (scaleX >= 1f) {
                        videoQuiz.scaleX = scaleX
                    } else {
                        videoQuiz.scaleY = 1f / scaleX
                    }
                    it.isLooping = true
                }
                binding.root.setOnClickListener { onItemClick(data) }
                binding.option1.setOnClickListener {
                    onOption1Clicked(data, position)
                    option1.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.primary,
                            null
                        )
                    )
                    option2.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option3.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option4.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                }
                binding.option2.setOnClickListener {
                    onOption2Clicked(data, position)
                    option1.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option2.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.primary,
                            null
                        )
                    )
                    option3.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option4.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                }
                binding.option3.setOnClickListener {
                    onOption3Clicked(data, position)
                    option1.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option2.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option3.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.primary,
                            null
                        )
                    )
                    option4.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                }
                binding.option4.setOnClickListener {
                    onOption4Clicked(data, position)
                    option1.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option2.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option3.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.white,
                            null
                        )
                    )
                    option4.setCardBackgroundColor(
                        ResourcesCompat.getColor(
                            binding.root.resources,
                            R.color.primary,
                            null
                        )
                    )
                }
                videoQuiz.setVideoURI(listVideo[position].toUri())
                videoQuiz.start()
            }
        }
    }

}