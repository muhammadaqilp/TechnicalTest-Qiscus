package com.example.technicaltest_qiscus.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList

abstract class SortedRecyclerViewAdapter<Item, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH>() {
//    private var recyclerViewItemClickListener: CommentsAdapter.RecyclerViewItemClickListener? = null

    private val data: SortedList<Item> = SortedList(
        getItemClass()!!, object : SortedList.Callback<Item>() {
            override fun compare(o1: Item, o2: Item): Int {
                return this@SortedRecyclerViewAdapter.compare(o1, o2)
            }

            override fun onInserted(position: Int, count: Int) {
                return this@SortedRecyclerViewAdapter.onInserted(position, count)
            }

            override fun onRemoved(position: Int, count: Int) {
                return this@SortedRecyclerViewAdapter.onRemoved(position, count)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                return this@SortedRecyclerViewAdapter.onMoved(fromPosition, toPosition)
            }

            override fun onChanged(position: Int, count: Int) {
                return this@SortedRecyclerViewAdapter.onChanged(position, count)
            }

            override fun areContentsTheSame(oldItem: Item?, newItem: Item?): Boolean {
                return oldItem?.let { old ->
                    newItem?.let { new ->
                        this@SortedRecyclerViewAdapter.areContentsTheSame(
                            old, new
                        )
                    }
                } == true
            }

            override fun areItemsTheSame(item1: Item?, item2: Item?): Boolean {
                return item1?.let { i1 ->
                    item2?.let { i2 ->
                        this@SortedRecyclerViewAdapter.areContentsTheSame(
                            i1, i2
                        )
                    }
                } == true
            }

        })

    protected abstract fun getItemClass(): Class<Item>

    protected abstract fun compare(item1: Item, item2: Item): Int

    protected fun onChanged(position: Int, count: Int) {}

    protected fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    protected fun areItemsTheSame(item1: Item, item2: Item): Boolean {
        return item1 == item2
    }

    protected fun onInserted(position: Int, count: Int) {}

    protected fun onRemoved(position: Int, count: Int) {}

    protected fun onMoved(fromPosition: Int, toPosition: Int) {}

    override fun getItemCount(): Int {
        return data.size()
    }

    fun getData(): SortedList<Item> {
        return data
    }

    fun findPosition(item: Item): Int {
        val size = data.size() - 1
        for (i in size downTo 0) {
            if (data[i] == item) {
                return i
            }
        }
        return -1
    }

    fun add(item: Item): Int {
        val i = data.add(item)
        notifyItemInserted(i)
        return i
    }

    fun add(items: List<Item>) {
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun addOrUpdate(item: Item) {
        val i = findPosition(item)
        if (i >= 0) {
            data.updateItemAt(i, item)
            notifyDataSetChanged()
        } else {
            add(item)
        }
    }

    open fun addOrUpdate(items: List<Item>) {
        for (item in items) {
            val i = findPosition(item)
            if (i >= 0) {
                data.updateItemAt(i, item)
            } else {
                data.add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        if (position >= 0 && position < data.size()) {
            data.removeItemAt(position)
            notifyItemRemoved(position)
        }
    }

    fun remove(item: Item) {
        val position = findPosition(item)
        remove(position)
    }

    //Set method of OnItemClickListener object
//    fun setOnItemClickListener(recyclerViewItemClickListener: CommentsAdapter.RecyclerViewItemClickListener?) {
//        this.recyclerViewItemClickListener = recyclerViewItemClickListener
//    }
//
//    fun setOnClickListener(view: View, position: Int) {
//        view.setOnClickListener { v -> //When item view is clicked, trigger the itemclicklistener
//            recyclerViewItemClickListener.onItemClick(v, position)
//        }
//        view.setOnLongClickListener { v -> //When item view is clicked long, trigger the itemclicklistener
//            recyclerViewItemClickListener.onItemLongClick(v, position)
//            true
//        }
//    }

    fun clear() {
        data.clear()
    }
}