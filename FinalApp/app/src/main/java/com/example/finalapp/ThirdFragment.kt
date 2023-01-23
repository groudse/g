package com.example.finalapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalapp.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    val model by lazy { ViewModelProvider(this).get(ThirdFragmentModel::class.java) }
    private var fragmentThirdBinding: FragmentThirdBinding? = null
    var adaptater = GameListAdaptater()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentThirdBinding.bind(view)
        fragmentThirdBinding = binding

        binding.rv.adapter = adaptater


        binding.rv.layoutManager = GridLayoutManager(context, 1)

        model.data.observe(viewLifecycleOwner) {
            if (it == null) {
                System.out.println("empty")
            } else {
                adaptater.submitList(it)
            }
        }

        binding.button2.setOnClickListener {
            if (req.isOnline(context)) {
                model.loadData(binding.simpleSearchView.query.toString())
            } else {
                Toast.makeText(context, "No internet", Toast.LENGTH_LONG).show()
            }
        }


    }

}