package com.example.poc.feature.c.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class AggregatedActivity : AppCompatActivity() {

    private val viewModel: AggregatedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            textSize = 16f
            setPadding(32, 32, 32, 32)
        }
        setContentView(textView)

        viewModel.data.observe(this) { (users, products) ->
            val usersText = users.joinToString("\n") { "👤 ${it.name}" }
            val productsText = products.joinToString("\n") { "📦 ${it.name} - ${it.price}€" }
            textView.text = "=== Usuarios ===\n$usersText\n\n=== Productos ===\n$productsText"
        }

        viewModel.loading.observe(this) { loading ->
            if (loading) textView.text = "Cargando datos combinados..."
        }
    }
}
