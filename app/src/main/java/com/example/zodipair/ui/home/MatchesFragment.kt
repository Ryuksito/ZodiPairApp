package com.example.zodipair.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.zodipair.R
import com.example.zodipair.data.UserSessionManager
import com.example.zodipair.domain.models.GetProfileModel
import com.example.zodipair.domain.models.GetRequestModel
import com.example.zodipair.domain.use_cases.ApiManager
import com.example.zodipair.utils.RequestsAdapter
import kotlinx.coroutines.launch

class MatchesFragment : Fragment() {

    private lateinit var currentRequests: GetRequestModel
    private val apiManager = ApiManager()
    private lateinit var dontHaveRequestsLayout: LinearLayout
    private var profilesList: MutableList<GetProfileModel> = mutableListOf()
    private var userNames: MutableList<String> = mutableListOf()
    private var requestsTypes: MutableList<Boolean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matches, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        dontHaveRequestsLayout = view.findViewById(R.id.donHaveRequests)

        fetchRequests { requests ->
            val allRequests = requests.hot_hearts + requests.hearts // Combina ambas listas
            if (allRequests.isNotEmpty()){
                lifecycleScope.launch {
                    for(r:String in requests.hot_hearts){
                        profilesList.add(apiManager.postGetProfile(r))
                        userNames.add(apiManager.getUser(r).user_name)
                        requestsTypes.add(true)
                    }
                    for(r:String in requests.hearts){
                        profilesList.add(apiManager.postGetProfile(r))
                        userNames.add(apiManager.getUser(r).user_name)
                        requestsTypes.add(false)
                    }
                    val adapter = RequestsAdapter(profilesList, userNames, requestsTypes)
                    recyclerView.adapter = adapter
                    profilesList = mutableListOf()
                }


            }else{
                dontHaveRequestsLayout.visibility = View.VISIBLE
            }

        }

        return view
    }

    private fun fetchRequests(onComplete: (GetRequestModel) -> Unit) {
        lifecycleScope.launch {
            UserSessionManager.uuid?.let {
                val requests = apiManager.getUserRequest(it)
                Log.d("Requests", requests.toString())
                onComplete(requests)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MatchesFragment().apply {
            }
    }
}