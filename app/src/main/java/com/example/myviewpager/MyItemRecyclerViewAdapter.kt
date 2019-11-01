package com.example.myviewpager


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.myviewpager.ItemFragment.OnListFragmentInteractionListener
import com.example.myviewpager.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val context: Context,
    private val mValues: List<TransactionSingle>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        val urlMain = "http://fuelmaster.greenboxinnovations.in/uploads/"
        val ext = ".jpeg"

        val url1: String = urlMain + item.date + "/" + item.t_string + "_start" + ext
        val url2: String = urlMain + item.date + "/" + item.t_string + "_start_top" + ext
        val url3: String = urlMain + item.date + "/" + item.t_string + "_stop" + ext
        val url4: String = urlMain + item.date + "/" + item.t_string + "_stop_top" + ext

        holder.itemName.text = item.customerDisplayName
        holder.itemCarNo.text = item.car_no_plate
        holder.itemDuration.text = item.trans_time
        holder.itemAmount.text = item.amount.toString()
        holder.itemLitres.text = item.litres.toString()

        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        if (item.t_string != "") {
            Glide.with(context)
                .load(url1)
                .apply(requestOptions)
                .thumbnail(0.05f)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.startPhoto)
            Glide.with(context)
                .load(url2)
                .apply(requestOptions)
                .thumbnail(0.05f)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.startTopPhoto)
            Glide.with(context)
                .load(url3)
                .apply(requestOptions)
                .thumbnail(0.05f)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.stopPhoto)
            Glide.with(context)
                .load(url4)
                .apply(requestOptions)
                .thumbnail(0.05f)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.stopTopPhoto)
        } else {
            // hide images if trans string is null
            // transaction has been manually uploaded
            holder.startPhoto.visibility = View.GONE
            holder.startTopPhoto.visibility = View.GONE
            holder.stopPhoto.visibility = View.GONE
            holder.stopTopPhoto.visibility = View.GONE
        }



        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val itemLitres: TextView = mView.findViewById(R.id.item_litres)
        val itemAmount: TextView = mView.findViewById(R.id.item_amount)
        val itemCarNo: TextView = mView.findViewById(R.id.item_car_no)
        val itemName: TextView = mView.findViewById(R.id.item_name)
        val itemDuration: TextView = mView.findViewById(R.id.item_duration)

        val startPhoto: ImageView = mView.findViewById(R.id.iv_start)
        val startTopPhoto: ImageView = mView.findViewById(R.id.iv_start_top)
        val stopPhoto: ImageView = mView.findViewById(R.id.iv_stop)
        val stopTopPhoto: ImageView = mView.findViewById(R.id.iv_stop_top)


//        val mIdView: TextView = mView.item_number
//        val mContentView: TextView = mView.content
//
//        override fun toString(): String {
//            return super.toString() + " '" + mContentView.text + "'"
//        }
    }
}
