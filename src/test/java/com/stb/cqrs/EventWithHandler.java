package com.stb.cqrs;

@SuppressWarnings("serial")
public class EventWithHandler implements AggregateEvent {
	
	private final String something;
	
	public EventWithHandler(String something) {
		this.something = something;
	}

	public String getSomething() {
		return something;
	}
}
