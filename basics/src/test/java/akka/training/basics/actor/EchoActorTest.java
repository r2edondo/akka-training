package akka.training.basics.actor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;

public class EchoActorTest {

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
    public void testEchoActorRepliesWithTheSameStringItRecieves() {
        new JavaTestKit(system) {

            {
                final Props props = Props.create(EchoActor.class);
                final ActorRef subject = system.actorOf(props);
                subject.tell("Hi", getRef());
                expectMsgEquals("Hi");
            }
        };

    }
}