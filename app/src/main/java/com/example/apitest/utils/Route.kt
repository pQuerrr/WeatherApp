package com.example.apitest.utils

import com.example.apitest.utils.Const.MAIN_SCREEN

sealed class Route(val route: String){
    object MainScreen: Route(MAIN_SCREEN)
}

object Const{
    const val MAIN_SCREEN = "mainscreen"
}