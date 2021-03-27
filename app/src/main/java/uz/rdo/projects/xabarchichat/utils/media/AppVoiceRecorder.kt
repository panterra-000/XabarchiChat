package uz.rdo.projects.xabarchichat.utils.media

import android.media.MediaRecorder
import uz.rdo.projects.xabarchichat.utils.APP_ACTIVITY
import uz.rdo.projects.xabarchichat.utils.DoubleBlock
import uz.rdo.projects.xabarchichat.utils.showToast
import java.io.File

class AppVoiceRecorder {

    companion object {

        private val mMediaRecorder = MediaRecorder()
        private lateinit var mFile: File
        private lateinit var mMessageKey: String

        fun startRecord(messageKey: String) {
            try {
                mMessageKey = messageKey
                createFileForRecord()
                prepareMediaRecorder()
                mMediaRecorder.start()
            } catch (e: Exception) {
                showToast(e.message.toString())
            }

        }

        private fun prepareMediaRecorder() {
            mMediaRecorder.reset()
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            mMediaRecorder.setOutputFile(mFile.absolutePath)
            mMediaRecorder.prepare()

        }

        private fun createFileForRecord() {
            mFile = File(APP_ACTIVITY.filesDir, mMessageKey)
            mFile.createNewFile()
        }

        fun stopRecord(onSuccess: DoubleBlock<File, String>) {
            try {
                mMediaRecorder.stop()
                onSuccess.invoke(mFile, mMessageKey)
            } catch (e: Exception) {
                showToast(e.message.toString())
            }

        }

        fun releaseRecorder() {
            try {
                mMediaRecorder.release()
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }


    }
}