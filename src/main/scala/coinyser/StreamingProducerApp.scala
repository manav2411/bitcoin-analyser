package coinyser

import cats.effect.{ExitCode, IO, IOApp}
import com.pusher.client.Pusher
import StreamingProducer._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.collection.JavaConversions._

class StreamingProducerApp extends IOApp {
  val topic = "transactions"
  val pusher = new Pusher("de504dc5763aeef9ff52")

  val props = Map(
    "bootstrap.servers" -> "localhost:9092",
    "key.serializer" ->
      "org.apache.kafka.common.serialization.IntegerSerializer",
    "value.serializer" ->
      "org.apache.kafka.common.serialization.StringSerializer")

  def run(args :List[String]):IO[ExitCode]={
    val kafkaProducer = new KafkaProducer[Int, String](props)

        subscribe(pusher){ wsTx =>
      val tx =  convertWsTransaction(deserializeWebsocketTransaction(wsTx))

      val jsonTxs = serializeTransaction(tx)
          println(jsonTxs)
      kafkaProducer.send(new ProducerRecord(topic, tx.tid, jsonTxs))

    }.flatMap(_ => IO.never)

  }



}

//object BatchProducerAppSpark extends StreamingProducerApp


//object BatchProducerAppIntelliJ extends  StreamingProducerApp