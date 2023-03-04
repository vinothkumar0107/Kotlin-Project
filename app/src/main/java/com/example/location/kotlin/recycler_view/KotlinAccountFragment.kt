package com.example.location.kotlin.recycler_view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.location.R
import com.example.location.kotlin.login_api_kotlin.SessionManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KotlinAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class KotlinAccountFragment : Fragment()
{
    lateinit var name:TextView
    lateinit var email:TextView
    // TODO: Rename and change types of parameters
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_kotlin_account,container,false)
        name = view.findViewById(R.id.tv_name)
        email = view.findViewById(R.id.tv_email)

        val sessionManager = SessionManager
        sessionManager.getUserName(requireContext())

        name.text = sessionManager.getUserName(requireContext())
        email.text = sessionManager.getEmail(requireContext())

        return view
    }

}