package main.consumers;

import main.util.JobEvent;

@FunctionalInterface
public interface EventConsumer {
   JobEvent consume(JobEvent event);
}
