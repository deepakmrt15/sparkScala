package com.ds.examples

class CompanionClass{
  def hello(){
    println("Hello, this is Companion Class.")
  }
}

object CompanionObject{
  def main(args:Array[String]){
    new CompanionClass().hello()
    println("And this is Companion Object.")


    var myVar :Int = 10;
    val myVal :String = "Hello Scala with datatype declaration.";

    var myVar1 = 20;
    val myVal1 = "Hello Scala new without datatype declaration.";

    println(myVar); println(myVal); println(myVar1);
    println(myVal1);

  }
}