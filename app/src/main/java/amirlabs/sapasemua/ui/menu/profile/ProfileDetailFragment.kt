package amirlabs.sapasemua.ui.menu.profile

import amirlabs.sapasemua.R
import amirlabs.sapasemua.base.DevFragment
import amirlabs.sapasemua.databinding.FragmentProfileDetailBinding
import amirlabs.sapasemua.utils.DevState
import amirlabs.sapasemua.utils.getViewModel
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class ProfileDetailFragment : DevFragment<FragmentProfileDetailBinding>(R.layout.fragment_profile_detail) {
    override val vm: ProfileViewModel by getViewModel()
    private val args: ProfileDetailFragmentArgs by navArgs()
    private val menuNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_menu) }
    private var image: File? = null
    private var updatedField: Map<String?, Any> = emptyMap()
    private lateinit var pickImage: ActivityResultLauncher<PickVisualMediaRequest>

    override fun initData() {
        if (!checkPermissions()) {
            requestPermissions()
        }
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                context?.contentResolver?.takePersistableUriPermission(uri, flag)
                lifecycleScope.launch {
                    val f = File(requireContext().cacheDir, System.currentTimeMillis().toString())
                    withContext(Dispatchers.IO){
                        val bitmap = getPickedImage(uri)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        f.createNewFile()
                        var fos: FileOutputStream? = null
                        try {
                            fos = FileOutputStream(f)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        try {
                            fos?.write(stream.toByteArray())
                            fos?.flush()
                            fos?.close()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                        image = f
                        withContext(Dispatchers.Main){
                            binding.btnEditProfile.isEnabled = isVerified()
                        }
                    }
                }
                binding.ivAvatar.setImageURI(uri)
            }
        }
    }

    override fun initUI() {

    }

    override fun initAction() {
        vm.getProfileDetail(args.userId)
        binding.btnBack.setOnClickListener {
            menuNavController?.popBackStack()
        }
        binding.etName.editText?.doAfterTextChanged {
            updatedField.plus(Pair("name", it.toString()))
            binding.btnEditProfile.isEnabled = isVerified()
        }
        binding.etEmail.editText?.doAfterTextChanged {
            updatedField.plus(Pair("email", it.toString()))
            binding.btnEditProfile.isEnabled = isVerified()
        }
        binding.etDomicile.editText?.doAfterTextChanged {
            updatedField.plus(Pair("domicile", it.toString()))
            binding.btnEditProfile.isEnabled = isVerified()
        }
        binding.etBio.editText?.doAfterTextChanged {
            updatedField.plus(Pair("bio", it.toString()))
            binding.btnEditProfile.isEnabled = isVerified()
        }
        binding.ivAvatar.setOnClickListener {
            pickImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnEditProfile.setOnClickListener {
            vm.updateProfile(
                args.userId,
                updatedField,
                image
            )
        }
    }

    override fun initObserver() {
        vm.profile.observe(viewLifecycleOwner){
            when(it){
                is DevState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
                }
                is DevState.Success -> {
                    updatedField = emptyMap()
                    image = null
                    binding.btnEditProfile.isEnabled = isVerified()
                    binding.etName.editText?.setText(it.data.name)
                    binding.etEmail.editText?.setText(it.data.email)
                    binding.etPhone.editText?.setText(it.data.role)
                    binding.etDomicile.editText?.setText(it.data.domicile)
                    binding.etBio.editText?.setText(it.data.bio)

                    if (it.data.avatar != null) {
                        val image: ByteArray = Base64.decode(it.data.avatar, Base64.DEFAULT)
                        Glide.with(binding.root.context)
                            .asBitmap()
                            .load(image)
                            .into(binding.ivAvatar)
                    }
                }
                is DevState.Failure -> {
//                    binding.progressBar.visibility = View.GONE
//                    binding.tvName.text = it.message
                }
                else ->{}
            }
        }
    }


    private fun isVerified(): Boolean {
        return updatedField.isNotEmpty() || image != null
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

    @Suppress("DEPRECATION")
    private fun getPickedImage(selectedPhotoUri: Uri): Bitmap {
        val bitmap = when {
            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                activity?.contentResolver,
                selectedPhotoUri
            )
            else -> {
                val source =
                    ImageDecoder.createSource(requireActivity().contentResolver, selectedPhotoUri)
                ImageDecoder.decodeBitmap(source)
            }
        }
        return bitmap
    }

}