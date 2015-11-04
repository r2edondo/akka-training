package akka.training.basics.actor;

import java.util.List;

import akka.actor.UntypedActor;

public class SequencingActor extends UntypedActor {

    //    final ActorRef actor;
    final List<Integer> headList;
    final List<Integer> tailList;

    public SequencingActor(List<Integer> headList, List<Integer> tailList) {
        //        this.actor = actorRef;
        this.headList = headList;
        this.tailList = tailList;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        for (final Integer headElement : this.headList) {
            getSender().tell(headElement, null);
        }
        getSender().tell(message, null);
        for (final Integer tailElement : this.tailList) {
            getSender().tell(tailElement, null);
        }

    }

}
