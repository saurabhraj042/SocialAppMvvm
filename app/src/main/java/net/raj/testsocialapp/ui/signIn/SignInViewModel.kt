package net.raj.testsocialapp.ui.signIn
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import net.raj.testsocialapp.models.User
import net.raj.testsocialapp.ui.Repository

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private  val repository : Repository
    var auth : FirebaseAuth

    private  val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser>
        get() = _firebaseUser

    init {
        repository = Repository()
        auth = Firebase.auth
    }


    fun addUser(firebaseUser: FirebaseUser){
        val user = User(firebaseUser.uid,firebaseUser.displayName,firebaseUser.photoUrl.toString())
        GlobalScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun firebaseAuthWithGoogleHelper(idToken : String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        GlobalScope.launch(Dispatchers.IO) {
            val auth = auth.signInWithCredential(credential).await()

            withContext(Dispatchers.Main){
                _firebaseUser.value = auth.user
            }
        }
    }

}