package com.example.a2048orginalapp.presenter.screen.game


import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import com.example.a2048orginalapp.domain.AppRepositoryImpl

class GameViewModel : ViewModel() {

    private val views = ArrayList<AppCompatTextView>(16)
    private val repository = AppRepositoryImpl.getInstance()
//    var matrix = MutableLiveData<Array<Array<Int>>>()

    fun getMatrix(): Array<Array<Int>> {
        return repository.getMatrix()
    }

    fun moveRight() {
        repository.moveRight()
    }

    fun moveLeft() {
        repository.moveLeft()
    }

    fun moveUp() {
        repository.moveUp()
    }

    fun moveDown() {
        repository.moveDown()
    }

    fun getScore(): Int {
        return repository.getScore()
    }

    fun saveMaxScore(maxScore: Int) {
        repository.saveMaxScore(maxScore)
    }

    fun getMaxScore(): Int {
        return repository.getMaxScore()
    }

    fun saveDate() {
        repository.saveData()
    }

    fun loadDate() {
        repository.loadData()
    }

    fun restart() {
        repository.restart()
    }
    fun  finish():Boolean = repository.finish()

    fun goBack(){
        repository.goBack()
    }
    fun backVisible():Boolean{
        return repository.baskVisible()
    }


}















