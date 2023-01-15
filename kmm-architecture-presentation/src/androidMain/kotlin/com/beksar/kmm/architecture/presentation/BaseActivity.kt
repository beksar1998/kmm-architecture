package com.beksar.kmm.architecture.presentation

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(layoutId: Int) : AppCompatActivity(layoutId) {

    private val page = this::class.simpleName.orEmpty()

}