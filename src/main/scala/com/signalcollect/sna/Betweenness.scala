package com.signalcollect.sna

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.SynchronizedBuffer

import com.signalcollect.DataGraphVertex
import com.signalcollect.DefaultEdge

object Betweenness /*extends App*/ {
  var vertexIds = Set[Int]()
  def run: ExecutionResult = {
    val vertexArray = PathTester.run
    val shortestPathList = PathTester.allShortestPathsAsList
    vertexIds = PathTester.allShortestPathsAsMap.keySet
    val betweennessMap = getBetweennessForAll(shortestPathList)
    val bla = new ComputationResults(0.0, betweennessMap)
    val compres = new ExecutionResult(bla, vertexArray)
    compres
  }
  // TODO: implement closeness just as computation and assign paths to vertices

  def getBetweennessForAll(shortestPathList: List[Path]): java.util.Map[String, Object] = {
    var betweennessMap = new java.util.TreeMap[String, Object]
    for (s <- vertexIds) {
      val pathsThroughVertex = shortestPathList.filter(p => p.sourceVertexId != s && p.targetVertexId != s && p.path.contains(s))
      val betweenness = BigDecimal(pathsThroughVertex.size.toDouble/shortestPathList.size.toDouble).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      betweennessMap.put(s.toString, betweenness.asInstanceOf[Object])
      println(s + " " + betweenness)
    }
    betweennessMap
  }
}