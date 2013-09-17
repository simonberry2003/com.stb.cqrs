package com.stb.cqrs;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public abstract class AbstractAggregate implements Aggregate {
	
	private final ObjectId id;
	private List<AggregateEvent> unsavedEvents = Lists.newArrayList();
	
	public AbstractAggregate() {
		this(new ObjectId());
	}
	
	public AbstractAggregate(ObjectId id) {
		this.id = Preconditions.checkNotNull(id);
	}

	public ObjectId getId() {
		return id;
	}
	
	/**
	 * Applies the specified {@link AggregateEvent} to this {@link Aggregate}. The {@link Aggregate} must
	 * have a handler method annotated with {@link AggregateEventHandler} that takes a single parameter
	 * of the {@link AggregateEvent} type. The handler method can be declared private to prevent access.
	 * @param event the {@link AggregateEvent}
	 * @throws IllegalArgumentException if the {@link Aggregate} does not have a handler method
	 * @throws NullPointerException if event is null
	 */
	protected void apply(AggregateEvent event) {
		Preconditions.checkNotNull(event);
		Method[] methods = getClass().getDeclaredMethods();
		for (Method method : methods) {
			AggregateEventHandler handlerAnnotation = method.getAnnotation(AggregateEventHandler.class);
			if (handlerAnnotation != null) {
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length == 1) {
					Class<?> eventType = parameterTypes[0];
					if (eventType.equals(event.getClass())) {
						try {
							method.setAccessible(true);
							method.invoke(this, event);
							unsavedEvents.add(event);
							return;
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}
			}
		}
		throw new IllegalArgumentException("No event handler was found for event type: " + event.getClass());
	}
	
	public final void loadFromEvents(List<AggregateEvent> events) {
		for (AggregateEvent event : events) {
			apply(event);
		}
		unsavedEvents.clear();
	}

	@Override
	public final List<AggregateEvent> getUnsavedEvents() {
		return unsavedEvents;
	}
}
