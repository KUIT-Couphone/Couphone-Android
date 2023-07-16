package com.kuit.couphone.ui.category.fashion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kuit.couphone.databinding.FragmentCategoryBinding

class CategoryFashionFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    private val categoyList = listOf<String>("이너웨어", "일반 의류", "스포츠웨어","액세사리","신발")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CategoryFashionVPAdapter(this)
        binding.searchListVp.adapter = adapter
        binding.categoryTv.text = "'패션'"
        TabLayoutMediator(binding.searchTb,binding.searchListVp){
                tab,pos ->
            tab.text = categoyList[pos]
        }.attach()
        setTabItemMargin(binding.searchTb,30)
    }
    private fun setTabItemMargin(tabLayout: TabLayout, marginEnd: Int = 20) {
        for (i in 0 until 3) {
            val tabs = tabLayout.getChildAt(0) as ViewGroup
            for (i in 0 until tabs.childCount) {
                val tab = tabs.getChildAt(i)
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                lp.marginEnd = marginEnd
                tab.layoutParams = lp
                tabLayout.requestLayout()
            }
        }
    }
}