package com.viled.feature_quiz

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.FrameLayout
import com.daasuu.camerarecorder.CameraRecordListener
import com.daasuu.camerarecorder.CameraRecorder
import com.daasuu.camerarecorder.CameraRecorderBuilder
import com.daasuu.camerarecorder.LensFacing
import com.viled.core.common.widget.SampleGLView
import java.io.File

class CameraLayer(
    val activity: Activity,
    private val cameraView: FrameLayout
) {

    private var sampleGLView: SampleGLView? = null
    private var cameraRecorder: CameraRecorder? = null
    private var filepath: String = ""
    private var lensFacing = LensFacing.FRONT
    private var cameraWidth = 1280
    private var cameraHeight = 720
    private var videoWidth = 720
    private var videoHeight = 720
    private var toggleClick = false

    private var isRecording: Boolean = false

    fun handleRecordingState() {
        if (isRecording) {
            cameraRecorder?.stop()
            isRecording = false
        } else {
            filepath = getVideoFilePath()
            cameraRecorder?.start(filepath)
            isRecording = true
        }
    }

    fun startRecording() {
        filepath = getVideoFilePath()
        cameraRecorder?.start(filepath)
        isRecording = true
    }

    fun stopRecording() {
        cameraRecorder?.stop()
        isRecording = false
    }

    fun setUpCamera() {
        setUpCameraView()
        cameraRecorder = CameraRecorderBuilder(activity, sampleGLView)
            .cameraRecordListener(object : CameraRecordListener {
                override fun onGetFlashSupport(flashSupport: Boolean) {}

                override fun onRecordComplete() {
                    exportMp4ToGallery(activity.applicationContext, filepath)
                }

                override fun onRecordStart() {}
                override fun onError(exception: Exception) {
                    Log.e("CameraRecorder", exception.toString())
                }

                override fun onCameraThreadFinish() {
                    if (toggleClick) {
                        activity.runOnUiThread { setUpCamera() }
                    }
                    toggleClick = false
                }
            })
            .videoSize(videoWidth, videoHeight)
            .cameraSize(cameraWidth, cameraHeight)
            .lensFacing(lensFacing)
            .build()
    }

    private fun setUpCameraView() {
        activity.runOnUiThread {
            val frameLayout: FrameLayout = cameraView
            frameLayout.removeAllViews()
            sampleGLView = null
            sampleGLView = SampleGLView(activity.applicationContext)
            sampleGLView?.setTouchListener { event, width, height ->
                if (cameraRecorder == null) return@setTouchListener
                cameraRecorder!!.changeManualFocusPoint(event.x, event.y, width, height)
            }
            frameLayout.addView(sampleGLView)
        }
    }

    fun releaseCamera() {
        if (sampleGLView != null) {
            sampleGLView?.onPause()
        }
        if (cameraRecorder != null) {
            cameraRecorder!!.stop()
            cameraRecorder!!.release()
            cameraRecorder = null
        }
        if (sampleGLView != null) {
            (cameraView as FrameLayout).removeView(sampleGLView)
            sampleGLView = null
        }
    }

    private fun getVideoFilePath(): String = getAndroidMoviesFolder().absolutePath

    private fun getAndroidMoviesFolder(): File =
        File(activity.externalMediaDirs.first(), "${System.currentTimeMillis()}.mp4")

    fun exportMp4ToGallery(context: Context, filePath: String) {
        val values = ContentValues(2)
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
        values.put(MediaStore.Video.Media.DATA, filePath)
        context.contentResolver.insert(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            values
        )
        context.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://$filePath")
            )
        )
    }
}