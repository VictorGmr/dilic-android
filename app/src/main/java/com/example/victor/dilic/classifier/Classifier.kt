package com.example.victor.dilic.classifier

import android.graphics.Bitmap
import java.util.*

interface Classifier {
    fun recognizeImage(bitmap: Bitmap): Result
}