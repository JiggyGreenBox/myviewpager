package com.example.myviewpager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest

import com.example.myviewpager.dummy.DummyContent.DummyItem
import org.json.JSONArray

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ItemFragment.OnListFragmentInteractionListener] interface.
 */
class ItemFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null


    private val transactionList: ArrayList<TransactionSingle> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                Log.e("size", "" + transactionList.size)
                adapter = MyItemRecyclerViewAdapter(context, transactionList, listener)
                getData(adapter as MyItemRecyclerViewAdapter)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: DummyItem?)
    }


    // function to get daily transactions , can also supply a date /{date}
    private fun getData(adapter: MyItemRecyclerViewAdapter) {
        val url = "http://fuelmaster.greenboxinnovations.in/api/admin/transactions/2019-09-29"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->

                transactionList.clear()



                Log.e("tag", response.toString())

                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i)
                    Log.e("tag", "" + item["trans_time"])

                    val transTime = item.optString("trans_time").replace("null", "")
                    val transString = item.optString("trans_string").replace("null", "")

                    val t = TransactionSingle(
                        item["cust_id"] as Int,
                        item["cust_disp_name"] as String,
                        item.getDouble("liters"),
                        item.getDouble("rate"),
                        item.getDouble("amount"),
                        item["timestamp"] as String,
                        item["date"] as String,
                        item.getString("car_no_plate"),
//                        item["trans_time"] as String,
//                        item["trans_string"] as String
                        transTime,
                        transString
                    )
                    transactionList.add(t)
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                //Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                Log.e("json error", "error")
            })

        VolleyService.requestQueue.add(request)
        VolleyService.requestQueue.start()
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
