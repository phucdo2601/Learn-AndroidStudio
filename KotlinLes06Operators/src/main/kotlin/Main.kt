fun main() {
    var x = 5
    var y = 3
    var result = x + y
//    println("result = $result")

    println("x + y = ${x + y}")
    println("x - y = ${x - y}")
    println("x * y = ${x * y}")
    println("x / y = ${x / y}")
    println("x % y = ${x % y}")

    result = 7 + 4
    println("result = $result")
    result += 9
    println("result = $result")
    result *= 3
    println("result = $result")
    result /= 2
    println("result = $result")
    result %= 7
    println("result = $result")

    y = 3
    println("the value of y++: ${y++}")
    println("the value of ++y: ${++y}")
    println("the value of y--: ${y--}")
    println("the value of --y: ${--y}")

    val isActive = true
    if (isActive) {
        println("The condition is true.")
    } else {
        println("The condition is false.")
    }
}
