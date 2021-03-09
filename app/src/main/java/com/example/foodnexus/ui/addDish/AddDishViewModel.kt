package com.example.foodnexus.ui.addDish

import androidx.lifecycle.ViewModel

class AddDishViewModel : ViewModel() {

    fun isDataNotFilled(text: String): Boolean {
        return text.isBlank()
    }
}