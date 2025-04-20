package com.example.iterativepractice.legacy.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import com.example.iterativepractice.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OrderCompleteBottomSheet : BottomSheetDialogFragment() {

    private var listener: OrderCompleteListener? = null
    private var address = "Default address"

    override fun onAttach(context: Context) { // this is just to ensure interface is implemented by parent fragment
        super.onAttach(context)
        listener = try {
            parentFragmentManager.fragments.find {it is OrderCompleteListener } as? OrderCompleteListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement OrderCompleteListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_bottom_sheet_confirm_order, container, false)
        return view
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = view.findViewById<RadioButton>(checkedId)
            val selectedText = selectedRadioButton.text.toString()
            Toast.makeText(context, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
            when (checkedId) {
                R.id.radioButton2 -> { address = "Home" }
                R.id.radioButton3 -> { address = "Friend's house"}
                R.id.radioButton4 -> { address = "Office"}
                R.id.radioButton5 -> { address = "Hotel"}
            }
        }
        val confirmButton = view.findViewById<View>(R.id.button3)
        confirmButton.setOnClickListener {
            //listener?.onOrderComplete(address)
            val bundle = Bundle().apply {
                putString("address", address)
            }
            setFragmentResult("address", bundle)
            dismiss()
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}