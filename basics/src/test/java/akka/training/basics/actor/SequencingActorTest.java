package akka.training.basics.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;

public class SequencingActorTest {

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
    public void testSequencingActor() {
        new JavaTestKit(system) {

            {

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

                final Props props = Props.create(SequencingActor.class, headList, tailList);
                final ActorRef subject = system.actorOf(props);
                // pass the reference to implicit sender testActor() otherwise
                // message end up in dead mailbox
                subject.tell("do something", getRef());

                for (final Integer value : headList) {
                    expectMsgClass(Integer.class);
                }
                expectMsgEquals("do something");
                for (final Integer value : tailList) {
                    expectMsgClass(Integer.class);
                }
                expectNoMsg();

            }
        };

    }

}
