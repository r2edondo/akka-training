package akka.training.basics.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ForwadingActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    final ActorRef forwardTo;

    public ForwadingActor(ActorRef forwardTo) {
        this.forwardTo = forwardTo;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        this.log.info("Forwading message...");
        this.forwardTo.tell(message, null);
    }

}
