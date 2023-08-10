package com.kuit.couphone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kuit.couphone.data.Information
import com.kuit.couphone.databinding.ActivityInfomationBinding
import com.kuit.couphone.databinding.ActivitySplashBinding
import java.text.SimpleDateFormat

class InformationActivity: AppCompatActivity() {
    lateinit var binding: ActivityInfomationBinding
    private val coupons = arrayOfNulls<ImageView>(10)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        coupons[0] = binding.couphoneNumber1
        coupons[1] = binding.couphoneNumber2
        coupons[2] = binding.couphoneNumber3
        coupons[3] = binding.couphoneNumber4
        coupons[4] = binding.couphoneNumber5
        coupons[5] = binding.couphoneNumber6
        coupons[6] = binding.couphoneNumber7
        coupons[7] = binding.couphoneNumber8
        coupons[8] = binding.couphoneNumber9
        coupons[9] = binding.couphoneNumber10
        var data =
            Gson().fromJson(intent.getStringExtra("Data"), Information::class.java)
        if(data.createdDate == null){
            binding.couphoneIssueddateUpdateTv.text = "아직 방문 하지 않았습니다!"
            binding.ddayFlIv.visibility = View.GONE
        }
        else{
            var sf = SimpleDateFormat("yyyy-MM-dd")
            var date = data.createdDate?.let { sf.parse(it) }
            binding.couphoneIssueddateUpdateTv.text = date.toString()
        }
        stampOn(data.stampCount)
        binding.cafeNameTv.text = data.name
        Glide.with(binding.root).load(data.brandimg).into(binding.cafePhotoIv)
        binding.countTotalFlTv.text = "총 "+data.stampCount+"개"

        binding.backIv.setOnClickListener {
            finish()
        }
    }
    fun stampOn(count : Int) {
        for (i in 0 until count) {
            coupons[i]?.setImageDrawable(resources.getDrawable(R.drawable.stamp))
        }
        for (i in count .. 9 ) {
            coupons[i]?.setImageDrawable(resources.getDrawable(R.drawable.circle_empty))
        }
    }

}