package com.bpkp.bsmartapp.pembebanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananData
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananResponse
import com.dicoding.tourismapp.core.databinding.ItemListPembebananBinding
import com.dicoding.tourismapp.core.databinding.ItemListSuratTugasBinding
import java.util.ArrayList

class PembebananAdapter : RecyclerView.Adapter<PembebananAdapter.ListViewHolder>(){
    private var listData = ArrayList<PembebananData>()
    var onItemClick: ((PembebananData) -> Unit)? = null

//    fun setData(newListData: List<SuratTugasResponse>?) {
//        if (newListData == null) return
//        listData.clear()
//        listData.addAll(newListData)
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view =
            ItemListPembebananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }


    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    fun addList(items: List<PembebananData>) {
        listData.addAll(items)
        notifyDataSetChanged()
    }

    fun clear(){
        listData.clear()
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: ItemListPembebananBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PembebananData) {

            binding.apply {

                tvBebanId.text = "${data.id_beban} / "
                tvBebanNumber.text = data.no_beban
                val x = data.tgl_buat
                val time = x?.dropLast(8)
                tvDate.text = time
                tvDescription.text = data.perihal

                tvMakNumber.text = "${data.mak_id}.${data.makkode}"
                tvMakKet.text = data.maknama
                tvNilai.text = "Rp.${data.nilai}"

//                when (data.apv_es5) {
//                    1 -> {
//                        circleYellowEs1.visibility = View.VISIBLE
//                        circleRedEs1.visibility = View.GONE
//                        lineYellowEs1.visibility = View.VISIBLE
//                        lineYellowEs2.visibility = View.VISIBLE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    0 -> {
//                        circleRedEs1.visibility = View.VISIBLE
//                        circleYellowEs1.visibility = View.GONE
//                        lineYellowEs1.visibility = View.VISIBLE
//                        lineYellowEs2.visibility = View.VISIBLE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    else -> {
//                        circleGreyEs1.visibility = View.VISIBLE
//                        circleRedEs1.visibility = View.GONE
//                        circleYellowEs1.visibility = View.GONE
//                        lineYellowEs1.visibility = View.GONE
//                    }
//                }
//
//                when (data.apv_es4) {
//                    1 -> {
//                        circleYellowEs4.visibility = View.VISIBLE
//                        circleRedEs4.visibility = View.GONE
//                    }
//                    0 -> {
//                        circleRedEs4.visibility = View.VISIBLE
//                        circleYellowEs4.visibility = View.GONE
//                    }
//                    else -> {
//                        circleGreyEs4.visibility = View.VISIBLE
//                        circleRedEs4.visibility = View.GONE
//                        circleYellowEs4.visibility = View.GONE
//                    }
//                }
//
//                when (data.apv_es3) {
//                    1 -> {
//                        circleYellowEs3.visibility = View.VISIBLE
//                        circleRedEs3.visibility = View.GONE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    0 -> {
//                        circleRedEs3.visibility = View.VISIBLE
//                        circleYellowEs3.visibility = View.GONE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    else -> {
//                        circleGreyEs3.visibility = View.VISIBLE
//                        circleRedEs3.visibility = View.GONE
//                        circleYellowEs3.visibility = View.GONE
//                        lineYellowEs3.visibility = View.GONE
//                    }
//                }
//
//                when (data.apv_es2) {
//                    1 -> {
//                        circleYellowEs2.visibility = View.VISIBLE
//                        circleRedEs2.visibility = View.GONE
//                        lineYellowEs2.visibility = View.VISIBLE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    0 -> {
//                        circleRedEs2.visibility = View.VISIBLE
//                        circleYellowEs2.visibility = View.GONE
//                        lineYellowEs2.visibility = View.VISIBLE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    else -> {
//                        circleGreyEs2.visibility = View.VISIBLE
//                        circleRedEs2.visibility = View.GONE
//                        circleYellowEs2.visibility = View.GONE
//                        lineYellowEs2.visibility = View.GONE
//                    }
//                }
//
//                when (data.apv_es1) {
//                    1 -> {
//                        circleYellowEs1.visibility = View.VISIBLE
//                        circleRedEs1.visibility = View.GONE
//                        lineYellowEs1.visibility = View.VISIBLE
//                        lineYellowEs2.visibility = View.VISIBLE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    0 -> {
//                        circleRedEs1.visibility = View.VISIBLE
//                        circleYellowEs1.visibility = View.GONE
//                        lineYellowEs1.visibility = View.VISIBLE
//                        lineYellowEs2.visibility = View.VISIBLE
//                        lineYellowEs3.visibility = View.VISIBLE
//                    }
//                    else -> {
//                        circleGreyEs1.visibility = View.VISIBLE
//                        circleRedEs1.visibility = View.GONE
//                        circleYellowEs1.visibility = View.GONE
//                        lineYellowEs1.visibility = View.GONE
//                    }
//                }


            }
        }


        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}