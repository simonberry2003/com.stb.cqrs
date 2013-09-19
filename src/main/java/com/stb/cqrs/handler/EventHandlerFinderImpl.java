package com.stb.cqrs.handler;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.stb.cqrs.AbstractAggregate;
import com.stb.cqrs.AggregateEvent;

public class EventHandlerFinderImpl implements EventHandlerFinder {

	private final Map<Class<? extends AbstractAggregate>, Map<Class<? extends AggregateEvent>, Method>> handlers = Maps.newConcurrentMap();
	
	@Override
	public Method find(Class<? extends AbstractAggregate> aggregateClass, Class<? extends AggregateEvent> eventClass) {

		Preconditions.checkNotNull(aggregateClass);
		Preconditions.checkNotNull(eventClass);
		
		Map<Class<? extends AggregateEvent>, Method> aggregateHandlers = handlers.get(aggregateClass);
		if (aggregateHandlers == null) {
			aggregateHandlers = Maps.newConcurrentMap();
			handlers.put(aggregateClass, aggregateHandlers);
		}
		
		final Method eventHandler = aggregateHandlers.get(eventClass);
		if (eventHandler != null) {
			return eventHandler;
		}
		
		Method[] methods = aggregateClass.getDeclaredMethods();
		for (Method method : methods) {
			AggregateEventHandler handlerAnnotation = method.getAnnotation(AggregateEventHandler.class);
			if (handlerAnnotation != null) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length == 1) {
					Class<?> eventType = parameterTypes[0];
					if (eventType.equals(eventClass)) {
						method.setAccessible(true);
						aggregateHandlers.put(eventClass, method);
						return method;
					}
				}
			}
		}

		throw new IllegalArgumentException("No event handler was found for event type: " + eventClass);
	}
}
