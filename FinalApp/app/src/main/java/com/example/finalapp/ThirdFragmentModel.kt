package com.example.finalapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import req
import kotlin.concurrent.thread


class ThirdFragmentModel : ViewModel() {

    val data = MutableLiveData<Games>()
    val errorMsg = MutableLiveData("")
    val runInProgress = MutableLiveData(false)

    fun loadData(gameName: String) {
        data.postValue(null)

        thread {
            try {
                data.postValue(req.loadGames(gameName))
            } catch (e: Exception) {
                e.printStackTrace();
                errorMsg.postValue("Une erreur est survenue : ${e.message}")
            }

        }
        runInProgress.postValue(false);
    }

}