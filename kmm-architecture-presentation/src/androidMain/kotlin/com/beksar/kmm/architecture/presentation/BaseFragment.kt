package com.beksar.kmm.architecture.presentation

import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    private val page = this::class.simpleName.orEmpty()

}