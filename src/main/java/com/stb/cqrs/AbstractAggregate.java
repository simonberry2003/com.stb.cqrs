package com.stb.cqrs;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.stb.cqrs.handler.AggregateEventHandler;
import com.stb.cqrs.handler.EventHandlerFinder;

public abstract class AbstractAggregate implements Aggregate {

	private final ObjectId id;
	private final EventHandlerFinder handlerFinder;
	private List<AggregateEvent> unsavedEvents = Lists.newArrayList();

	public AbstractAggregate(EventHandlerFinder handlerFinder) {
		this(new ObjectId(), handlerFinder);
	}

	public AbstractAggregate(ObjectId id, EventHandlerFinder handlerFinder) {
		this.id = Preconditions.checkNotNull(id);
		this.handlerFinder = Preconditions.checkNotNull(handlerFinder);
	}

	public ObjectId getId() {
		return id;
	}

	/**
	 * Applies the specified {@link AggregateEvent} to this {@link Aggregate}.
	 * The {@link Aggregate} must have a handler method annotated with
	 * {@link AggregateEventHandler} that takes a single parameter of the
	 * {@link AggregateEvent} type. The handler method can be declared private
	 * to prevent access.
	 * 
	 * @param event
	 *            the {@link AggregateEvent}
	 * @throws IllegalArgumentException
	 *             if the {@link Aggregate} does not have a handler method
	 * @throws NullPointerException
	 *             if event is null
	 */
	protected void apply(AggregateEvent event) {
		try {
			Method handler = handlerFinder.find(getClass(), event.getClass());
			handler.invoke(this, event);
			unsavedEvents.add(event);
		} catch (Exception e) {
			throw ExceptionWrapper.wrap(e);
		}
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
