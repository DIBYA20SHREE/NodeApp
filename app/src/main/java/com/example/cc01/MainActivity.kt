package com.example.cc01

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.show()
        val item_list = findViewById<RecyclerView>(R.id.item_list)


        val fabAuth = findViewById<FloatingActionButton>(R.id.fabAuth)

        //floating button part
        setupAuthButton(UserData)

        UserData.isSignedIn.observe(this, Observer<Boolean> { isSignedUp ->
            // update UI
            Log.i(TAG, "isSignedIn changed : $isSignedUp")

            if (isSignedUp) {
                fabAuth.setImageResource(R.drawable.baseline_lock_open_24)
            } else {
                fabAuth.setImageResource(R.drawable.baseline_lock_24)
            }
        })

        // prepare our List view and RecyclerView (cells)
        setupRecyclerView(item_list)
    }


    // recycler view is the list of cells
    private fun setupRecyclerView(recyclerView: RecyclerView) {

        // update individual cell when the Note data are modified
        UserData.notes().observe(this, Observer<MutableList<UserData.Note>> { notes ->
            Log.d(TAG, "Note observer received ${notes.size} notes")

            // let's create a RecyclerViewAdapter that manages the individual cells
            recyclerView.adapter = NoteRecyclerViewAdapter(notes)
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    // floating action button part
    private fun setupAuthButton(userData: UserData) {


        val fabAuth = findViewById<FloatingActionButton>(R.id.fabAuth)

        // register a click listener
        fabAuth.setOnClickListener { view ->

            val authButton = view as FloatingActionButton

            if (userData.isSignedIn.value!!) {
                authButton.setImageResource(R.drawable.baseline_lock_open_24)
                Backend.signOut()
            } else {
                authButton.setImageResource(R.drawable.baseline_lock_open_24)
                Backend.signIn(this)
            }
        }
    }

    // MainActivity.kt
// receive the web redirect after authentication
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Backend.handleWebUISignInResponse(requestCode, resultCode, data)
    }


}




