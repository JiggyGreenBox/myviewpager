package com.example.myviewpager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myviewpager.dummy.DummyContent

class MissingRecyclerViewAdapter(
    private val context: Context,
    private val mValues: List<MissingSingle>,
    private val mListener: MissingFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MissingRecyclerViewAdapter.ViewHolder>() {


    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyContent.DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.missing_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mValues[position]

        holder.missingDate.text = item.date
        holder.missingVal.text = item.t_string
        holder.missingTString.text = item.items
    }

    inner class ViewHolder(private val mView: View) : RecyclerView.ViewHolder(mView) {
        val missingDate: TextView = mView.findViewById(R.id.missing_date)
        val missingVal: TextView = mView.findViewById(R.id.missing_item)
        val missingTString: TextView = mView.findViewById(R.id.missing_t_string)
    }
}