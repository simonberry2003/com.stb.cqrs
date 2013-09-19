package com.stb.cqrs.handler;

import java.lang.reflect.Method;

import com.stb.cqrs.AbstractAggregate;
import com.stb.cqrs.AggregateEvent;

public interface EventHandlerFinder {
	Method find(Class<? extends AbstractAggregate> aggregateClass, Class<? extends AggregateEvent> eventClass);
}
