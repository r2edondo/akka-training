package akka.training.basics.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestKit;

public class SequencingActorTest extends TestKit {

    static ActorSystem system = ActorSystem.create("actor-system-test", ConfigFactory.load());

    public SequencingActorTest() {
        super(system);
    }

    @Test
    public void testSequencingActor() {
        final List<Integer> headList = new ArrayList<Integer>();
        final List<Integer> tailList = new ArrayList<Integer>();

        final int randomHead = new Random().nextInt(6);
        final int randomTail = new Random().nextInt(10);

        for (int i = 0; i < randomHead; i++) {
            headList.add(i);
        }
        for (int i = 1; i < randomTail; i++) {
            tailList.add(i);
        }

        final ActorRef sequencingActorRef = system.actorOf(Props.create(SequencingActor.class, super.testActor(),
                headList, tailList));
        // pass the reference to implicit sender testActor() otherwise
        // message end up in dead mailbox
        sequencingActorRef.tell("do something", super.testActor());

        for (final Integer value : headList) {
            expectMsgClass(Integer.class);
        }
        expectMsg("do something");
        for (final Integer value : tailList) {
            expectMsgClass(Integer.class);
        }
        expectNoMsg();
    }
}
