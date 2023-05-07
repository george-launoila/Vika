package com.example.george

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.george.databinding.FragmentCommentApiBinding
import com.google.gson.GsonBuilder

class CommentApiFragment : Fragment() {
    // change this to match your fragment name
    private var _binding: FragmentCommentApiBinding? = null

    // alustetaan viittaus adapteriin sekä luodaan LinearLayoutManager
    // RecyclerView tarvitsee jonkin LayoutManagerin, joista yksinkertaisin on Linear
    private lateinit var adapter: CommentAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentApiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // asetetaan RecyclerViewille linear layout manager
        linearLayoutManager = LinearLayoutManager(context)
        binding.recyclerViewComments.layoutManager = linearLayoutManager

        binding.buttonGetComments.setOnClickListener {
            Log.d("TESTI", "Nappi toimii!")
            // haetaan kommentit rajapinnasta
            getComments()
        }

        // the binding -object allows you to access views in the layout, textviews etc.
        return root
    }

    // apunfunktio, joka hakee Volleylla dataa fragmenttiin
    fun getComments()
    {
        // this is the url where we want to get our data from
        val JSON_URL = "https://jsonplaceholder.typicode.com/comments"

        // alustetaan GSON-plugin
        val gson = GsonBuilder().setPrettyPrinting().create()

        // Request a string response from the provided URL.
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.GET, JSON_URL,
            Response.Listener { response ->

                // print the response as a whole
                // we can use GSON to modify this response into something more usable
                // Log.d("ADVTECH", response)
                var rows : List<Comment> = gson.fromJson(response, Array<Comment>::class.java).toList()

                for(item in rows)
                {
                    Log.d("ADVTECH", item.email.toString())
                }

                // kytketään rajapinnasta ladattu data recyclerviewin adapteriin
                adapter = CommentAdapter(rows)
                binding.recyclerViewComments.adapter = adapter

            },
            Response.ErrorListener {
                // typically this is a connection error
                Log.d("ADVTECH", it.toString())
            })
        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {

                // basic headers for the data
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json; charset=utf-8"
                return headers
            }
        }

        // Add the request to the RequestQueue. This has to be done in both getting and sending new data.
        // if using this in an activity, use "this" instead of "context"
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}