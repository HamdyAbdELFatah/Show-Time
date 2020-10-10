package com.hamdy.showtime.ui.ui.person_details

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.PersonDetailsFragmentBinding
import kotlin.math.abs


class PersonDetailsFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    private lateinit var binding:PersonDetailsFragmentBinding
    private lateinit var viewModel: PersonDetailsViewModel
    private  val TAG = "PersonDetailsFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PersonDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =  ViewModelProvider(this).get(PersonDetailsViewModel::class.java)
        binding.appBarImage.load(R.drawable.test3)
        binding.appbar.addOnOffsetChangedListener(this)

    }
    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, offset: Int) {
        val toolBarHeight: Int = binding.toolbar.measuredHeight
        val appBarHeight: Int = appBarLayout!!.measuredHeight
        val appBarWidth: Int = appBarLayout.measuredWidth
        val maxScroll = appBarLayout.totalScrollRange
        var percentage: Float = abs(offset).toFloat() / maxScroll.toFloat()
        Log.d(TAG, "onOffsetChanged: offset $offset     ${appBarHeight+offset}  $toolBarHeight ")
        val density = Resources.getSystem().displayMetrics.density
//        view?.layoutParams?.height = appBarHeight+offset * density.toInt()
        binding.appBarImage.apply {
            if(appBarHeight+offset!=toolBarHeight||appBarHeight+offset!=appBarHeight){
                requestLayout()
            }else{
                layoutParams.height=appBarHeight+offset
                layoutParams.width=appBarWidth+offset
            }
        }


        when {
            offset == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    Log.d(TAG, "onOffsetChanged:  ${State.EXPANDED}")
                }
                mCurrentState = State.EXPANDED
            }
            abs(offset) >= appBarLayout.totalScrollRange -> {
                if (mCurrentState != State.COLLAPSED) {
                    Log.d(TAG, "onOffsetChanged:  ${State.COLLAPSED}")
                }
                mCurrentState = State.COLLAPSED
            }
            else -> {
                if (mCurrentState != State.IDLE) {
                    Log.d(TAG, "onOffsetChanged:  ${State.IDLE}")
                    binding.appBarImage.layoutParams.height=appBarHeight+offset+50
                    binding.appBarImage.layoutParams.width=appBarWidth+offset

                }
                mCurrentState = State.IDLE
            }
        }
    }

}