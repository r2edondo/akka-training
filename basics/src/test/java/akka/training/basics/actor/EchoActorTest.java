package akka.training.basics.actor;

import org.junit.Test;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;

public class EchoActorTest extends TestKit {

    static ActorSystem actorSystem = ActorSystem.create("actor-system-test", ConfigFactory.load());

    public EchoActorTest() {
        super(actorSystem);
    }

    @Test
    public void testEchoActorRepliesWithTheSameStringItRecieves() {
        final ActorRef echoActorRef = actorSystem.actorOf(Props.create(EchoActor.class));
        echoActorRef.tell("Hi", super.testActor());
        expectMsg("Hi");
    }

}
