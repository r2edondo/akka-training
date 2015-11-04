package akka.training.basics.actor;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;

public class FilteringActorTest {

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
    public void testFilteringActor() {
        new JavaTestKit(system) {

            {
                final Props props = Props.create(FilteringActor.class);
                final ActorRef subject = system.actorOf(props);
                subject.tell("test message", getRef());
                expectMsgEquals("test message");
                subject.tell(1, getRef());
                expectNoMsg();
            }
        };

    }
}
