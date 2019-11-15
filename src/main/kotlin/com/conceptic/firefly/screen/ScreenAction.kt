package com.conceptic.firefly.screen

sealed class ScreenAction

data class SizeChangeAction(val width: Int, val height: Int): ScreenAction()

object InitAction: ScreenAction()
object ShowAction: ScreenAction()
object UpdateAction: ScreenAction()
object DestroyAction: ScreenAction()