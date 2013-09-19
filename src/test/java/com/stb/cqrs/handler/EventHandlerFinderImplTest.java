package com.stb.cqrs.handler;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.stb.cqrs.EventWithHandler;
import com.stb.cqrs.TestAggregate;

public class EventHandlerFinderImplTest {

	@Test
	public void test() {
		EventHandlerFinderImpl finder = new EventHandlerFinderImpl();
		Method handler = finder.find(TestAggregate.class, EventWithHandler.class);
		Assert.assertNotNull(handler);
		Assert.assertEquals("handle", handler.getName());
		Assert.assertEquals(1, handler.getParameterTypes().length);
		Assert.assertEquals(EventWithHandler.class, handler.getParameterTypes()[0]);

		Method handler2 = finder.find(TestAggregate.class, EventWithHandler.class);
		Assert.assertSame(handler, handler2);
	}
}
