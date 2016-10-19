package checkout
import Currency._

object Bacon extends Item {
  override def price = GBP(1.50)
}

object ChickenWings extends Item {
  override def price = GBP(2.25)
}
