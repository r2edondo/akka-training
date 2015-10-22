package akka.training.basics.actor;

import org.junit.Test;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;

public class FilteringActorTest extends TestKit {

    static ActorSystem system = ActorSystem.create("actor-system-test", ConfigFactory.load());

    public FilteringActorTest() {
        super(system);
    }

    @Test
    public void testFilteringActor() {
        final ActorRef filteringActorRef = system.actorOf(Props.create(FilteringActor.class));
        filteringActorRef.tell("test message", super.testActor());
        expectMsg("test message");
        filteringActorRef.tell(1, super.testActor());
        expectNoMsg();
    }
}
