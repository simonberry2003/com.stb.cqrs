package com.stb.cqrs.guice;

import com.google.inject.AbstractModule;
import com.stb.cqrs.handler.EventHandlerFinder;
import com.stb.cqrs.handler.EventHandlerFinderImpl;

public class CqrsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EventHandlerFinder.class).to(EventHandlerFinderImpl.class);
	}
}
