package com.hamdy.showtime.ui.ui.person_details.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
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
    private var stopOffset = -1000
    private var stopOffsetImage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        removeStatusBarTransparent()
        val posterPath = arguments?.getString("posterPath")
        binding.appBarImage.load(ImageUrlBase + posterPath)
        binding.appBarImage.requestLayout()
        binding.viewDark.requestLayout()
        castNameTextSize = binding.castName.textSize / resources.displayMetrics.scaledDensity
        castNickTextSize = binding.castNickName.textSize / resources.displayMetrics.scaledDensity

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


    override fun onOffsetChanged(appBarLayout: AppBarLayout?, offset: Int) {
        // expand  offset   0     525  84  441
        //collapse offset -441     84  84  441
        val toolBarHeight: Int = binding.toolbar.measuredHeight
        val appBarHeight: Int = appBarLayout!!.measuredHeight
        val appBarWidth: Int = appBarLayout.measuredWidth
        val maxScroll = appBarLayout.totalScrollRange

        val percentage: Float = abs(offset).toFloat() / maxScroll.toFloat()
//        Log.d(
//            TAG,
//            "onOffsetChanged: offset $offset     ${appBarHeight + offset}  $toolBarHeight  $maxScroll"
//        )
//        Log.d(TAG, "onOffsetChanged: maxScroll $maxScroll   percentage  $percentage")
        val density = Resources.getSystem().displayMetrics.density
        val plus = 20
        if (binding.appBarImage.width >= toolBarHeight || offset > stopOffsetImage) {
            binding.appBarImage.apply {
                layoutParams.height = (appBarHeight + offset + percentage * plus * density).toInt()
                layoutParams.width = (appBarWidth + offset + percentage * (plus) * density).toInt()
                stopOffsetImage = offset
            }
        }
//        binding.viewDark.apply {
//            layoutParams.height= (appBarHeight+offset + percentage * plus * density).toInt()
//            layoutParams.width= (appBarHeight+offset + percentage * plus * density).toInt()
//        }
        if ((binding.castNickName.measuredHeight >= toolBarHeight / 2
                    || ((appBarHeight + offset) - (toolBarHeight * 2)) > toolBarHeight)
        ) {
            binding.castName.textSize =
                castNameTextSize - (castNameTextSize * percentage)

            binding.castNickName.textSize =
                castNickTextSize - (castNickTextSize * percentage)

        }


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

    }


    private fun removeStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        }
        val root: ConstraintLayout? = activity?.findViewById(R.id.root)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                    topMargin = insets.top
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }

    private fun addStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.transparent)

        }
        WindowCompat.setDecorFitsSystemWindows(activity?.window!!, false)
        val root: ConstraintLayout? = activity?.findViewById(R.id.root)
        if (root != null) {
            ViewCompat.setOnApplyWindowInsetsListener(root) { view, windowInsets ->
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.layoutParams = (view.layoutParams as FrameLayout.LayoutParams).apply {
                    topMargin = 0
                    leftMargin = insets.left
                    bottomMargin = insets.bottom
                    rightMargin = insets.right
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }

    override fun onPause() {
        super.onPause()
        addStatusBarTransparent()
    }
}