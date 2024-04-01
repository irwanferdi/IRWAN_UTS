package com.example.irwan_uts.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import com.example.irwan_uts.model.DataItemUser
import com.example.irwan_uts.model.ResponseApiUser
import com.example.irwan_uts.R
import com.example.irwan_uts.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Home : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var userArrayList: ArrayList<DataItemUser> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recyclerview_user)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = UserAdapter(userArrayList)
        recyclerView.adapter = adapter

        searchView = view.findViewById(R.id.search_action)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        fetchData()
    }

    private fun fetchData() {
        val client = ApiConfig.getApiService().getListUsers("1")
        client.enqueue(object : Callback<ResponseApiUser> {
            override fun onResponse(call: Call<ResponseApiUser>, response: Response<ResponseApiUser>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.data as List<DataItemUser>
                    userArrayList.addAll(dataArray)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ResponseApiUser>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }

    private fun filterList(query: String?) {
        if (!query.isNullOrEmpty()) {
            val filteredList = ArrayList<DataItemUser>()
            for (user in userArrayList) {
                val username = "${user.firstName} ${user.lastName}"
                if (username.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(user)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        } else {
            adapter.setFilteredList(userArrayList)
        }
    }
}
