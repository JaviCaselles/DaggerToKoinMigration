package com.example.poc.feature.b.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import javax.inject.Inject

/**
 * Fragment que muestra lista de productos.
 * Demuestra inyección de dependencias usando el patrón DIManager.
 */
import dagger.android.support.AndroidSupportInjection
import androidx.lifecycle.ViewModelProvider
import com.example.poc.common.di.ViewModelFactory

class ProductListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProductListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(context).apply {
            text = "Product List Fragment - Loading..."
            textSize = 18f
            setPadding(32, 32, 32, 32)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.products.observe(viewLifecycleOwner) { products ->
            // Actualizar UI con productos
            (view as? TextView)?.text = products.joinToString("\n\n") { 
                "🛍️ ${it.name}\n   💰 ${it.price}€" 
            }
        }
    }
}
