package com.sport.utilities

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer

/**
 * User: bizehao
 * Date: 2019-05-02
 * Time: 上午8:47
 * Description: 交替播放两种不同的声音，对应俯卧撑的 向下 向上
 */
class MyMediaPlayer(val context: Context, val down: Int, val up: Int) {

    private val mediaPlayer = MediaPlayer()

    init {
        mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());

        //声音装载完毕
        mediaPlayer.setOnPreparedListener {
            it.start()
        }

        //声音播放完毕
        mediaPlayer.setOnCompletionListener {
            it.stop()
        }
    }

    /**
     * 播放向下的声音
     */
    fun playSoundOfDown() {
        mediaPlayer.reset()
        val afd = context.resources.openRawResourceFd(down)
        mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaPlayer.prepareAsync()
    }

    /**
     * 播放向上的声音
     */
    fun playSoundOfUp() {
        mediaPlayer.reset()
        val afd = context.resources.openRawResourceFd(up)
        mediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        mediaPlayer.prepareAsync()
    }

    /**
     * 销毁
     */
    fun destroy(){
        mediaPlayer.release()
    }
}