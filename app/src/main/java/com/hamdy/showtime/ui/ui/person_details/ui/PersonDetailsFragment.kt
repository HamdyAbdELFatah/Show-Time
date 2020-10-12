package com.hamdy.showtime.ui.ui.person_details.ui

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.PersonDetailsFragmentBinding
import com.hamdy.showtime.ui.ui.home.adapter.PopularAdapter
import com.hamdy.showtime.ui.ui.person_details.adapter.KnownMoviesAdapter
import kotlin.math.abs


class PersonDetailsFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    private lateinit var binding:PersonDetailsFragmentBinding
    private lateinit var viewModel: PersonDetailsViewModel
    private  val TAG = "PersonDetailsFragment"
    private var castNameTextSize =0f
    private var castNickTextSize =0f
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
        binding.appBarImage.load(R.drawable.test4)
        binding.appBarImage.requestLayout()
        binding.viewDark.requestLayout()
        castNameTextSize=binding.castName.textSize
        castNickTextSize=binding.castNickName.textSize
        binding.appbar.addOnOffsetChangedListener(this)
//        binding.toolbar.inflateMenu(R.menu.favorite_menu);
        val knownMoviesAdapter= KnownMoviesAdapter()
        binding.knownRecyclerView.adapter=knownMoviesAdapter


    }


    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }


    private var mCurrentState = State.IDLE

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, offset: Int) {
        val toolBarHeight: Int = binding.toolbar.measuredHeight
        val appBarHeight: Int = appBarLayout!!.measuredHeight
        val appBarWidth: Int = appBarLayout.measuredWidth
        val maxScroll = appBarLayout.totalScrollRange
        val percentage: Float = abs(offset).toFloat() / maxScroll.toFloat()
        Log.d(
            TAG,
            "onOffsetChanged: offset $offset     ${appBarHeight + offset}  $toolBarHeight  $maxScroll"
        )
        Log.d(TAG, "onOffsetChanged: maxScroll $maxScroll   percentage  $percentage")
        val density = Resources.getSystem().displayMetrics.density
//        view?.layoutParams?.height = appBarHeight+offset * density.toInt()
        val plus = 20
//        plus = if(percentage>0.5f) (30*density).toInt() else 0

        binding.appBarImage.apply {
            layoutParams.height= (appBarHeight+offset + percentage * plus * density).toInt()
            layoutParams.width= (appBarWidth+offset + percentage * plus * density).toInt()
        }
//        binding.viewDark.apply {
//            layoutParams.height= (appBarHeight+offset + percentage * plus * density).toInt()
//            layoutParams.width= (appBarHeight+offset + percentage * plus * density).toInt()
//        }

        binding.castName.textSize=castNameTextSize-(percentage*23)
        binding.castNickName.textSize=castNickTextSize-(percentage*23)

        val paramsImage = binding.appBarImage.layoutParams as (ConstraintLayout.LayoutParams)
        paramsImage.horizontalBias = 1-(1.0f - percentage)/2
        paramsImage.marginEnd = (50 * percentage * density).toInt()
        binding.appBarImage.layoutParams = paramsImage
//        binding.knownText.alpha=1-percentage
//        binding.knownRecyclerView.alpha=1-percentage
//        binding.knownText.visibility=if(1-percentage<0.1) View.GONE else View.VISIBLE
//        binding.knownRecyclerView.visibility=if(1-percentage<0.1) View.GONE else View.VISIBLE

//        val paramsViewDark = binding.viewDark.layoutParams as (ConstraintLayout.LayoutParams)
//        paramsViewDark.horizontalBias = 1-(1.0f - percentage)/2
//        paramsViewDark.marginEnd = (50 * percentage * density).toInt()
//        binding.viewDark.layoutParams = paramsViewDark

        val paramsCastName = binding.castName.layoutParams as (ConstraintLayout.LayoutParams)
        paramsCastName.horizontalBias = (1.0f - percentage)/2
        binding.castName.layoutParams = paramsCastName

        when {
            offset == 0 -> {
                if (mCurrentState != State.EXPANDED) {
                    Log.d(TAG, "onOffsetChanged:  ${State.EXPANDED}")
                }
                mCurrentState = State.EXPANDED
//                ObjectAnimator.ofFloat(binding.castName, "translationX", -50f).apply {
//                    duration = 500
//                    start()
//                }
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
                }
                mCurrentState = State.IDLE
            }
        }





    }

}