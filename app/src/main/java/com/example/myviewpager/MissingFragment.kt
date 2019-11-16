package com.example.myviewpager

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.myviewpager.dummy.DummyContent
import org.json.JSONArray


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MissingFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MissingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MissingFragment : Fragment() {

    // TODO: Customize parameters
    private var columnCount = 1

    private var listener: OpenMissingTransaction? = null


    private val missingList: ArrayList<MissingSingle> = ArrayList()

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
        val view = inflater.inflate(R.layout.fragment_missing, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                Log.e("size", "" + missingList.size)
                adapter = MissingRecyclerViewAdapter(context, missingList, listener)
                getData(adapter as MissingRecyclerViewAdapter)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MissingFragment.OpenMissingTransaction) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OpenMissingTransaction")
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
    interface OpenMissingTransaction {
        // TODO: Update argument type and name
        fun OpenMissingTransaction(item: MissingSingle)
    }


    // function to get daily transactions , can also supply a date /{date}
    private fun getData(adapter: MissingRecyclerViewAdapter) {
        val url = "http://fuelmaster.greenboxinnovations.in/api/admin/missing"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->

                missingList.clear()



                Log.e("tag", response.toString())

                for (i in 0 until response.length()) {
                    val item = response.getJSONObject(i)

                    val t = MissingSingle(
                        item["date"] as String,
                        item["t_string"] as String,
                        item["items"] as String
                    )
                    missingList.add(t)
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                //Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                Log.e("json error", it.toString())
            })

        VolleyService.requestQueue.add(request)
        //VolleyService.requestQueue.start()
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MissingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
