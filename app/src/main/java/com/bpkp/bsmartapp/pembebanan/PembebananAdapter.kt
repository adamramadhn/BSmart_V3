package com.bpkp.bsmartapp.pembebanan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananData
import com.dicoding.tourismapp.core.databinding.ItemListPembebananBinding
import java.util.ArrayList
//dipakai
class PembebananAdapter : RecyclerView.Adapter<PembebananAdapter.ListViewHolder>() {
    private var listData = ArrayList<PembebananData>()
    var onItemClick: ((PembebananData) -> Unit)? = null

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

    fun clear() {
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
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}