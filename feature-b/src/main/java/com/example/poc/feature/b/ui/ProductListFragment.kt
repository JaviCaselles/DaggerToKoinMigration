package com.example.poc.feature.b.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : Fragment() {

    private val viewModel: ProductListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            textSize = 16f
            setPadding(32, 32, 32, 32)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view as TextView

        viewModel.products.observe(viewLifecycleOwner) { products ->
            textView.text = products.joinToString("\n") { "${it.name} - ${it.price}€" }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) textView.text = "Cargando productos..."
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let { textView.text = "Error: $it" }
        }
    }
}
