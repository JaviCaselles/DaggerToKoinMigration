package com.example.poc.feature.a.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {

    private val viewModel: UserListViewModel by viewModel()

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

        viewModel.users.observe(viewLifecycleOwner) { users ->
            textView.text = users.joinToString("\n") { "${it.name} (${it.email})" }
        }

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading) textView.text = "Cargando usuarios..."
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let { textView.text = "Error: $it" }
        }
    }
}
