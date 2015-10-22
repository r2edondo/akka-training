package akka.training.basics.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class BoomActor extends UntypedActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            this.log.info("String received. Throwing illegal argument exception.");
            throw new IllegalArgumentException("boom!");
        } else if (message instanceof Integer) {
            this.log.info("Integer received. Throwing null pointer exception.");
            throw new NullPointerException("caput");
        }
    }
}
