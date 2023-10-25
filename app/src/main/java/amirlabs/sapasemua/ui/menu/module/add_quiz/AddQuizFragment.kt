package amirlabs.sapasemua.ui.menu.module.add_quiz

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentAddQuizBinding
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import androidx.navigation.findNavController

class AddQuizFragment : DevFragment<FragmentAddQuizBinding>(R.layout.fragment_add_quiz) {
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    override fun initData() {

    }

    override fun initUI() {

    }

    override fun initAction() {
        binding.btnBack.setOnClickListener {
            menuNavController?.popBackStack()
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
    }

    private fun isVerified(): Boolean {
        return binding.etAddSubject.editText?.error == null &&
                binding.etAddAnswer1.editText?.error == null &&
                binding.etAddAnswer2.editText?.error == null &&
                binding.etAddAnswer3.editText?.error == null &&
                binding.etAddAnswer4.editText?.error == null
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

    override fun initObserver() {

    }
}