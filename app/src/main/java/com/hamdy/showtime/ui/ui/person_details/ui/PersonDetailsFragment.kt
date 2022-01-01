package com.hamdy.showtime.ui.ui.person_details.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.hamdy.showtime.R
import com.hamdy.showtime.databinding.PersonDetailsFragmentBinding
import com.hamdy.showtime.ui.ui.person_details.adapter.KnownMoviesAdapter
import com.hamdy.showtime.ui.ui.person_details.adapter.PersonPhotosAdapter
import com.hamdy.showtime.ui.util.ImageUrlBase
import kotlin.math.abs


class PersonDetailsFragment : Fragment(), AppBarLayout.OnOffsetChangedListener {
    private lateinit var binding: PersonDetailsFragmentBinding
    private lateinit var viewModel: PersonDetailsViewModel
    private val TAG = "PersonDetailsFragment"
    private var castNameTextSize = 0f
    private var castNickTextSize = 0f
    private var sharedIdValue = false
    private var favorite = false
    private var personId = 0
    private var stoppercentage = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        viewModel = ViewModelProvider(this)[PersonDetailsViewModel::class.java]
        personId = arguments?.getInt("id")!!
        viewModel.getPersonDetails(personId)
        viewModel.getPersonImage(personId)
        viewModel.getFavorite(personId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PersonDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posterPath = arguments?.getString("posterPath")
        binding.appBarImage.load(ImageUrlBase + posterPath)
        binding.appBarImage.requestLayout()
        binding.viewDark.requestLayout()
        castNameTextSize = binding.castName.textSize
        castNickTextSize = binding.castNickName.textSize
//        outRect.top = myContext.resources.getDimensionPixelSize(R.dimen._100sdp)

        binding.appbar.addOnOffsetChangedListener(this)

        binding.favoriteImage.setOnClickListener {
            val sharedPreferences: SharedPreferences =
                context?.getSharedPreferences("ShowTimeAuth", Context.MODE_PRIVATE)!!
            sharedIdValue = sharedPreferences.getBoolean("login", false)
            if (sharedIdValue) {
                viewModel.setFavorite(personId, posterPath!!, favorite)
                if (favorite)
                    binding.favoriteImage.setImageResource(R.drawable.ic_favorite)
                else
                    binding.favoriteImage.setImageResource(R.drawable.ic_favorite_choosed)
                favorite = !favorite
                it.findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "favoriteBack",
                    personId.toString() to favorite
                )
            } else {
                it.findNavController()
                    .navigate(R.id.action_navigation_person_to_loginFragment, null, null, null)
            }
        }

        val knownMoviesAdapter = KnownMoviesAdapter()
        binding.knownRecyclerView.adapter = knownMoviesAdapter
        val container = binding.bottomSheetContainer
        val personPhotosAdapter = PersonPhotosAdapter()
        container.photoRecyclerView.adapter = personPhotosAdapter
        viewModel.favorite.observe(viewLifecycleOwner, {
            favorite = it
            if (it)
                binding.favoriteImage.setImageResource(R.drawable.ic_favorite_choosed)
            else
                binding.favoriteImage.setImageResource(R.drawable.ic_favorite)
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "favoriteBack",
                personId.toString() to it
            )

        })

        viewModel.personDetails.observe(viewLifecycleOwner, {
            container.overviewText.text = it.biography
            var isTextViewClicked = true
            if (container.overviewText.lineCount > 4) {
                container.seeMoreImage.visibility = View.VISIBLE
            }
            container.seeMoreImage.setOnClickListener {
                isTextViewClicked = if (isTextViewClicked) {
                    container.overviewText.maxLines = Integer.MAX_VALUE
                    container.seeMoreImage.setImageResource(R.drawable.ic_arrow_up)
                    false
                } else {
                    container.overviewText.maxLines = 4
                    container.seeMoreImage.setImageResource(R.drawable.ic_arrow_down)
                    true
                }
            }
            container.bornDate.text = it.birthday
            container.bornPlace.text = it.placeOfBirth
            val name = it.name?.split(" ")
            binding.castName.text = name?.get(0) ?: ""
            if (name?.size ?: 0 > 1)
                binding.castNickName.text = name?.get(1) ?: ""

        })

        viewModel.listPersons.observe(viewLifecycleOwner, {
            personPhotosAdapter.setPerson(it)
        })

        viewModel.listKnown.observe(viewLifecycleOwner, {
            knownMoviesAdapter.setMovies(it)
        })
    }


    enum class State {
        EXPANDED, COLLAPSED, IDLE
    }

    private var mCurrentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, offset: Int) {
        // expand  offset   0     525  84  441
        //collapse offset -441     84  84  441
        val toolBarHeight: Int = binding.toolbar.measuredHeight
        val appBarHeight: Int = appBarLayout!!.measuredHeight
        val appBarWidth: Int = appBarLayout.measuredWidth
        val maxScroll = appBarLayout.totalScrollRange
        val percentage: Float = abs(offset).toFloat() / maxScroll.toFloat()
        Log.d(
            TAG,
            "onOffsetChanged: offset $offset     ${appBarHeight + offset}  $toolBarHeight  $maxScroll"
        )
//        Log.d(TAG, "onOffsetChanged: maxScroll $maxScroll   percentage  $percentage")
        val density = Resources.getSystem().displayMetrics.density

//        view?.layoutParams?.height = appBarHeight+offset * density.toInt()
        val plus = 20
//        plus = if(percentage>0.5f) (30*density).toInt() else 0

        binding.appBarImage.apply {
            layoutParams.height = (appBarHeight + offset + percentage * plus * density).toInt()
            layoutParams.width = (appBarWidth + offset + percentage * (plus + 5) * density).toInt()
        }
//        binding.viewDark.apply {
//            layoutParams.height= (appBarHeight+offset + percentage * plus * density).toInt()
//            layoutParams.width= (appBarHeight+offset + percentage * plus * density).toInt()
//        }
        if (binding.castName.measuredHeight > toolBarHeight / 2 || percentage < stoppercentage ) {
            binding.castName.textSize =
                castNameTextSize - (castNameTextSize * percentage)
            binding.castNickName.textSize =
                castNickTextSize - (percentage * (castNickTextSize))
            stoppercentage = percentage
        }else if (percentage < stoppercentage){

        }
//        Expand Dark 36.0 54.0
//        collap Dark 36.0 0.0
        Log.d(
            TAG,
            "onOffsetChanged: offset Dark" +
                    " $castNameTextSize ${(percentage * (castNameTextSize))}" +
                    " ${binding.castName.textSize}"
        )
        val paramsImage = binding.appBarImage.layoutParams as (ConstraintLayout.LayoutParams)
        paramsImage.horizontalBias = 1 - (1.0f - percentage) / 2
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
        paramsCastName.horizontalBias = (1.0f - percentage) / 2
        binding.castName.layoutParams = paramsCastName

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
                }
                mCurrentState = State.IDLE
            }
        }
    }
}