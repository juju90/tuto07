/* SimpleApp.scala */
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import Java.io.StringReader
import au.com.bytecode.opencsv.CSVReader

object Csv {
  def main(args: Array[String]) {
    //val logFile = "YOUR_SPARK_HOME/README.md" // Should be some file on your system
    val logFile = "/vagrant/src/test.csv" // Should be some file on your system
    val conf = new SparkConf().setAppName("Word Count")
    val sc = new SparkContext(conf)

    val input = sc.textFile(logFile)
    val result = input.map{ line =>
    val reader = new CSVReader(new StringReader(line));
      reader.readNext();
    }

    // load data
    val logData = sc.textFile(logFile, 2).cache()
    // split it up into words
    val words = logData.flatMap(line => line.split(" "))
    // transform into pairs and count
    //val counts = words.map(word => (word, 1)).reduceByKey{case (x, y) => x + y}
    val counts = logData.flatMap(line => line.split(" ")).map(word => (word, 1))
    //val counts = logData.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    val numCs = logData.filter(line => line.contains("c")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs, Lines with c: $numCs")
    println(s"Numbers of words : $counts")
    sc.stop()
  }
}