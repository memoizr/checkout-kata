package checkout

import checkout.Offer.Offers
import checkout.Currency.GBP

class Checkout[+T <: Item](basket: Basket[T], deals: Offers[T]) {

  def totalCost: GBP = basket.items
    .groupBy(identity)
    .foldLeft(GBP(0)) { (total, groupedItems) => {
      val (item, itemList) = groupedItems

      val offer = deals.getOrElse(item, Offer.Default)

      val numberOfItems: Int = itemList.length
      val reducedPrice = offer.reduced(numberOfItems) * item.price

      total + reducedPrice
    }
    }
}

object Checkout {

  def apply[T <: Item](basket: Basket[T], deal: Offers[T] = Offers[T, Offer]()) = new Checkout(basket, deal)
}

case class Basket[+T <: Item](items: T*)

trait Item {
  def price: GBP
}
