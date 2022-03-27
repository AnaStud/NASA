package ru.anasoft.nasa.view.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.anasoft.nasa.databinding.ActivityRecyclerItemEventBinding
import ru.anasoft.nasa.databinding.ActivityRecyclerItemHeaderBinding
import ru.anasoft.nasa.databinding.ActivityRecyclerItemNoteBinding

class RecyclerActivityAdapter(val onClickItemListener:OnClickItemListener):RecyclerView.Adapter<RecyclerActivityAdapter.BaseViewHolder>() {

    private lateinit var listData: MutableList<Pair<Data, Boolean>>

    fun setData(listData:MutableList<Pair<Data, Boolean>>){
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
        abstract fun bind(data: Pair<Data, Boolean>)
    }


    inner class EventViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Pair<Data, Boolean>){
            ActivityRecyclerItemEventBinding.bind(itemView).apply {
                textViewName.text = data.first.name
                textViewDescription.text = data.first.description
                textViewFullDescription.text = data.first.description
                imageViewItem.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
                addItem.setOnClickListener {
                    listData.add(layoutPosition + 1, Pair(Data("New event", "Description event", TYPE_EVENT), false))
                    notifyItemInserted(layoutPosition + 1)
                }
                removeItem.setOnClickListener {
                    listData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemUp.setOnClickListener {
                    if (layoutPosition > 1) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)
                    }
                }
                moveItemDown.setOnClickListener {
                    if (layoutPosition < (listData.size - 1)) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)
                    }
                }
                textViewFullDescription.visibility =
                    if (listData[layoutPosition].second) View.VISIBLE else View.GONE
                itemView.setOnClickListener {
                    listData[layoutPosition] = listData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
            }
        }
    }

    inner class NoteViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Pair<Data, Boolean>){
            ActivityRecyclerItemNoteBinding.bind(itemView).apply {
                textViewName.text = data.first.name
                textViewDescription.text = data.first.description
                textViewFullDescription.text = data.first.description
                imageViewItem.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
                addItem.setOnClickListener {
                    listData.add(layoutPosition + 1, Pair(Data("New note", "Description note", TYPE_NOTE), false))
                    notifyItemInserted(layoutPosition + 1)
                }
                removeItem.setOnClickListener {
                    listData.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
                moveItemUp.setOnClickListener {
                    if (layoutPosition > 1) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition - 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition - 1)
                    }
                }
                moveItemDown.setOnClickListener {
                    if (layoutPosition < (listData.size - 1)) {
                        listData.removeAt(layoutPosition).apply {
                            listData.add(layoutPosition + 1, this)
                        }
                        notifyItemMoved(layoutPosition, layoutPosition + 1)
                    }
                }
                textViewFullDescription.visibility =
                    if (listData[layoutPosition].second) View.VISIBLE else View.GONE
                itemView.setOnClickListener {
                    listData[layoutPosition] = listData[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
            }
        }
    }

    inner class HeaderViewHolder(view:View):BaseViewHolder(view){
        override fun bind(data: Pair<Data, Boolean>){
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                tvName.text = data.first.name
                itemView.setOnClickListener {
                    onClickItemListener.onItemClick(data.first)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return listData[position].first.type
    }

    override fun getItemCount() = listData.size

}
