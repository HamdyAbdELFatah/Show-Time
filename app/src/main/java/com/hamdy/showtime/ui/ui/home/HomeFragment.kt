package com.hamdy.showtime.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.hamdy.showtime.R
import com.hamdy.showtime.ui.model.Teacher
import com.hamdy.showtime.ui.ui.home.adapter.TeacherAdapter
import com.hamdy.showtime.ui.util.CenterZoomLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var teachersList: ArrayList<Teacher>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setData()
        val helper: SnapHelper = LinearSnapHelper()
        trendingRecyclerview?.layoutManager =
            CenterZoomLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                true
            )
        trendingRecyclerview?.adapter = TeacherAdapter(context, teachersList)
        helper.attachToRecyclerView(trendingRecyclerview)
    }

    private fun setData() {
        teachersList = ArrayList()
        teachersList.add(
            Teacher(
                "حمدي",
                "",
                "مدرس رياضيات للمنهاج الوطني الأردني، بخبرة تتجاوز 24 سنة في التدريس، حاصل على شهادة البكالوريوس في العلوم الك…",
                "رياضيات"
            )
        )
        teachersList.add(
            Teacher(
                "احمد",
                "",
                "مدرس كمياء للمنهاج الوطني الأردني، بخبرة تتجاوز 24 سنة في التدريس، حاصل على شهادة البكالوريوس في العلوم الك…",
                "كمياء"
            )
        )
        teachersList.add(
            Teacher(
                "عمر",
                "",
                "مدرس لغة عربية للمنهاج الوطني الأردني، بخبرة تتجاوز 19 سنة في التدريس",
                "لغة عربية"
            )
        )
        teachersList.add(
            Teacher(
                "محمد",
                "",
                "مدرس لغة انجليزية للمنهاج الوطني الأردني، بخبرة تتجاوز 24 سنة في التدريس، حاصل على شهادة البكالوريوس في العلوم الك…",
                "لغة انجليزية"
            )
        )
        teachersList.add(
            Teacher(
                "انس",
                "",
                "مدرس احياء للمنهاج الوطني الأردني، بخبرة تتجاوز 24 سنة في التدريس، حاصل على شهادة البكالوريوس في العلوم الك…",
                "احياء"
            )
        )
    }
}