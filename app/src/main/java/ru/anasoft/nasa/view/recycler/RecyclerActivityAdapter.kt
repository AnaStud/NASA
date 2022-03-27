package ru.anasoft.nasa.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ru.anasoft.nasa.databinding.ActivityRecyclerItemEventBinding
import ru.anasoft.nasa.databinding.ActivityRecyclerItemHeaderBinding
import ru.anasoft.nasa.databinding.ActivityRecyclerItemNoteBinding

class RecyclerActivityAdapter(val onClickItemListener:OnClickItemListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var listData: List<Data>

    fun setData(listData:List<Data>){
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_EVENT -> {
                val binding = ActivityRecyclerItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                EventViewHolder(binding.root)
            }
            TYPE_HEADER-> {
                val binding = ActivityRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                HeaderViewHolder(binding.root)
            }
            else-> {
                val binding = ActivityRecyclerItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                NoteViewHolder(binding.root)
            }
        }
    }

    inner class EventViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(data: Data){
            ActivityRecyclerItemEventBinding.bind(itemView).apply {
                textViewName.text = data.name
                textViewDescription.text = data.description
                imageViewItem.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    inner class NoteViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(data: Data){
            ActivityRecyclerItemNoteBinding.bind(itemView).apply {
                textViewName.text = data.name
                textViewDescription.text = data.description
                imageViewItem.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    inner class HeaderViewHolder(view:View):RecyclerView.ViewHolder(view){
        fun bind(data: Data){
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(getItemViewType(position)){
            TYPE_EVENT -> {
                (holder as EventViewHolder).bind(listData[position])
            }
            TYPE_HEADER -> {
                (holder as HeaderViewHolder).bind(listData[position])
            }
            else-> {
                (holder as NoteViewHolder).bind(listData[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun getItemCount() = listData.size

}
