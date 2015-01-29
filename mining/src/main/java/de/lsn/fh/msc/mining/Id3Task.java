package de.lsn.fh.msc.mining;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

public @FunctionalInterface
interface Id3Task<R, T> {
	
	default public void apply(Consumer<Id3Task<R, T>> c) {
		Objects.requireNonNull(c);
		c.accept(this);
	}
	
	public HashMap<R, T> entropies();
	
}
