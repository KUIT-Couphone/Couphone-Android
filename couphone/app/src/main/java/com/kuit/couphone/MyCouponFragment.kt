package com.kuit.couphone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kuit.couphone.data.ApiInterface
import com.kuit.couphone.data.BrandInfo
import com.kuit.couphone.data.BrandInfoListItem
import com.kuit.couphone.data.ResultData
import com.kuit.couphone.data.StoreResponse
import com.kuit.couphone.data.UserBrandResponse
import com.kuit.couphone.data.getRetrofit
import com.kuit.couphone.data.user_token
import com.kuit.couphone.databinding.FragmentMyCouponBinding
import com.kuit.couphone.ui.settings.SettingsFragment
import retrofit2.Call
import retrofit2.Response

class MyCouponFragment : Fragment() {
    private lateinit var binding : FragmentMyCouponBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCouponBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.previousArrow1.setOnClickListener {
            val intent = Intent(requireContext(), SettingsFragment::class.java)
            parentFragmentManager.beginTransaction().replace(R.id.main_frm, SettingsFragment()).commit()
        }

        val service =  getRetrofit().create(ApiInterface::class.java)
        Log.d("token", "Bearer $user_token")
        service.sortingBrand("Bearer $user_token")
            .enqueue( object : retrofit2.Callback<UserBrandResponse>{
                override fun onResponse(
                    call: Call<UserBrandResponse>,
                    response: Response<UserBrandResponse>
                ) {
                    if(response.isSuccessful) {
                        val resp = response.body()

                        Log.d("UserBrandResponse", resp.toString())
                    }
                    else{
                        Log.d("UserBrandResponse", response.toString())
                    }
                }

                override fun onFailure(call: Call<UserBrandResponse>, t: Throwable) {
                    Log.d("UserBrandResponse",t.message.toString())
                }

            })

    }
}