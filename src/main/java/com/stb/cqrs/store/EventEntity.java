package com.stb.cqrs.store;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.google.common.base.Preconditions;
import com.stb.cqrs.AggregateEvent;

@SuppressWarnings("serial")
public class EventEntity implements Serializable {

	private final long id;
	private final AggregateEvent event;
	private final DateTime timestamp;
	
	public EventEntity(long id, AggregateEvent event) {
		this.id = id;
		this.event = Preconditions.checkNotNull(event);
		this.timestamp = DateTime.now();
	}
}
