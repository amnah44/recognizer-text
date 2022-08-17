package com.amnah.recog_txt.data

import android.graphics.Bitmap
import android.util.Log
import com.amnah.recog_txt.R
import com.amnah.recog_txt.databinding.ItemPhotoBinding
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText

class DetectText {

    fun detectTextFromImage(imageBitmap: Bitmap, binding: ItemPhotoBinding?) {
        val firebaseImage = FirebaseVisionImage.fromBitmap(imageBitmap)
        val firebaseVisionTextDetector =
            FirebaseVision.getInstance().visionTextDetector
        firebaseVisionTextDetector.detectInImage(firebaseImage).addOnSuccessListener { firebaseVisionText ->

            binding?.let { displayTextFromImage(firebaseVisionText, it) }

        }.addOnFailureListener {
            Log.i("onFailure", it.message.toString())
        }
    }

    private fun displayTextFromImage(
        firebaseVisionText: FirebaseVisionText,
        binding: ItemPhotoBinding
    ) {

        val blockList: List<FirebaseVisionText.Block> = firebaseVisionText.blocks

        if (blockList.isEmpty()) {
            Log.i("tag", "Empty list")
        } else {
            firebaseVisionText.blocks.forEach { block ->
                val text = block.text
                binding.textRecognizer.text = text
            }
        }
    }
}