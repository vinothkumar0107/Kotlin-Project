package com.example.location.kotlin.recycler_view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.location.R
import com.example.location.kotlin.bottom_sheet.BottomSheetAdapter
import com.example.location.kotlin.bottom_sheet.BottomSheetViewModel
import com.example.location.kotlin.bottom_sheet.SlotTimings
import com.google.android.material.bottomsheet.BottomSheetDialog


class FavoriteFragment : Fragment() {
    private val listViewModel by viewModels<ListViewModel>()
    var arrayList = ArrayList<ListData>()
    var bottomArrayList = ArrayList<SlotTimings>()
    lateinit var recyclerView: RecyclerView
    private val viewModel by viewModels<BottomSheetViewModel>()

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.findViewById(R.id.fav_recycler_view)

        listData()

        listViewModel.listLiveData.observe(viewLifecycleOwner) {
            if (it?.status == "1") {
                if (it.response != null) {
                    arrayList = it.response!!
                    val itemAdapter = ItemAdapter(arrayList, requireContext(), onClick = { it ->
                        if (it != null) {
                            Log.d("lamda", "lamda  " + it.toString())

                            val userId = "1663144227-548b-4c9e-9854-238f89102746"
                            val slot_date = "22-10-2022"
                            viewModel.bottomSheet(userId, slot_date)
                        }
                    })
                    recyclerView.adapter = itemAdapter

                }
            } else {
                //Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.bottomSheetLiveData.observe(requireActivity()) {
            if (it.status == "1") {
                if (it.response != null) {
                    bottomArrayList = it.response!!.slotTimings
                    bottomSheet()

                }
            } else {
                // Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun bottomSheet() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        var botomRecycler = view.findViewById<RecyclerView>(R.id.bottom_sheet_recycler_view)

        val bottomSheetAdapter = BottomSheetAdapter(bottomArrayList)
        botomRecycler.adapter = bottomSheetAdapter
        dialog.setContentView(view)
        dialog.show()
    }

    private fun listData() {
        val user_id = "1663144227-548b-4c9e-9854-238f89102746"
        val page_number = "0"
        listViewModel.listOfData(user_id, page_number)
    }

}