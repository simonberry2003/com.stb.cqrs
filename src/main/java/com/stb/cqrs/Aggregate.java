package com.stb.cqrs;

import java.util.List;

public interface Aggregate {
	List<AggregateEvent> getUnsavedEvents();
}
