package com.example.poc.feature.c.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.poc.common.di.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class AggregatedActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AggregatedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        val textView = TextView(this).apply {
            text = "Aggregated Feature (Activity) - Loading..."
            textSize = 18f
            setPadding(32, 32, 32, 32)
        }
        setContentView(textView)

        viewModel.data.observe(this) { (users, products) ->
            textView.text = buildString {
                append("--- USERS ---\n")
                users.forEach { append("👤 ${it.name}\n") }
                append("\n--- PRODUCTS ---\n")
                products.forEach { append("📦 ${it.name}\n") }
            }
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                textView.text = "Error: $error"
            }
        }
    }
}
