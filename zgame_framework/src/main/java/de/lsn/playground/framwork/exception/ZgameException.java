package de.lsn.playground.framwork.exception;

@SuppressWarnings("serial")
public class ZgameException extends Exception {

	public ZgameException(String msg) {
		super(msg);
	}

	public ZgameException(Throwable t) {
		super(t);
	}

	public ZgameException(String msg, Throwable t) {
		super(msg, t);
	}
	
}
