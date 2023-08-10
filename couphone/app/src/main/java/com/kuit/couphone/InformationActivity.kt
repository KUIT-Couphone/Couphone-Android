package com.kuit.couphone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfomationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var data =
            Gson().fromJson(intent.getStringExtra("Data"), Information::class.java)
        if(data.createdDate == null){
            binding.couphoneIssueddateUpdateTv.text = "아직 방문 하지 않았습니다!"
        }
        else{
            var sf = SimpleDateFormat("yyyy-MM-dd")
            var date = data.createdDate?.let { sf.parse(it) }
            binding.couphoneIssueddateUpdateTv.text = date.toString()
        }

        binding.cafeNameTv.text = data.name
        Glide.with(binding.root).load(data.brandimg).into(binding.cafePhotoIv)
        binding.countTotalFlTv.text = "총 "+data.stampCount+"개"

        binding.backIv.setOnClickListener {
            finish()
        }
    }

}