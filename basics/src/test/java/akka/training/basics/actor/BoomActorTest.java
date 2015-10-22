package akka.training.basics.actor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.typesafe.config.ConfigFactory;

import akka.actor.Actor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.TestKit;

public class BoomActorTest extends TestKit {

    static ActorSystem actorSystem = ActorSystem.create("actor-system-test", ConfigFactory.load());

    public BoomActorTest() {
        super(actorSystem);
    }

    @Test
    public void testBoomActorReceivingStringMessage() {
        final TestActorRef<Actor> child = TestActorRef.apply(Props.create(BoomActor.class), actorSystem);
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
        final TestActorRef<Actor> child = TestActorRef.apply(Props.create(BoomActor.class), actorSystem);
        try {
            child.receive(123);
            // should not reach here
            fail();
        } catch (final NullPointerException e) {
            assertEquals(e.getMessage(), "caput");
        }
    }
}
