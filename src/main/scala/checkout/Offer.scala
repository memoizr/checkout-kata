package checkout

case class Offer(total: Int, forPriceOf: Int) {

  def reduced(numberOfItems: Int): Int = {
    val priceUnderOffer = (numberOfItems / total) * forPriceOf
    val priceOfRest = numberOfItems % total
    priceUnderOffer + priceOfRest
  }
}

object Offer {

  type Offers[T <: Item] = Map[T, Offer]
  val Offers = Map

  object Default extends Offer(1, 1) {
    override def reduced(numberOfItems: Int) = numberOfItems
  }

  implicit class OfferOps(numberOfItems: Int) {
    def forThePriceOf(forPriceOf: Int): Offer = Offer(numberOfItems, forPriceOf)
  }
}