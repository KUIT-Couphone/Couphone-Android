package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kuit.couphone.data.ApiInterface
import com.kuit.couphone.data.UserBrandResponse
import com.kuit.couphone.data.getRetrofit
import com.kuit.couphone.data.user_token
import com.kuit.couphone.databinding.FragmentMyCouponBinding
import com.kuit.couphone.ui.settings.SettingsFragment
import retrofit2.Call
import retrofit2.Response

class MyCouponFragment : Fragment() {
    private lateinit var binding: FragmentMyCouponBinding
    private val couphone_store = arrayOfNulls<ImageView>(10)
    private val couphone_stamp = arrayOfNulls<TextView>(10)
    private val couphone_store2 = arrayOfNulls<ImageView>(10)
    private val couphone_stamp2 = arrayOfNulls<TextView>(10)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCouponBinding.inflate(inflater, container, false)

        couphone_store[0] = binding.recentCoupon1
        couphone_store[1] = binding.recentCoupon2
        couphone_store[2] = binding.recentCoupon3
        couphone_store[3] = binding.recentCoupon4
        couphone_store[4] = binding.recentCoupon5
        couphone_store[5] = binding.recentCoupon6
        couphone_store[6] = binding.recentCoupon7
        couphone_store[7] = binding.recentCoupon8
        couphone_store[8] = binding.recentCoupon9
        couphone_store[9] = binding.recentCoupon10
        couphone_store2[0] = binding.recentCoupon11
        couphone_store2[1] = binding.recentCoupon12
        couphone_store2[2] = binding.recentCoupon13
        couphone_store2[3] = binding.recentCoupon14
        couphone_store2[4] = binding.recentCoupon15
        couphone_store2[5] = binding.recentCoupon16
        couphone_store2[6] = binding.recentCoupon17
        couphone_store2[7] = binding.recentCoupon18
        couphone_store2[8] = binding.recentCoupon19
        couphone_store2[9] = binding.recentCoupon20


        couphone_stamp[0] = binding.recentCouponTv1
        couphone_stamp[1] = binding.recentCouponTv2
        couphone_stamp[2] = binding.recentCouponTv3
        couphone_stamp[3] = binding.recentCouponTv4
        couphone_stamp[4] = binding.recentCouponTv5
        couphone_stamp[5] = binding.recentCouponTv6
        couphone_stamp[6] = binding.recentCouponTv7
        couphone_stamp[7] = binding.recentCouponTv8
        couphone_stamp[8] = binding.recentCouponTv9
        couphone_stamp[9] = binding.recentCouponTv10
        couphone_stamp2[0] = binding.recentCouponTv11
        couphone_stamp2[1] = binding.recentCouponTv12
        couphone_stamp2[2] = binding.recentCouponTv13
        couphone_stamp2[3] = binding.recentCouponTv14
        couphone_stamp2[4] = binding.recentCouponTv15
        couphone_stamp2[5] = binding.recentCouponTv16
        couphone_stamp2[6] = binding.recentCouponTv17
        couphone_stamp2[7] = binding.recentCouponTv18
        couphone_stamp2[8] = binding.recentCouponTv19
        couphone_stamp2[9] = binding.recentCouponTv20
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previousArrow1.setOnClickListener {
            val intent = Intent(requireContext(), SettingsFragment::class.java)
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, SettingsFragment())
                .commit()
        }
        fetchStoreData()

    }

    private fun fetchStoreData() {
        val service = getRetrofit().create(ApiInterface::class.java)
        service.sortingBrand("Bearer $user_token")
            .enqueue(object : retrofit2.Callback<UserBrandResponse> {
                override fun onResponse(
                    call: Call<UserBrandResponse>,
                    response: Response<UserBrandResponse>
                ) {
                    if (response.isSuccessful) {
                        val storeData = response.body()
                        updateUIWithStoreData(storeData)
                    } else {
                        Log.d("StoreDataResponse", "API call unsuccessful: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserBrandResponse>, t: Throwable) {
                    Log.d("StoreDataResponse", "API call failed: ${t.message}")
                }
            })
    }

    private fun updateUIWithStoreData(storeData: UserBrandResponse?) {
        val storeList = storeData!!.result.brandInfoListByModifiedDate
        for (i in 0 until storeList.size) {
            Glide.with(requireContext())
                .load(storeData.result.brandInfoListByModifiedDate[i].brandInfo.brandImageUrl)
                .into(couphone_store[i]!!)
            couphone_stamp[i]!!.text =
                storeData.result.brandInfoListByModifiedDate[i].brandInfo.stampCount.toString() + "/10"
            Glide.with(requireContext())
                .load(storeData.result.brandInfoListByTotalCount[i].brandInfo.brandImageUrl)
                .into(couphone_store2[i]!!)
            couphone_stamp2[i]!!.text =
                storeData.result.brandInfoListByTotalCount[i].brandInfo.stampCount.toString() + "/10"
        }
        for (i in storeList.size..9) {
            couphone_stamp[i]!!.text = "0/10"
            couphone_stamp2[i]!!.text = "0/10"
        }

    }

}


