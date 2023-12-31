package com.kuit.couphone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kuit.couphone.data.SearchRoomDB.LocalSearchDB
import com.kuit.couphone.data.SearchRoomDB.LocalSearchEntity
import com.kuit.couphone.databinding.FragmentSearchBinding
import java.util.*
import kotlin.collections.ArrayList

class SearchMapFragment : Fragment() {
    private lateinit var searchItemList: ArrayList<LocalSearchEntity>
    private lateinit var filteredList: ArrayList<LocalSearchEntity>
    var adapter : SearchAdapter?= null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var localSearchDB: LocalSearchDB
    lateinit var binding: FragmentSearchBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        filteredList = ArrayList()
        searchItemList = ArrayList()
        linearLayoutManager = LinearLayoutManager(requireContext())
        localSearchDB = LocalSearchDB.getInstance(requireContext())!!
        searchItemList = localSearchDB.SearchKeywordDAO().getKeyWordList("temp",2) as ArrayList<LocalSearchEntity>
        adapter = SearchAdapter(searchItemList)
        binding.recyclerViewList.adapter = adapter
        binding.recyclerViewList.layoutManager = LinearLayoutManager(context)
        binding.backIv.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MyLocationFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
        Log.d("dbupdateeeeeeeeeee",searchItemList.toString())
        adapter!!.setOnItemClickListener(object : SearchAdapter.OnItemClickListener{
            override fun onItemClick(keyword: LocalSearchEntity) {
                val bundle = Bundle()
                bundle.putString("key", keyword.keyword)

                val passBundleBFragment = MyLocationFragment()
                passBundleBFragment.arguments = bundle
                parentFragmentManager.beginTransaction().replace(R.id.main_frm, passBundleBFragment).commit()
            }

            override fun onDeleteClick(keyword: LocalSearchEntity) {

            }

        })
        binding.submitBtn.setOnClickListener {
            //유효성검사
            val localDao = LocalSearchDB.getInstance(requireContext())!!.SearchKeywordDAO()
            if(localDao.getresultkeyword(binding.searchEt.text.toString())==null) {
                localDao.insertSearchKeyword(
                    LocalSearchEntity(
                        2,
                        localDao.getCount(),
                        "temp",
                        binding.searchEt.text.toString()
                    )
                )
            }
            //검색화면 이동
            val bundle = Bundle()
            bundle.putString("key", binding.searchEt.text.toString())
            val passBundleBFragment = MyLocationFragment()
            passBundleBFragment.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, passBundleBFragment).commit()
            //검색결과 api 요청 받은 list들 리사이클러뷰로 띄우기, 지도에 마커 찍기


            Log.d("dbupdateeeeeeeeeee","업데이트완룓ㄷㄷㄷㄷㄷㄷㄷㄷㄷ")
        }
        binding.searchEt.setOnEditorActionListener(getEditorActionListener(binding.submitBtn))
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
                val searchText = binding.searchEt.text.toString()
                if (searchText.isEmpty()) {
                    adapter!!.filterList(searchItemList)
                } else {
                    searchFilter(searchText)
                }
            }

            override fun afterTextChanged(editable: Editable?) {}

        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val homeFragment = com.kuit.couphone.ui.home.HomeFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, homeFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        })
        adapter!!.notifyDataSetChanged()
        return binding.root
    }

    private fun searchFilter(searchText: String) {
        filteredList.clear()

        if (searchText.isEmpty()) {
            filteredList.addAll(searchItemList)
        } else {
            for (item in searchItemList) {
                if (item.keyword.toLowerCase(Locale.getDefault()).contains(searchText.toLowerCase(
                        Locale.getDefault()))) {
                    filteredList.add(item)
                }
            }
        }

        adapter!!.filterList(filteredList)
    }
    fun getEditorActionListener(view: View): TextView.OnEditorActionListener { // 키보드에서 done(완료) 클릭 시 , 원하는 뷰 클릭되게 하는 메소드
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.callOnClick()
            }
            false
        }
    }
}