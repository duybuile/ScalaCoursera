package week8

trait Generator[+T] {
  def generate: T
}