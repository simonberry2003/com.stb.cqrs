package com.stb.cqrs.store;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.stb.cqrs.Aggregate;
import com.stb.cqrs.AggregateEvent;

public class HazelcastEventStore implements EventStore {

	private final HazelcastInstance hazelcast; 
	private final AtomicLong eventId = new AtomicLong(1);
	private final List<EventEntity> events;
	
	public HazelcastEventStore() {
		Config cfg = new Config();
		hazelcast = Hazelcast.newHazelcastInstance(cfg);
		events = hazelcast.getList("events");
	}
	
	@Override
	public void persist(AggregateEvent event) {
		EventEntity entity = new EventEntity(eventId.getAndIncrement(), event);
		events.add(entity);
	}

	@Override
	public void persist(Aggregate aggregate) {
		List<AggregateEvent> unsavedEvents = aggregate.getUnsavedEvents();
		for (AggregateEvent event : unsavedEvents) {
			persist(event);
		}
	}
}
