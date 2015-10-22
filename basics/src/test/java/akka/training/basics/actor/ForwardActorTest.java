package akka.training.basics.actor;

import org.junit.Test;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;

public class ForwardActorTest extends TestKit {

    static ActorSystem system = ActorSystem.create("actor-system-test", ConfigFactory.load());

    public ForwardActorTest() {
        super(system);
    }

    @Test
    public void testForwardingActorForwardsTheMessageSuccessfully() {
        final ActorRef forwardingActorRef = system.actorOf(Props.create(ForwadingActor.class, super.testActor()));
        forwardingActorRef.tell("testActor should recieve this message",null);
        expectMsg("testActor should recieve this message");
    }
}
