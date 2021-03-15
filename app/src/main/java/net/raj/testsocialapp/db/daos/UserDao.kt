package net.raj.testsocialapp.db.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.raj.testsocialapp.models.User

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection  = db.collection("users")

    suspend fun addUser(user: User){
        userCollection.document(user.uid).set(user)
    }

    fun getUserById(uId : String): Task<DocumentSnapshot>{
        return userCollection.document(uId).get()
    }
}