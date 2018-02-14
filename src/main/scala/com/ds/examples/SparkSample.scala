package com.ds.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import scala.collection.Map


class SparkSample(sc: SparkContext) {
  def run(t: String, u: String) : RDD[(String, String)] = {
    val transactions = sc.textFile(t)
    val newTransactionsPair = transactions.map{t =>
      val p = t.split("\t")
      (p(2).toInt, p(1).toInt)
    }

    val users = sc.textFile(u)
    val newUsersPair = users.map{t =>
      val p = t.split("\t")
      (p(0).toInt, p(3))
    }

    val result = processData(newTransactionsPair, newUsersPair)
    val to = sc.parallelize(result.toSeq).map(t => (t._1.toString, t._2.toString))
    println("===>> "+to.getNumPartitions)
    return sc.parallelize(result.toSeq).map(t => (t._1.toString, t._2.toString))
  }

  def processData (t: RDD[(Int, Int)], u: RDD[(Int, String)]) : Map[Int,Long] = {
    var jn = t.leftOuterJoin(u).values.distinct
    return jn.countByKey
  }
}

object SparkSample {

  def main(args: Array[String]) {
    val transactionsIn = "transaction.text"//args(1)
    val usersIn = "user.text"
    val conf = new SparkConf().setAppName("SparkJoins").setMaster("local")
    val context = new SparkContext(conf)
    val job = new SparkSample(context)
    val results = job.run(transactionsIn, usersIn)
    val output = "output.text"//args(2)

    val rdd= context.textFile(usersIn, 4)
    val rdd1= context.parallelize(usersIn,10)

    println(" parallelize: "+rdd1.getNumPartitions)

    //results.saveAsTextFile(output)
    context.stop()
  }
}
