package com.example.foodnexus.ui.addDish

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.foodnexus.R
import com.example.foodnexus.databinding.DialogSelectImageBinding
import com.example.foodnexus.databinding.FragmentAddDishBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddDishFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddDishBinding
    private lateinit var viewModel: AddDishViewModel
    private var imagePath = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddDishBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(AddDishViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addImage.setOnClickListener {
            displaySelectImageDialog()
        }
        binding.addDishButton.setOnClickListener {
            if (validator()) {
                dismiss()
            }
        }
    }

    private fun validator(): Boolean {
        if (viewModel.isDataNotFilled(binding.textTitleEditText.text.toString())) {
            binding.textTitle.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textTitle.error = null
        }
        if (viewModel.isDataNotFilled(binding.textTypeEditText.text.toString())) {
            binding.textType.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textType.error = null
        }
        if (viewModel.isDataNotFilled(binding.textCategoryEditText.text.toString())) {
            binding.textCategory.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textCategory.error = null
        }
        if (viewModel.isDataNotFilled(binding.textIngredientsEditText.text.toString())) {
            binding.textIngredients.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textIngredients.error = null
        }
        if (viewModel.isDataNotFilled(binding.textTimeEditText.text.toString())) {
            binding.textTime.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textTime.error = null
        }
        if (viewModel.isDataNotFilled(binding.textDirectionsEditText.text.toString())) {
            binding.textDirections.error = getString(R.string.please_fill_this_field)
            return false
        } else {
            binding.textDirections.error = null
        }
        return true
    }

    private fun displaySelectImageDialog() {
        val dialog = context?.let { Dialog(it) }
        val binding: DialogSelectImageBinding = DialogSelectImageBinding.inflate(layoutInflater)
        dialog?.setContentView(binding.root)

        binding.cameraImageButton.setOnClickListener {
            Dexter.withContext(context).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CAMERA)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permisions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    displayGoToSettingsDialog()
                }
            }).onSameThread().check()
            dialog?.dismiss()
        }
        binding.galleryImageButton.setOnClickListener {
            Dexter.withContext(context).withPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : PermissionListener {

                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, STORAGE)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    Permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    displayGoToSettingsDialog()
                }

            }).onSameThread().check()
            dialog?.dismiss()
        }
        dialog?.show()
    }

    private fun displayGoToSettingsDialog() {
        AlertDialog.Builder(context)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", context?.packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA) {
                data?.extras?.let {
                    val thumbnail: Bitmap = data.extras!!.get("data") as Bitmap
                    Glide.with(this)
                        .load(thumbnail)
                        .centerCrop()
                        .into(binding.image)

                    imagePath = saveImageToStorage(thumbnail)
                }
            }
            if (requestCode == STORAGE) {
                data?.let {
                    val selectedImageURI = data.data
                    Glide.with(this)
                        .load(selectedImageURI)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                Log.e("TAG", "Error loading image", e)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                resource?.let {
                                    val bitmap = resource.toBitmap()
                                    imagePath = saveImageToStorage(bitmap)
                                }
                                return false
                            }
                        })
                        .into(binding.image)
                }
            }
        }
    }

    private fun saveImageToStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(activity?.applicationContext)

        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
    }

    companion object {
        private const val CAMERA = 1
        private const val STORAGE = 2

        private const val IMAGE_DIRECTORY = "FoodNexus"
    }
}