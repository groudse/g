package com.example.finalapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalapp.databinding.FragmentFirstBinding
import req


class FirstFragment : Fragment(R.layout.fragment_first) {

    val model by lazy { ViewModelProvider(this).get(FirstFragmentModel::class.java) }
    private var fragmentFirstBinding: FragmentFirstBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFirstBinding.bind(view)
        fragmentFirstBinding = binding

        val languages = resources.getStringArray(R.array.Languages)


        val adapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, languages
            )
        }
        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        model.data.observe(viewLifecycleOwner) {

            if (it == null) {
                binding.firstFragment.text = "wait"
            } else {
                binding.firstFragment.text = "${
                    req.roundOffDecimal(it).toString()
                }${binding.spinnerTo.selectedItem.toString()}"
            }
        }



        binding.button.setOnClickListener {
            if (binding.quantity.text.isEmpty()) {
                Toast.makeText(context, "Value is required", Toast.LENGTH_LONG).show()
            } else {
                if (req.isOnline(context)) {

                    model.loadData(
                        binding.spinnerFrom.selectedItem.toString(),
                        binding.spinnerTo.selectedItem.toString(),
                        binding.quantity.text.toString().toDouble()
                    )
                } else {
                    Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
                }
            }
        }
    }




}

