package com.example.testandroid.module.testroom.viewmodel

import androidx.lifecycle.*
import com.example.testandroid.base.BaseViewModel
import com.example.testandroid.module.testroom.model.Word
import com.example.testandroid.module.testroom.model.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : BaseViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun inset(word: Word) = viewModelScope.launch {
        repository.inset(word)
    }
}

class WordViewModelFactory(private val repository: WordRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}