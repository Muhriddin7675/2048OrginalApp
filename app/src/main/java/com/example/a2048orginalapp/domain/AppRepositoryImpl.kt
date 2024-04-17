package com.example.a2048orginalapp.domain

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.random.Random


class AppRepositoryImpl {
    private var score = 0
    private var scoreBack = 0
    private var backBool = true
    private var pref: SharedPreferences = getPref()
    private var matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
    )
    private var matrixBack = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
    )
    private var addAmount = 2


    companion object {
        private lateinit var pref: SharedPreferences

        fun init(context1: Context): SharedPreferences {
            if (!(::pref.isInitialized)) {
                pref = context1.getSharedPreferences("Game2048", Context.MODE_PRIVATE)
            }
            return pref
        }

        fun getPref(): SharedPreferences {
            return pref
        }

        private lateinit var instance: AppRepositoryImpl

        fun getInstance(): AppRepositoryImpl {
            if (!(::instance.isInitialized)) instance = AppRepositoryImpl()
            return instance
        }
    }

    init {
        addNewElement()
        addNewElement()
        matrixBack = matrix
    }

    fun getMatrix(): Array<Array<Int>> = matrix
    fun getScore(): Int {
        return score
    }

    fun baskVisible(): Boolean {
        return backBool
    }

    fun goBack() {
        if (backBool) {
            matrix = matrixBack
            if (score >= scoreBack) {
                score -= scoreBack
            }
            backBool = false
        }
    }

    private fun createBasicMatrix() = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
    )

    fun moveUp() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean
        scoreBack = 0
        backBool = true


        for (i in matrix.indices) {
            index = 0
            isAdded = false
            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (newMatrix[0][i] == 0) {
                    newMatrix[0][i] = matrix[j][i]
                    continue
                }

                if (newMatrix[index][i] == matrix[j][i] && !isAdded) {
                    newMatrix[index][i] *= 2
                    scoreBack += newMatrix[index][i]
                    score += newMatrix[index][i]
                    isAdded = true
                } else {
                    newMatrix[++index][i] = matrix[j][i]
                    isAdded = false
                }
            }

        }
        matrixBack = matrix
        matrix = newMatrix
        addNewElement()

//        if (isScoreUpdated()) {
////            score += newElementValue
//            saveGameData()
//        }
//
        finish()
    }

    fun moveRight() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean
        backBool = true
        scoreBack = 0

        for (i in matrix.indices) {
            index = 3
            isAdded = false

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][3] == 0) {
                    newMatrix[i][3] = matrix[i][j]
                    continue
                }

                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *= 2
                    scoreBack += newMatrix[index][i]
                    score += newMatrix[index][i]
                    isAdded = true
                } else {
                    newMatrix[i][--index] = matrix[i][j]
                    isAdded = false
                }
            }
        }
        matrixBack = matrix
        matrix = newMatrix
        addNewElement()

        finish()
    }

    fun moveDown() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean
        backBool = true
        scoreBack = 0

        for (i in matrix.indices) {
            index = 3
            isAdded = false

            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (newMatrix[3][i] == 0) {
                    newMatrix[3][i] = matrix[j][i]
                    continue
                }

                if (newMatrix[index][i] == matrix[j][i] && !isAdded) {
                    newMatrix[index][i] *= 2
                    scoreBack += newMatrix[index][i]
                    score += newMatrix[index][i]
                    isAdded = true
                } else {
                    newMatrix[--index][i] = matrix[j][i]
                    isAdded = false
                }
            }
        }
        matrixBack = matrix
        matrix = newMatrix
        addNewElement()

//        if (isScoreUpdated()) {
////            score += newElementValue
//            saveGameData()
//        }
//
        finish()
    }

    fun moveLeft() {
        val newMatrix = createBasicMatrix()
        var index: Int
        var isAdded: Boolean
        backBool = true
        scoreBack = 0

        for (i in matrix.indices) {
            index = 0
            isAdded = false

            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (newMatrix[i][0] == 0) {
                    newMatrix[i][0] = matrix[i][j]
                    continue
                }

                if (newMatrix[i][index] == matrix[i][j] && !isAdded) {
                    newMatrix[i][index] *= 2
                    scoreBack += newMatrix[i][index]
                    score += newMatrix[i][index]
                    isAdded = true
                } else {
                    newMatrix[i][++index] = matrix[i][j]
                    isAdded = false
                }
            }
        }
        matrixBack = matrix
        matrix = newMatrix
        addNewElement()

//        if (isScoreUpdated()) {
////            score += newElementValue
//            saveGameData()
//        }
//
        finish()
    }

    fun saveMaxScore(maxScore: Int) {
        pref.edit().putInt("MaxScore", maxScore).apply()
    }

    fun getMaxScore(): Int {
        return pref.getInt("MaxScore", 0)
    }

    fun saveData() {
        pref.edit().putInt("Score", score).apply()

        val st = matrix.flatten().joinToString("#")
        pref.edit().putString("Matrix", st).apply()
    }

    fun loadData() {
        score = pref.getInt("Score", 0)
        matrix = loadLast()
    }

    private fun loadLast(): Array<Array<Int>> {
        val matrixString = pref.getString("Matrix", "")
        return if (matrixString.isNullOrEmpty()) {
            Array(4) { Array(4) { 0 } }
        } else {
            val matrixValues = matrixString.split("#").map { it.toInt() }
            val matrixSize = 4
            Array(matrixSize) { i ->
                Array(matrixSize) { j ->
                    matrixValues[i * matrixSize + j]
                }
            }
        }
    }

    private fun addNewElement() {
        val empty = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) empty.add(Pair(i, j))
            }
        }
        if (empty.isEmpty()) return
        val randomIndex = Random.nextInt(0, empty.size)
        val findPairByRandomIndex = empty[randomIndex]
        matrix[findPairByRandomIndex.first][findPairByRandomIndex.second] = addAmount
    }

    fun restart() {
        matrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
        )
        score = 0
        addNewElement()
        addNewElement()
    }

    fun finish(): Boolean {
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) return false
            }
        }
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (j < matrix[i].size - 1 && matrix[i][j] == matrix[i][j + 1]) return false
                if ((i < matrix.size - 1) && (matrix[i][j] == matrix[i + 1][j])) {
                    return false
                }
            }
        }
        return true
    }

}