package com.example.finalapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import req
import kotlin.concurrent.thread

class FirstFragmentModel: ViewModel() {
    val data = MutableLiveData<Double>()
    val errorMsg = MutableLiveData("")
    val runInProgress = MutableLiveData(false)

    fun loadData(from: String, to: String, quantity: Double) {
        //On reset des données
        data.postValue(null)
        errorMsg.postValue(null);
        //Tache asynchrone en cours
        runInProgress.postValue(true);

        thread {
            try {
                var response = req.exchange(from, to, quantity)
                data.postValue(req.ret(quantity, response));
            } catch (e: Exception) {
                e.printStackTrace();
                //j'ai une erreur
                errorMsg.postValue("Une erreur est survenue : ${e.message}")
            }
            //Tache asynchrone terminée
            runInProgress.postValue(false);
        }
    }
}