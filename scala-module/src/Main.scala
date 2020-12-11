object Main {
  def main(args: Array[String]): Unit = {
    println("hello scala")
    print(hello("hello method"))
  }

  def hello(name: String) : Int = {
    println(name)
    5
  }

}
