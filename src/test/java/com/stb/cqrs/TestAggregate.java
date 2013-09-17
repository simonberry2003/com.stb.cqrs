package com.stb.cqrs;


public class TestAggregate extends AbstractAggregate {
	
	private String something;

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

