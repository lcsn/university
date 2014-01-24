package de.lsn.connector;

import com.firebase.client.FirebaseException;

@SuppressWarnings("serial")
public class CustomFirebaseException extends FirebaseException {

	public CustomFirebaseException(String message) {
		super(message);
	}

}
