package com.example.listadinamicaheroes
//data class representa datos consistentes
data class Heroe(var name:String, var alterEgo:String, var url:String) {
    fun getFullName():String = "$name is $alterEgo"

}