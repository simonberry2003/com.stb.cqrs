package com.stb.cqrs;

import com.stb.cqrs.handler.AggregateEventHandler;
import com.stb.cqrs.handler.EventHandlerFinderImpl;

public class TestAggregate extends AbstractAggregate {

	private String something;

	public TestAggregate() {
		super(new EventHandlerFinderImpl());
	}
	
	public void doSomethingWithMissingHandler() {
		apply(new EventWithNoHandler());
	}

	public void doSomething() {
		apply(new EventWithHandler("something"));
	}

	@SuppressWarnings("unused")
	@AggregateEventHandler
	private void handle(EventWithHandler event) {
		this.something = event.getSomething();
	}

	public String getSomething() {
		return something;
	}
}
