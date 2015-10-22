# Basics

* Echo Actor – responds back with whatever is passed to the actor
* Forwarding Actor – forwards the message to another actor
* Sequencing Actor – replies back in a series of messages but assuming we are interested in one
* Filtering Actor – replies back for certain messages and ignores the others
* Boom Actor – throws an exception when a message is send
* Supervisor Actor – manages an another worker actor, and based on the exception thrown by the worker actor, applies the appropriate supervisor strategy