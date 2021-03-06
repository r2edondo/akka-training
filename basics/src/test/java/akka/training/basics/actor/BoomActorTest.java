package akka.training.basics.actor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.Actor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;

public class BoomActorTest {

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
    public void testBoomActorReceivingStringMessage() {
        final TestActorRef<Actor> child = TestActorRef.apply(Props.create(BoomActor.class), system);
        try {
            child.receive("do something");
            // should not reach here
            fail();
        } catch (final IllegalArgumentException e) {
            assertEquals(e.getMessage(), "boom!");
        }
    }

    @Test
    public void testBoomActorReceivingIntegerMessage() {
        final TestActorRef<Actor> child = TestActorRef.apply(Props.create(BoomActor.class), system);
        try {
            child.receive(123);
            // should not reach here
            fail();
        } catch (final NullPointerException e) {
            assertEquals(e.getMessage(), "caput");
        }
    }

}
