package waslim.binar.andlima.classdiscussions.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataStore : DataStore<Preferences> = context.createDataStore(name = "data_user")

    companion object{
        val USERNAME = preferencesKey<String>("USERNAME")
        val PASSWORD = preferencesKey<String>("PASSWORD")
        val NAME = preferencesKey<String>("NAME")
        val UMUR = preferencesKey<String>("UMUR")
        val IMAGE = preferencesKey<String>("IMAGE")
        val ADDRESS = preferencesKey<String>("ADDRESS")
    }

    suspend fun saveData(username: String, password: String, name: String, umur: String, image: String, address: String){
        dataStore.edit {
            it[USERNAME] = username
            it[PASSWORD] = password
            it[NAME] = name
            it[UMUR] = umur
            it[IMAGE] = image
            it[ADDRESS] = address

        }
    }

    val username : Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val password : Flow<String> = dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val name : Flow<String> = dataStore.data.map {
        it[NAME] ?: ""
    }

    val umur : Flow<String> = dataStore.data.map {
        it[UMUR] ?: ""
    }

    val image : Flow<String> = dataStore.data.map {
        it[IMAGE] ?: ""
    }

    val address : Flow<String> = dataStore.data.map {
        it[ADDRESS] ?: ""
    }


}