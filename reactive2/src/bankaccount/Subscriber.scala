package bankaccount

trait Subscriber {
  def handler(pub: Publisher)
}