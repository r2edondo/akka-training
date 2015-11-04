package akka.training.basics.actor

import org.junit.runner.RunWith
import akka.testkit.TestKit
import akka.actor.ActorSystem
import akka.testkit.ImplicitSender
import akka.actor.Props
import org.scalatest.junit.JUnitRunner
import com.typesafe.config.ConfigFactory
import org.scalatest.BeforeAndAfterAll
import org.scalatest.junit.ShouldMatchersForJUnit
import org.scalatest.MustMatchers
import org.scalatest.WordSpec
import org.scalatest.WordSpecLike

@RunWith(classOf[JUnitRunner])
class EchoActorScalaTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
    with WordSpecLike with MustMatchers with BeforeAndAfterAll  {

  def this() = this(ActorSystem("TestSys", ConfigFactory
    .load().getConfig("TestSys")))

  "Test Echo actor" must {
    "send back messages unchanged" in {
      val echo = system.actorOf(Props[EchoActor])
      echo ! "Hi there"
      expectMsg("Hi there")
    }
  }
}