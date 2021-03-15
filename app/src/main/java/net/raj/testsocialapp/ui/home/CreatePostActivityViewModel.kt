package net.raj.testsocialapp.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.raj.testsocialapp.ui.Repository

class CreatePostActivityViewModel : ViewModel() {
    private val repository = Repository()
    fun addPost(text : String){
        GlobalScope.launch(Dispatchers.IO) {
            repository.addPost(text)
        }
    }
}