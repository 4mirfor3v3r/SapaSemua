package amirlabs.sapasemua.ui.menu.module.add_quiz

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentAddQuizBinding
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.getViewModel
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class AddQuizFragment : DevFragment<FragmentAddQuizBinding>(R.layout.fragment_add_quiz) {
    override val vm: AddQuizViewModel by getViewModel()
    private val args: AddQuizFragmentArgs by navArgs()
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private lateinit var pickVideo: ActivityResultLauncher<PickVisualMediaRequest>
    private var video: File? = null
    override fun initData() {
        if (!checkPermissions()) {
            requestPermissions()
        }

        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        pickVideo = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
//                context?.contentResolver?.takePersistableUriPermission(uri, flag)
                lifecycleScope.launch {
                    val f = File(requireContext().cacheDir, System.currentTimeMillis().toString())
                    withContext(Dispatchers.IO){
                        f.createNewFile()
                        val inputStream = activity?.contentResolver?.openInputStream(uri)
                        var fos: FileOutputStream? = null
                        try {
                            fos = FileOutputStream(f)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        try {
                            fos?.write(inputStream?.readBytes())
                            fos?.flush()
                            fos?.close()
                            inputStream?.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        video = f
                    }
                }
                binding.videoQuiz.setVideoURI(uri)
                binding.btnPlayVideo.visibility = View.VISIBLE
                binding.btnSubmit.isEnabled = isVerified()
            }
        }
    }

    override fun initUI() {
        binding.rgCorrectAnswer.check(R.id.rbCorrect1)
        verifyQuestion()
        verifyOption1()
        verifyOption2()
        verifyOption3()
        verifyOption4()
        binding.btnSubmit.isEnabled = isVerified()
    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuNavController?.popBackStack()
        }
        binding.videoQuiz.setOnClickListener {
            pickVideo.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }
        binding.btnPlayVideo.setOnClickListener {
            binding.videoQuiz.start()
        }
        binding.etAddSubject.editText?.doAfterTextChanged {
            verifyQuestion()
            binding.btnSubmit.isEnabled = isVerified()
        }
        binding.etAddAnswer1.editText?.doAfterTextChanged {
            verifyOption1()
            binding.btnSubmit.isEnabled = isVerified()
        }
        binding.etAddAnswer2.editText?.doAfterTextChanged {
            verifyOption2()
            binding.btnSubmit.isEnabled = isVerified()
        }
        binding.etAddAnswer3.editText?.doAfterTextChanged {
            verifyOption3()
            binding.btnSubmit.isEnabled = isVerified()
        }
        binding.etAddAnswer4.editText?.doAfterTextChanged {
            verifyOption4()
            binding.btnSubmit.isEnabled = isVerified()
        }
        binding.rgCorrectAnswer.setOnCheckedChangeListener { _, _ ->
            binding.btnSubmit.isEnabled = isVerified()
        }
        binding.btnSubmit.setOnClickListener {
            val question = binding.etAddSubject.editText?.text.toString()
            val answer = when {
                binding.rbCorrect1.isChecked -> binding.etAddAnswer1.editText?.text.toString()
                binding.rbCorrect2.isChecked -> binding.etAddAnswer2.editText?.text.toString()
                binding.rbCorrect3.isChecked -> binding.etAddAnswer3.editText?.text.toString()
                binding.rbCorrect4.isChecked -> binding.etAddAnswer4.editText?.text.toString()
                else -> ""
            }
            val option1 = binding.etAddAnswer1.editText?.text.toString()
            val option2 = binding.etAddAnswer2.editText?.text.toString()
            val option3 = binding.etAddAnswer3.editText?.text.toString()
            val option4 = binding.etAddAnswer4.editText?.text.toString()
            vm.createQuiz(args.moduleId, question, answer, option1, option2, option3, option4, video!!)
        }
    }


    override fun initObserver() {
        vm.createResult.observe(viewLifecycleOwner) {
            when (it) {
                is DevState.Loading -> {
                    binding.etAddSubject.isEnabled = false
                    binding.etAddAnswer1.isEnabled = false
                    binding.etAddAnswer2.isEnabled = false
                    binding.etAddAnswer3.isEnabled = false
                    binding.etAddAnswer4.isEnabled = false
                    binding.btnSubmit.isClickable = false
                    binding.rgCorrectAnswer.isClickable = false
                    binding.btnSubmit.startAnimation()
                }

                is DevState.Success -> {
                    binding.etAddSubject.isEnabled = true
                    binding.etAddAnswer1.isEnabled = true
                    binding.etAddAnswer2.isEnabled = true
                    binding.etAddAnswer3.isEnabled = true
                    binding.etAddAnswer4.isEnabled = true
                    binding.rgCorrectAnswer.isClickable = true

                    binding.btnSubmit.revertAnimation {
                        binding.btnSubmit.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.sample, null)
                        binding.btnSubmit.isClickable = true
                    }
                    menuNavController?.popBackStack()
                }

                is DevState.Failure -> {
                    binding.etAddSubject.isEnabled = true
                    binding.etAddAnswer1.isEnabled = true
                    binding.etAddAnswer2.isEnabled = true
                    binding.etAddAnswer3.isEnabled = true
                    binding.etAddAnswer4.isEnabled = true
                    binding.rgCorrectAnswer.isClickable = true

                    binding.btnSubmit.revertAnimation {
                        binding.btnSubmit.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.sample, null)
                        binding.btnSubmit.isClickable = true
                    }
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is DevState.Default -> {}
                is DevState.Empty -> {}
            }
        }
    }

    private fun isVerified(): Boolean {
        return binding.etAddSubject.editText?.error == null &&
                binding.etAddAnswer1.editText?.error == null &&
                binding.etAddAnswer2.editText?.error == null &&
                binding.etAddAnswer3.editText?.error == null &&
                binding.etAddAnswer4.editText?.error == null &&
                video != null
    }

    private fun verifyQuestion() {
        if (binding.etAddSubject.editText?.text.toString().isEmpty()) {
            binding.etAddSubject.error = "Pertanyaan tidak boleh kosong"
        } else {
            binding.etAddSubject.error = null
        }
    }

    private fun verifyOption1() {
        if (binding.etAddAnswer1.editText?.text.toString().isEmpty()) {
            binding.etAddAnswer1.error = "Opsi tidak boleh kosong"
        } else {
            binding.etAddAnswer1.error = null
        }
    }

    private fun verifyOption2() {
        if (binding.etAddAnswer2.editText?.text.toString().isEmpty()) {
            binding.etAddAnswer2.error = "Opsi tidak boleh kosong"
        } else {
            binding.etAddAnswer2.error = null
        }
    }
    private fun verifyOption3() {
        if (binding.etAddAnswer3.editText?.text.toString().isEmpty()) {
            binding.etAddAnswer3.error = "Opsi tidak boleh kosong"
        } else {
            binding.etAddAnswer3.error = null
        }
    }
    private fun verifyOption4() {
        if (binding.etAddAnswer4.editText?.text.toString().isEmpty()) {
            binding.etAddAnswer4.error = "Opsi tidak boleh kosong"
        } else {
            binding.etAddAnswer4.error = null
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED&&
            ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
        )
    }
}