package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kuit.couphone.data.*
import com.kuit.couphone.databinding.FragmentSearchResultBinding
import com.kuit.couphone.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Response

class SearchResultFragment : Fragment() {
    lateinit var binding: FragmentSearchResultBinding
    var adapter: BaseItemAdapter? = null
    var storeList = ArrayList<BrandResult>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        var result = requireArguments().getString("key")
        fetchBrandData(result)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backIv.setOnClickListener {
            val homeFragment = com.kuit.couphone.ui.home.HomeFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, homeFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

    }

    private fun fetchBrandData(result: String?) {
        val service = getRetrofit().create(ApiInterface::class.java)
        Log.d("token", "Bearer $user_token")
        service.getBrand("Bearer $user_token", null, result, 1)
            .enqueue(object : retrofit2.Callback<BrandResponse> {
                override fun onResponse(
                    call: Call<BrandResponse>,
                    response: Response<BrandResponse>
                ) {
                    if (response.isSuccessful) {
                        val resp = response.body()
                        storeList.clear()
                        storeList = resp!!.result as ArrayList<BrandResult>
                        adapter = BaseItemAdapter(storeList)
                        binding.categoryListRv.adapter = adapter
                        binding.categoryListRv.layoutManager = LinearLayoutManager(context)
                        adapter!!.setOnItemClickListener(object :
                            BaseItemAdapter.OnItemClickListener {
                            override fun onItemClick(itemList: BrandResult) {
                                val intent = Intent(requireContext(), InformationActivity::class.java)
                                val dataJson = Gson().toJson(Information(itemList.name,itemList.createdDate,itemList.brandImageUrl,itemList.stampCount))
                                intent.putExtra("Data", dataJson)
                                startActivity(intent)
                            }
                        })
                        Log.d("BrandResponse", resp.toString())
                    } else {
                        Log.d("BrandResponse", response.toString())
                    }
                }

                override fun onFailure(call: Call<BrandResponse>, t: Throwable) {
                    Log.d("BrandResponse", t.message.toString())
                }

            })
    }


}