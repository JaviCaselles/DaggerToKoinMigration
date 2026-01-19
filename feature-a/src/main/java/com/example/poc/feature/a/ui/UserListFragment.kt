package com.example.poc.feature.a.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import javax.inject.Inject

/**
 * Fragment que muestra lista de usuarios.
 * Demuestra inyección de dependencias usando el patrón DIManager.
 */
import dagger.android.support.AndroidSupportInjection
import androidx.lifecycle.ViewModelProvider
import com.example.poc.common.di.ViewModelFactory

class UserListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserListViewModel::class.java]
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
            text = "User List Fragment - Loading..."
            textSize = 18f
            setPadding(32, 32, 32, 32)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.users.observe(viewLifecycleOwner) { users ->
            // Actualizar UI con usuarios
            (view as? TextView)?.text = users.joinToString("\n\n") { 
                "👤 ${it.name}\n   📧 ${it.email}" 
            }
        }
    }
}
