package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: GenerateButtonClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        listener = context as GenerateButtonClickedListener

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val minValueField: EditText = view.findViewById(R.id.min_value)
        val maxValueField: EditText = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            val minStr = minValueField.text.toString()
            val maxStr = maxValueField.text.toString()
            if (minStr.length >= 11 || maxStr.length >= 11)
                Toast.makeText(context, "Too big number or numbers", Toast.LENGTH_LONG).show()
            else {
                val min = minStr.toLongOrNull()
                val max = maxStr.toLongOrNull()
                if (min != null && max != null)
                    if (min < max)
                        if (min <= MAX_INTEGER_VALUE && max <= MAX_INTEGER_VALUE)
                            listener?.onButtonClicked(min.toInt(), max.toInt())
                        else Toast.makeText(context, "Too big number or numbers", Toast.LENGTH_LONG)
                            .show()
                    else
                        Toast.makeText(
                            context,
                            "The min value is greater than or equal to the max value",
                            Toast.LENGTH_LONG
                        ).show()
                else
                    Toast.makeText(context, "Please input numbers", Toast.LENGTH_LONG).show()
            }
        }
    }

    interface GenerateButtonClickedListener {
        fun onButtonClicked(min: Int, max: Int)
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
        private const val MAX_INTEGER_VALUE = 2_147_483_647
    }
}