package latelatency

import scala.util.Try
import java.time.Duration

trait Awaitable[T] extends AnyRef{
  def ready(atMost: Duration): Unit
  def result(atMost: Duration): T
}

trait Future[T] extends Awaitable[T]{
  
  def filter(p: T => Boolean): Future[T]
  def flatMap[S](f: T => Future[S]): Future[S]
  def map[S](f: T => S): Future[S]
  def recoverWith(f: PartialFunction[Throwable, Future[T]]): Future[T]
  
  def onComplete(callback: Try[T] => Unit)
  
  val socket = Socket()
  val packet: Future[Array[Byte]] = socket.readFromMemory()

  val confirmation: Future[Array[Byte]] = packet.flatMap { p => socket.sendToEurope(p) }
  
}

object Future{
  def apply[T](body: => T): Future[T]
}

trait Socket{
  
  def readFromMemory()
}

