package com.example.codingexcercise.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codingexcercise.model.data.MusicItem
import com.example.codingexcercise.model.network.MusicAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MusicViewModel: ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val musicAPI: MusicAPI = MusicAPI.initRetrofit()

    val musicLiveData: MutableLiveData<List<MusicItem>> = MutableLiveData()
    val errorData: MutableLiveData<String> = MutableLiveData()

    fun getMusic(searchQuery: String){
        Log.d("TAG_X", "info obtained.")
        compositeDisposable.add(
            musicAPI.getMeSongs(searchQuery)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map {
                    it.results
                }
                .subscribe({ response ->
                    Log.d("TAG_X", "result obtained. 3 . ${response.size}")
                    if(response.isNotEmpty())
                        musicLiveData.postValue(response)
                    else
                        errorData.postValue("Results not found.")

                    clearDisposable()
                }, { error ->
                    errorData.postValue(error.localizedMessage)
                })
        )

    }

    private fun clearDisposable() {
       compositeDisposable.clear()
    }
}