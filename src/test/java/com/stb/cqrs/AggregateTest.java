package com.stb.cqrs;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class AggregateTest {

	@Test(expected = IllegalArgumentException.class)
	public void testMissingEventHandler() {
		TestAggregate test = new TestAggregate();
		test.doSomethingWithMissingHandler();
	}

	@Test
	public void testEventHandler() {
		TestAggregate test = new TestAggregate();
		test.doSomething();
		Assert.assertEquals("something", test.getSomething());
	}

	@Test
	public void testLoadFromEvents() {
		TestAggregate test = new TestAggregate();
		test.loadFromEvents(ImmutableList.<AggregateEvent>of(new EventWithHandler("test"), new EventWithHandler("something")));
		Assert.assertEquals("something", test.getSomething());
		Assert.assertTrue(test.getUnsavedEvents().isEmpty());
	}
	
	@Test
	public void testGetUnsavedEvents() {
		TestAggregate test = new TestAggregate();
		test.apply(new EventWithHandler("test"));
		test.apply(new EventWithHandler("something"));
		Assert.assertEquals(2, test.getUnsavedEvents().size());
		Assert.assertEquals("test", ((EventWithHandler)test.getUnsavedEvents().get(0)).getSomething());
		Assert.assertEquals("something", ((EventWithHandler)test.getUnsavedEvents().get(1)).getSomething());
	}
}
