package dev.robert.rickandmorty.utils

fun String.extractId() = this.substringAfter("character").replace("/","").toInt()