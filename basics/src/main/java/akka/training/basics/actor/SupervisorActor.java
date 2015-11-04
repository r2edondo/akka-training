package akka.training.basics.actor;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.UntypedActor;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

public class SupervisorActor extends UntypedActor {

    private ActorRef childActor;

    public SupervisorActor() {
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Props) {
            this.childActor = getContext().actorOf((Props) message, "childActor");
            sender().tell(this.childActor, null);
        } else {
            this.childActor.tell(message, sender());
        }
    }

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create(10, TimeUnit.SECONDS),
            new Function<Throwable, Directive>() {
        @Override
        public Directive apply(Throwable t) {
            if (t instanceof IllegalArgumentException) {
                return SupervisorStrategy.stop();
            } else if (t instanceof NullPointerException) {
                return SupervisorStrategy.resume();
            } else {
                return SupervisorStrategy.escalate();
            }
        }
    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
