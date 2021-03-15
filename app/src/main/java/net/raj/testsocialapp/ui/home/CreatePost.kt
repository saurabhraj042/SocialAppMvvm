package net.raj.testsocialapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import net.raj.testsocialapp.R
import net.raj.testsocialapp.databinding.ActivityCreatePostBinding
import net.raj.testsocialapp.db.daos.PostDao

class CreatePost : AppCompatActivity() {

    private lateinit var viewModel: CreatePostActivityViewModel
    private lateinit var binding : ActivityCreatePostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreatePostBinding.inflate(layoutInflater)


        setContentView(binding.root)
        viewModel = CreatePostActivityViewModel()

        binding.postButton.setOnClickListener {
            fetchInputPostText()
        }
    }

    fun fetchInputPostText(){
        val text = binding.postInput.text.toString().trim()

        if( text.isNotEmpty() ){
            viewModel.addPost(text)
            finish()
        }
    }
}