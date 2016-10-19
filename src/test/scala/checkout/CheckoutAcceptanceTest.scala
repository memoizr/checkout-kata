package checkout

import org.scalatest.{FlatSpec, Matchers}

class CheckoutAcceptanceTest extends FlatSpec with Matchers {
  import Offer._
  import checkout.Currency._

  "Items" should "have a set price" in {
    ChickenWings.price shouldBe GBP(2.25)
    Bacon.price shouldBe GBP(1.50)
  }

  "Checkout" should "calculate the total price of items in basket" in {
    val basket = Basket(ChickenWings, ChickenWings, Bacon, ChickenWings)

    Checkout(basket).totalCost shouldBe (ChickenWings.price * 3) + (Bacon.price * 1)
  }

  it should "support n for m offers and calculate total price" in {
    val mixedBasket = Basket(Bacon, ChickenWings, ChickenWings, ChickenWings)
    val anotherBasket = Basket(Bacon, Bacon, Bacon, Bacon)

    val offers = Offers(
      (Bacon, 3 forThePriceOf 2),
      (ChickenWings, 2 forThePriceOf 1)
    )

    Checkout(mixedBasket, offers).totalCost shouldBe (Bacon.price * 1) + (ChickenWings.price * 2)
    Checkout(anotherBasket, offers).totalCost shouldBe (Bacon.price * 3)
  }

  it should "calculate a cost of 0 for no items" in {
    Checkout(Basket()).totalCost shouldBe GBP(0)
  }
}
