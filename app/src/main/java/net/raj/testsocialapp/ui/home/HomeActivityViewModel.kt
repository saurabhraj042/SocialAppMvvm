package net.raj.testsocialapp.ui.home

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.raj.testsocialapp.ui.Repository

class HomeActivityViewModel : ViewModel() {
    private val repository = Repository()
    lateinit var postCollection : CollectionReference
    lateinit var query : Query

    fun updatePostLikes(postId : String){
        GlobalScope.launch(Dispatchers.IO) {
            repository.updatePostLikes(postId)
        }
    }

    fun updateUtilities(){
       postCollection = repository.getPostCollectionReference()
        query = postCollection.orderBy("createdAt", Query.Direction.DESCENDING)
    }
}