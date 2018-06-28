package main.producers;

import main.consumers.EventConsumer;

public interface EventStream {
    void produce(EventConsumer consumer);
}
