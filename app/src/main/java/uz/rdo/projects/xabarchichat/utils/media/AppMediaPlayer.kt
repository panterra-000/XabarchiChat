package uz.rdo.projects.xabarchichat.utils.media

import android.media.MediaPlayer
import uz.rdo.projects.xabarchichat.utils.showToast

class AppMediaPlayer {
    companion object {
        private var mMediaPlayer = MediaPlayer()
        private lateinit var mPath: String

        fun playMyVoice() {
            try {
                mMediaPlayer.start()
            } catch (e: java.lang.Exception) {
                showToast(e.message.toString())
            }
        }

        fun pauseMyVoice() {
            try {
                mMediaPlayer.pause()
            } catch (e: java.lang.Exception) {
                showToast(e.message.toString())
            }
        }


        fun stopMyVoice() {
            try {
                mMediaPlayer.seekTo(0)
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }

        fun prepareMediaPlayer(path: String) {
            mMediaPlayer.reset()
            mPath = path
            mMediaPlayer.setDataSource(mPath)
            mMediaPlayer.prepare()
        }

        fun releaseMediaPlayer() {
            try {
                mMediaPlayer.release()
            } catch (e: Exception) {
                showToast(e.message.toString())
            }
        }


    }
}