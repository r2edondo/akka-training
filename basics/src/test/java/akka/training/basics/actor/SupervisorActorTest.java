package akka.training.basics.actor;

import static org.junit.Assert.assertFalse;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class SupervisorActorTest {

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
    public void testSupervisorStrategyWhenChildIsStopped() throws Exception {
        new JavaTestKit(system) {

            {
                final Props props = Props.create(SupervisorActor.class);
                final ActorRef subject = system.actorOf(props);
                // create a BoomActor child actor supervised by the SupervisorActor
                final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
                final Future<Object> future = Patterns.ask(subject, Props.create(BoomActor.class), timeout);
                final ActorRef child = (ActorRef) Await.result(future, timeout.duration());
                watch(child);
                child.tell("123", null);
                expectTerminated(Duration.create(1, TimeUnit.SECONDS), child);
            }
        };

    }

    @Test
    public void testSupervisorStrategyWhenChildIsResumed() throws Exception {
        new JavaTestKit(system) {

            {
                final Props props = Props.create(SupervisorActor.class);
                final ActorRef subject = system.actorOf(props);
                // create a BoomActor child actor supervised by the SupervisorActor
                final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
                final Future<Object> future = Patterns.ask(subject, Props.create(BoomActor.class), timeout);
                final ActorRef child = (ActorRef) Await.result(future, timeout.duration());
                child.tell(123, null);
                // FIXME: assert that the child actor has been resumed
                assertFalse(child.isTerminated());

            }
        };

    }

}
