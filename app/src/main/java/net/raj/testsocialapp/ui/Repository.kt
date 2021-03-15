package net.raj.testsocialapp.ui

import com.google.firebase.firestore.CollectionReference
import net.raj.testsocialapp.db.daos.PostDao
import net.raj.testsocialapp.db.daos.UserDao
import net.raj.testsocialapp.models.User

class Repository {
    private val postDao = PostDao()
    private  val userDao = UserDao()

    suspend fun addPost(text : String){
        postDao.addPost(text)
    }

    suspend fun updatePostLikes(postId : String){
        postDao.updateLikes(postId)
    }

    fun getPostById(postId: String){
        postDao.getPostById(postId)
    }

    suspend fun addUser(user : User){
        userDao.addUser(user)
    }

    fun getUserById(uId : String){
        userDao.getUserById(uId)
    }

    fun getPostCollectionReference() : CollectionReference{
       return postDao.getPostCollectionReference()
    }
}