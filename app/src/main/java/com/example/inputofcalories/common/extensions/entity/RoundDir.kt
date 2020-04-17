package com.example.inputofcalories.common.extensions.entity

sealed class RoundDir

object LEFT: RoundDir()

object TOP: RoundDir()

object RIGHT: RoundDir()

object BOTTOM: RoundDir()