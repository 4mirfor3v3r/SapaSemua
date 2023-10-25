package amirlabs.sapasemua.ui.menu.module.quiz

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentQuizBinding
import android.os.Environment
import android.util.Base64
import android.util.Log
import java.io.File
import java.io.FileOutputStream


class QuizFragment : DevFragment<FragmentQuizBinding>(R.layout.fragment_quiz) {
    override fun initData() {

    }

    override fun initUI() {
    }

    override fun initAction() {
    }

    override fun initObserver() {
    }

    private fun base64ToVideo(base64: String, quizId: String): File {
        val decodedBytes: ByteArray = Base64.decode(base64, Base64.DEFAULT)

        try {
            val out = FileOutputStream(
                Environment.getExternalStorageDirectory()
                    .toString() + "/video/$quizId.mp4"
            )
            out.write(decodedBytes)
            out.close()
        } catch (e: Exception) {
            // TODO: handle exception
            Log.e("Error", e.toString())
        }
        return File(Environment.getExternalStorageDirectory().toString() + "/$quizId.mp4")
    }
}