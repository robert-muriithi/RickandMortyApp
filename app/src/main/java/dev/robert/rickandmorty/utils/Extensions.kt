package dev.robert.rickandmorty.utils

import android.content.Context

fun String.extractId() = this.substringAfter("character").replace("/","").toInt()
fun show(applicationContext: Context?, tag: String?){}