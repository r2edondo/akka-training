package akka.training.basics.actor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;

public class ForwardActorTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        JavaTestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testForwardingActorForwardsTheMessageSuccessfully() {
        new JavaTestKit(system) {

            {
                final Props props = Props.create(ForwadingActor.class, getRef());
                final ActorRef subject = system.actorOf(props);
                subject.tell("testActor should recieve this message", null);
                expectMsgEquals("testActor should recieve this message");
            }
        };

    }
}
