# Basics

To test this actors we make use of the testActor contained inside the JavaTestKit.

## EchoActor
Responds back with whatever is passed to the actor. 

## Forwarding Actor
Forwards the message to the actor reference passed as a parameter during the instantiation.
 
## Sequencing Actor
Replies back in a series of messages but assuming we are interested in one. The test demonstrates how the messages are processed in order.

## Filtering Actor
Replies back for certain messages and ignores the others. It responds only to String type messages.

## Boom Actor 
Throws an exception when a message is send. This actor actor is tested using the TestActorRef.

## Supervisor Actor
Manages an another worker actor, and based on the exception thrown by the worker actor, applies the appropriate supervisor strategy.