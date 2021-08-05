package com.bpkp.bsmartapp.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import com.dicoding.tourismapp.core.databinding.ItemListSuratTugasBinding
import java.util.ArrayList

class SuratTugasAdapter : RecyclerView.Adapter<SuratTugasAdapter.ListViewHolder>() {
    private var listData = ArrayList<SuratTugasResponse>()
    var onItemClick: ((SuratTugasResponse) -> Unit)? = null

//    fun setData(newListData: List<SuratTugasResponse>?) {
//        if (newListData == null) return
//        listData.clear()
//        listData.addAll(newListData)
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            ItemListSuratTugasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    fun addList(items: List<SuratTugasResponse>) {
        listData.addAll(items)
        notifyDataSetChanged()
    }
    fun clear(){
        listData.clear()
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemListSuratTugasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SuratTugasResponse) {

            binding.apply {

                tvStId.text = "${data.id_st} |"
                tvDate.text = data.tgl_st
                tvStNumber.text = data.no_st
                tvDescription.text = data.perihal

                when (data.apv_es4) {
                    1 -> {
                        circleYellowEs4.visibility = View.VISIBLE
                        circleRedEs4.visibility = View.GONE
                    }
                    0 -> {
                        circleRedEs4.visibility = View.VISIBLE
                        circleYellowEs4.visibility = View.GONE
                    }
                    else -> {
                        circleGreyEs4.visibility = View.VISIBLE
                        circleRedEs4.visibility = View.GONE
                        circleYellowEs4.visibility = View.GONE
                    }
                }

                when (data.apv_es3) {
                    1 -> {
                        circleYellowEs3.visibility = View.VISIBLE
                        circleRedEs3.visibility = View.GONE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    0 -> {
                        circleRedEs3.visibility = View.VISIBLE
                        circleYellowEs3.visibility = View.GONE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    else -> {
                        circleGreyEs3.visibility = View.VISIBLE
                        circleRedEs3.visibility = View.GONE
                        circleYellowEs3.visibility = View.GONE
                        lineYellowEs3.visibility = View.GONE
                    }
                }

                when (data.apv_es2) {
                    1 -> {
                        circleYellowEs2.visibility = View.VISIBLE
                        circleRedEs2.visibility = View.GONE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    0 -> {
                        circleRedEs2.visibility = View.VISIBLE
                        circleYellowEs2.visibility = View.GONE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    else -> {
                        circleGreyEs2.visibility = View.VISIBLE
                        circleRedEs2.visibility = View.GONE
                        circleYellowEs2.visibility = View.GONE
                        lineYellowEs2.visibility = View.GONE
                    }
                }

                when (data.apv_es1) {
                    1 -> {
                        circleYellowEs1.visibility = View.VISIBLE
                        circleRedEs1.visibility = View.GONE
                        lineYellowEs1.visibility = View.VISIBLE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    0 -> {
                        circleRedEs1.visibility = View.VISIBLE
                        circleYellowEs1.visibility = View.GONE
                        lineYellowEs1.visibility = View.VISIBLE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    else -> {
                        circleGreyEs1.visibility = View.VISIBLE
                        circleRedEs1.visibility = View.GONE
                        circleYellowEs1.visibility = View.GONE
                        lineYellowEs1.visibility = View.GONE
                    }
                }
            }
        }


        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

}