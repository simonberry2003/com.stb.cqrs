package com.stb.cqrs.store;

import com.stb.cqrs.Aggregate;
import com.stb.cqrs.AggregateEvent;

public interface EventStore {
	void persist(AggregateEvent event);
	void persist(Aggregate aggregate);
}
