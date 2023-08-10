package com.kuit.couphone.ui.category.restaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kuit.couphone.*
import com.kuit.couphone.data.*
import com.kuit.couphone.databinding.FragmentCategoryBinding
import com.kuit.couphone.ui.home.HomeFragment
import retrofit2.Call
import retrofit2.Response

class CategoryRestaurantFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    var adapter : BaseItemAdapter?= null
    var storeList = ArrayList<BrandResult>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)


        fetchBrandData(1)
        binding.button1.setOnClickListener {
            fetchBrandData(1) // Default (sorting 1)
        }

        binding.button2.setOnClickListener {
            fetchBrandData(2) // sorting 2
            adapter?.notifyDataSetChanged()
        }

        binding.button3.setOnClickListener {
            fetchBrandData(3) // sorting 3
            adapter?.notifyDataSetChanged()
        }

        binding.backIv.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commit()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryTv.text = "'식당'"
    }
    private fun fetchBrandData(sortedBy: Int) {
        val service =  getRetrofit().create(ApiInterface::class.java)
        Log.d("token", "Bearer $user_token")
        service.getBrand("Bearer $user_token",5,null, sortedBy)
            .enqueue( object : retrofit2.Callback<BrandResponse>{
                override fun onResponse(
                    call: Call<BrandResponse>,
                    response: Response<BrandResponse>
                ) {
                    if(response.isSuccessful) {
                        val resp = response.body()

                        Log.d("BrandResponse", resp.toString())
                        storeList.clear()
                        storeList = resp!!.result as ArrayList<BrandResult>
                        adapter = BaseItemAdapter(storeList)
                        binding.categoryListRv.adapter = adapter
                        binding.categoryListRv.layoutManager = LinearLayoutManager(context)
                        adapter!!.setOnItemClickListener(object : BaseItemAdapter.OnItemClickListener{
                            override fun onItemClick(itemList: BrandResult) {
                                val intent = Intent(requireContext(), InformationActivity::class.java)
                                val dataJson = Gson().toJson(Information(itemList.name,itemList.createdDate,itemList.brandImageUrl,itemList.stampCount))
                                intent.putExtra("Data", dataJson)
                                startActivity(intent)
                            }
                        })
                        adapter!!.notifyDataSetChanged()
                    }
                    else{
                        Log.d("BrandResponse", response.toString())
                    }
                }

                override fun onFailure(call: Call<BrandResponse>, t: Throwable) {
                    Log.d("BrandResponse",t.message.toString())
                }

            })
    }


    /*private fun initDummyData() {
        storeList.add(StoreInfo("test1", "test1111111"))
        storeList.add(StoreInfo("test2", "test22222222222"))
        storeList.add(StoreInfo("test3", "test333333333333333333"))
        storeList.add(StoreInfo("test4", "test4444444444444444444"))
    }*/
}