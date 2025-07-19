package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    var text: String? = null
//    text = null
//
//    if (text != null) {
//        println(text.length)
//    } else {
//        println("The variable is null")
//    }

    val text2 = text ?: "Some text is case null."

    println(text2)

    var text3 = ""

    if (text3 != null) {
        println("This text3 variable is not null")
    } else {
        println("This text3 variable is null")
    }
}