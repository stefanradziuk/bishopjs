package uk.radzi

import scala.util.Random
import scala.scalajs.js.timers

object Bishop:

  private val chars =
    List(' ', '.', 'o', '+', '=', '*', 'B', '0', 'X', '@', '%', '&', '#', '/', '^')

  private val width = 7
  private val height = 9
  private val bounds = (width, height)
  private val interval = 600

  private def intToChr(x: Int): Char = chars(Math.min(x, chars.length - 1))

  private def applyDelta(pos: (Int, Int), delta: (Int, Int)): (Int, Int) =
    (pos, delta, bounds) match {
      case ((posX, posY), (dX, dY), (boundX, boundY)) =>
        (applyDelta1D(posX, dX, boundX), applyDelta1D(posY, dY, boundY))
    }

  private def applyDelta1D(pos: Int, delta: Int, bound: Int): Int =
    Math.max(Math.min(pos + delta, bound - 1), 0)

  private def getNextDelta(rng: Random): (Int, Int) =
    (
      if (rng.nextBoolean()) 1 else -1,
      if (rng.nextBoolean()) 1 else -1
    )

  def main(args: Array[String]): Unit =
    val rng = new Random()
    val table: Array[Array[Int]] = Array.fill(width, height)(0)
    var pos = (width / 2, height / 2)

    timers.setInterval(interval) {
      val delta = getNextDelta(rng)
      pos = applyDelta(pos, delta)
      table(pos._1)(pos._2) += 1

      println(table.map(row => row.map(intToChr).mkString(" ")).mkString("\n"))
    }

