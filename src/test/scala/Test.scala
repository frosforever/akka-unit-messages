
import akka.actor.{Actor, ActorSystem}
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.WordSpecLike
import scala.concurrent.duration._

class SerializableTest extends TestKit(ActorSystem("SerializableTest", ConfigFactory.parseString(
  """akka.actor.serialize-messages = on
    |akka.loglevel = "OFF"
  """.stripMargin))) with WordSpecLike with ImplicitSender {

  "ActorSystem with serialization" should {
    val unitActor = TestActorRef[UnitActor]

    "fail to send Unit type" in {
      unitActor ! Unit
      within(1 second){
        expectNoMsg()
      }
    }
    "send unit value" in {
      unitActor ! ()
      within(1 second){
        expectMsg("Received Unit value")
      }
    }
  }
}

class NoSerializableTest extends TestKit(ActorSystem("SerializableTest", ConfigFactory.parseString(
  """akka.loglevel = "OFF"
  """.stripMargin))) with WordSpecLike with ImplicitSender {

  "ActorSystem without requiring serialization" should {
    val unitActor = TestActorRef[UnitActor]

    "send Unit type" in {
      unitActor ! Unit
      within(1 second){
        expectMsg("Received Unit Type")
      }
    }
    "send unit value" in {
      unitActor ! ()
      within(1 second){
        expectMsg("Received Unit value")
      }
    }
  }
}

class UnitActor extends Actor {
  override def receive: Receive = {
    case Unit => sender ! "Received Unit Type"
    case () => sender ! "Received Unit value"
  }
}

