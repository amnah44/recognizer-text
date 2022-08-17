package com.amnah.recog_txt.ui.fragment.camera

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.amnah.recog_txt.data.model.Item
import com.amnah.recog_txt.databinding.FragmentCameraBinding
import com.amnah.recog_txt.ui.fragment.base.BaseFragment
import com.amnah.recog_txt.util.Constants.TAG_CAMERA_FRAGMENT
import com.amnah.recog_txt.util.toastMessage
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.io.IOException
import java.util.*

class CameraFragment : BaseFragment<FragmentCameraBinding>() {

    override val LOG_TAG: String
        get() = TAG_CAMERA_FRAGMENT
    override val bindingInflater: (LayoutInflater) -> FragmentCameraBinding =
        FragmentCameraBinding::inflate

    private lateinit var _binding: FragmentCameraBinding
    private lateinit var _viewModel: CameraViewModel
    private lateinit var imageBitmap: Bitmap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCameraBinding.bind(view)
        _viewModel = ViewModelProvider(this)[CameraViewModel::class.java]

        _binding.button.setOnClickListener {
            savePhoto()
        }

        dispatchTakePictureIntent()

    }

    private fun savePhoto() {

        if (_binding.textDetected.text != "") {

            savePhotoToInternalStorage(UUID.randomUUID().toString(), imageBitmap)
            val item = Item(0,UUID.randomUUID().toString(), _binding.textDetected.text.toString())
            _viewModel.addItem(item)

        } else {
            context?.toastMessage("Take a picture please..")
        }

    }

    private fun savePhotoToInternalStorage(fileName: String, bmp: Bitmap): Boolean {
        return try {
            requireActivity().openFileOutput("$fileName.jpg", Context.MODE_APPEND).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                    throw IOException("couldn't save photo")
                } else {
                    context?.toastMessage("Photo is saved")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)

        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageBitmap = data?.extras?.get("data") as Bitmap
            _binding.imageView.setImageBitmap(imageBitmap)
            detectTextFromImage()
        }
    }

    private fun detectTextFromImage() {
        val firebaseImage = FirebaseVisionImage.fromBitmap(imageBitmap)
        val firebaseVisionTextDetector =
            FirebaseVision.getInstance().visionTextDetector
        firebaseVisionTextDetector.detectInImage(firebaseImage).addOnSuccessListener {

            displayTextFromImage(it)

        }.addOnFailureListener {
            log("onFailure")
        }
    }

    private fun displayTextFromImage(firebaseVisionText: FirebaseVisionText) {

        val blockList: List<FirebaseVisionText.Block> = firebaseVisionText.blocks

        if (blockList.isEmpty()) {
            log("Empty list")
        } else {
            firebaseVisionText.blocks.forEach { block ->
                val text = block.text
                _binding.textDetected.text = text
            }
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }


}