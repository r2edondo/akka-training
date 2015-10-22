package akka.training.basics.actor;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class SupervisorActorTest  {

    private static ActorSystem actorSystem;

    private JavaTestKit probe;

    @BeforeClass
    public static void setUpClass() {
        actorSystem = ActorSystem.create("actor-system-test", ConfigFactory.load());
    }

    @Before
    public void setUp(){
        this.probe = new JavaTestKit(actorSystem);
    }

    @AfterClass
    public static void tearDownClass() {
        JavaTestKit.shutdownActorSystem(actorSystem);
        actorSystem = null;
    }


    @Test
    @Ignore
    public void testSupervisorStrategyWhenChildIsResumed() throws Exception {
        final ActorRef supervisor = actorSystem.actorOf(Props.create(SupervisorActor.class), "supervisor1");
        final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        final Future<Object> future = Patterns.ask(supervisor, Props.create(BoomActor.class), timeout);
        final ActorRef child = (ActorRef) Await.result(future, timeout.duration());
        child.tell(123, null);
        this.probe.watch(child);
        final Terminated msg = this.probe.expectMsgClass(Terminated.class);
        assertEquals(msg.getActor(), child);
    }


    @Test
    public void testSupervisorStrategyWhenChildIsStopped() throws Exception {
        final ActorRef supervisor = actorSystem.actorOf(Props.create(SupervisorActor.class), "supervisor2");
        final Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
        final Future<Object> future = Patterns.ask(supervisor, Props.create(BoomActor.class), timeout);
        final ActorRef child = (ActorRef) Await.result(future, timeout.duration());
        this.probe.watch(child);
        child.tell("do something",null);
        final Terminated msg = this.probe.expectMsgClass(Terminated.class);
        assertEquals(msg.getActor(), child);
    }
}
