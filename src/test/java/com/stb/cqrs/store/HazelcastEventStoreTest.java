package com.stb.cqrs.store;

import org.junit.Test;

import com.stb.cqrs.AggregateEvent;
import com.stb.cqrs.EventWithHandler;

public class HazelcastEventStoreTest {

	@Test
	public void test() {
		HazelcastEventStore store = new HazelcastEventStore();
		AggregateEvent event = new EventWithHandler("test");
		store.persist(event);
	}
}
