package com.example.iterativepractice.legacy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment

class InfoFabFragment : Fragment() {
    companion object {
        private const val ARG_PAGE_ID = "arg_page_id"

        fun newInstance(pageId: Int): InfoFabFragment {
            val fragment = InfoFabFragment()
            val args = Bundle()
            args.putInt(ARG_PAGE_ID, pageId)
            fragment.arguments = args
            return fragment
        }
    }
}