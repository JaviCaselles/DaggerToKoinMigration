package com.example.poc.ui

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.poc.feature.a.ui.UserListFragment
import com.example.poc.feature.b.ui.ProductListFragment

/**
 * MainActivity principal.
 * Permite navegar entre los diferentes features (Users / Products).
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Create UI programmatically
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        // Title
        val title = TextView(this).apply {
            text = "POC Dagger → Koin"
            textSize = 24f
            setPadding(0, 0, 0, 32)
        }
        layout.addView(title)

        // Subtitle
        val subtitle = TextView(this).apply {
            text = "Fase 1: Solo Dagger"
            textSize = 16f
            setPadding(0, 0, 0, 48)
        }
        layout.addView(subtitle)

        // Buttons container
        val buttonsLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }

        // Users button
        val usersButton = Button(this).apply {
            text = "👤 Users"
            setOnClickListener { showUserList() }
        }
        buttonsLayout.addView(usersButton, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))

        // Products button
        val productsButton = Button(this).apply {
            text = "🛍️ Products"
            setOnClickListener { showProductList() }
        }
        buttonsLayout.addView(productsButton, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))

        // Feature C button
        val featureCButton = Button(this).apply {
            text = "🔗 Combined"
            setOnClickListener {
                val intent = android.content.Intent(this@MainActivity, com.example.poc.feature.c.ui.AggregatedActivity::class.java)
                startActivity(intent)
            }
        }
        buttonsLayout.addView(featureCButton, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))

        layout.addView(buttonsLayout)

        // Fragment container
        val fragmentContainer = FrameLayout(this).apply {
            id = FRAGMENT_CONTAINER_ID
            setPadding(0, 32, 0, 0)
        }
        layout.addView(fragmentContainer, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ))

        setContentView(layout)

        // Show users by default
        if (savedInstanceState == null) {
            showUserList()
        }
    }

    private fun showUserList() {
        supportFragmentManager.commit {
            replace(FRAGMENT_CONTAINER_ID, UserListFragment())
        }
    }

    private fun showProductList() {
        supportFragmentManager.commit {
            replace(FRAGMENT_CONTAINER_ID, ProductListFragment())
        }
    }

    companion object {
        private const val FRAGMENT_CONTAINER_ID = 0x12345
    }
}
