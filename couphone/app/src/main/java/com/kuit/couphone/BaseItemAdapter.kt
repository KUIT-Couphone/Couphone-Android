package com.kuit.couphone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kuit.couphone.data.BrandResult
import com.kuit.couphone.data.StoreInfo
import com.kuit.couphone.databinding.ItemStoreBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BaseItemAdapter(private val itemList : ArrayList<BrandResult>) : RecyclerView.Adapter<BaseItemAdapter.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener
    interface OnItemClickListener{
        fun onItemClick(itemList: BrandResult)
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        itemClickListener = onItemClickListener
    }
    inner class ViewHolder(val binding : ItemStoreBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(storeInfo: BrandResult){
            binding.storeNameTv.text = storeInfo.name
            binding.storeInfoTv.text = storeInfo.rewardDescription
            binding.itemWebtoonCl.setOnClickListener{
                itemClickListener.onItemClick(storeInfo)
            }
            Glide.with(binding.root).load(storeInfo.brandImageUrl).into(binding.categoryImgIv)
            if(storeInfo.stampCount !=0 ) {
                binding.couphoneCountBackgroundIv.text =
                    storeInfo.stampCount.toString() + " / 10"

                var sf = SimpleDateFormat("yyyy-MM-dd")
                var date = sf.parse(storeInfo.createdDate)
                var today = Calendar.getInstance()

                var calcuDate = 183 -(today.time.time - date.time) / (60 * 60 * 24 * 1000)

                binding.couphoneDdayBackgroundIv.text = "D-$calcuDate"
            }
            else{
                binding.couphoneCountBackgroundIv.visibility = View.GONE
                binding.couphoneDdayBackgroundIv.visibility = View.GONE
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemAdapter.ViewHolder {
        val binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseItemAdapter.ViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int =itemList.size
}