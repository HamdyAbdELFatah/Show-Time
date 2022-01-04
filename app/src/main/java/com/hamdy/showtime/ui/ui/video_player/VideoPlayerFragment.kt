package com.hamdy.showtime.ui.ui.video_player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import com.hamdy.showtime.databinding.FragmentVideoPlayerBinding
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YtFile
import android.util.SparseArray
import android.view.WindowManager
import at.huber.youtubeExtractor.YouTubeExtractor
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.hamdy.showtime.ui.ui.MainActivity
import com.hamdy.showtime.ui.util.YouTubeBase



class VideoPlayerFragment : Fragment(), Player.Listener {
    private lateinit var youtubeLink: String
    private lateinit var binding: FragmentVideoPlayerBinding
    var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        youtubeLink = YouTubeBase + arguments?.get("url").toString()
        if(activity != null && activity is MainActivity){
            (activity as MainActivity?)?.getNav()?.visibility = View.GONE

        }

        initializePlayer()

    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.videoView.player = player

        object : YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {

                    val iTag = 137//tag of video 1080
                    val audioTag = 140 //tag m4a audio
                    // 720, 1080, 480
                    var videoUrl = ""
                    val iTags: List<Int> = listOf(22, 137, 18)
                    for (i in iTags) {
                        val ytFile = ytFiles.get(i)
                        if (ytFile != null) {
                            val downloadUrl = ytFile.url
                            if (downloadUrl != null && downloadUrl.isNotEmpty()) {
                                videoUrl = downloadUrl
                            }
                        }
                    }
                    if (videoUrl == "")
                        videoUrl = ytFiles[iTag].url
                    val audioUrl = ytFiles[audioTag].url
                    val audioSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(audioUrl))
                    val videoSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(videoUrl))
                    player?.setMediaSource(
                        MergingMediaSource(true, videoSource, audioSource), true
                    )
                    player?.prepare()
                    player?.playWhenReady = playWhenReady
                    player?.seekTo(currentWindow, playbackPosition)
                    player?.addListener(this@VideoPlayerFragment)
                }
            }

        }.extract(youtubeLink)

    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        if (playbackState == Player.STATE_READY) {
            binding.progressBar.visibility = View.INVISIBLE
        } else {
            binding.progressBar.visibility = View.VISIBLE
        }
    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24 || player == null) {
            initializePlayer()

        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()

        }
    }

    override fun onPause() {
//        if (Util.SDK_INT < 24)
            releasePlayer()
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        if(activity != null && activity is MainActivity){
            (activity as MainActivity?)?.getNav()?.visibility = View.VISIBLE

        }
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentMediaItemIndex
            player?.release()
            player = null
        }

    }
}