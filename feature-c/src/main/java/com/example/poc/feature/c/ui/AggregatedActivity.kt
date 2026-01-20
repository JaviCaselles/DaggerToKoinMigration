package com.example.poc.feature.c.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.poc.common.di.DI
import com.example.poc.common.di.ViewModelFactory

class AggregatedActivity : AppCompatActivity() {

    private val viewModelFactory: ViewModelFactory by lazy { DI.get() }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AggregatedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.data.observe(this) { (users, products) ->
            setContentView(TextView(this).apply {
                text = buildString {
                    append("--- USERS ---\n")
                    users.forEach { append("👤 ${it.name}\n") }
                    append("\n--- PRODUCTS ---\n")
                    products.forEach { append("📦 ${it.name}\n") }
                }
                textSize = 18f
                setPadding(32, 32, 32, 32)
            })
        }
    }
}
