package coinyser

import java.sql.Timestamp
import coinyser.StreamingProducerSpec._
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.{Matchers, WordSpec}

class StreamingProducerSpec extends WordSpec with Matchers with TypeCheckedTripleEquals {
  "StreamingProducer.deserializeWebsocketTransaction" should {
    "deserialize a valid String to a WebsocketTransaction" in {
      val str =
        """{"microtimestamp":"12","amount": 0.045318270000000001, "buy_order_id": 1969499130,
          |"sell_order_id": 1969495276, "amount_str": "0.04531827",
          |"price_str": "6339.73", "timestamp": "1533797395",
          |"price": 6339.7299999999996, "type": 0, "id":
          71826763}""".stripMargin
      StreamingProducer.deserializeWebsocketTransaction(str) should
        ===(SampleWebsocketTransaction)
    }
  }
}

object StreamingProducerSpec {
  val SampleWebsocketTransaction = WebsocketTransaction( microtimestamp = "12",
    amount = 0.04531827, buy_order_id = 1969499130, sell_order_id =
      1969495276, amount_str = "0.04531827", price_str = "6339.73",
    timestamp = "1533797395", price = 6339.73, `type` = 0, id =
      71826763)
}