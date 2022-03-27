package ru.anasoft.nasa.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.anasoft.nasa.databinding.ActivityRecyclerItemEventBinding
import ru.anasoft.nasa.databinding.ActivityRecyclerItemHeaderBinding
import ru.anasoft.nasa.databinding.ActivityRecyclerItemNoteBinding

class RecyclerActivityAdapter(val onClickItemListener:OnClickItemListener):RecyclerView.Adapter<RecyclerActivityAdapter.BaseViewHolder>() {

    private lateinit var listData: List<Data>

    fun setData(listData:List<Data>){
        this.listData = listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return when(viewType){
            TYPE_EVENT -> {
                EventViewHolder(ActivityRecyclerItemEventBinding.inflate(LayoutInflater.from(parent.context),parent,false).root)
            }
            TYPE_HEADER-> {
                HeaderViewHolder(ActivityRecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false).root)
            }
            else-> {
                NoteViewHolder(ActivityRecyclerItemNoteBinding.inflate(LayoutInflater.from(parent.context),parent,false).root)
            }
        }
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: Data)
    }


    inner class EventViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Data){
            ActivityRecyclerItemEventBinding.bind(itemView).apply {
                textViewName.text = data.name
                textViewDescription.text = data.description
                imageViewItem.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    inner class NoteViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Data){
            ActivityRecyclerItemNoteBinding.bind(itemView).apply {
                textViewName.text = data.name
                textViewDescription.text = data.description
                imageViewItem.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Data){
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun getItemCount() = listData.size

}