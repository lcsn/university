package de.lsn.connector;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Logger;
import com.firebase.client.ValueEventListener;
import com.firebase.client.Logger.Level;

import de.lsn.jackson.JsonHelper;
import de.lsn.model.Person;

public class FirebaseConnector {

	private static FirebaseConnector firebaseConnector;
	
	private Firebase firebase;
	
//	private Stack<Firebase> navigationStack;
	
	private Firebase firebasePointer;

	private FirebaseConnector() {
	}
	
	public static FirebaseConnector getInstance() {
		if(null == firebaseConnector) {
			firebaseConnector = new FirebaseConnector();
		}
		return firebaseConnector;
	}
	
	public FirebaseConnector newFirebase(String url) {
		this.firebase = new Firebase(url);
		firebase.child(".info/connected");
//		this.navigationStack = new Stack<Firebase>();
		return this;
	}
	
	public FirebaseConnector testFirebase() {
		if(this.firebase == null) {
			System.err.println("Firebase is not instantiated");
		}
		else {
			Firebase _firebase = this.firebase;
			_firebase.child("stat").addValueEventListener(new ValueEventListener() {
				
				@Override
				public void onDataChange(DataSnapshot ds) {
					System.out.println(ds.getValue());
				}
				
				@Override
				public void onCancelled(FirebaseError arg0) {
				}
			});
		}
		return this;
	}

	public FirebaseConnector setLogLevel(Level logLevel) {
		Firebase.getDefaultConfig().setLogLevel(logLevel);
		return this;
	}
	
	public FirebaseConnector child(String childName) {
//		this.navigationStack.push(this.firebase.child(childName));
		if(null != this.firebasePointer) {
			this.firebasePointer = this.firebasePointer.child(childName);
		} else {
			this.firebasePointer = this.firebase.child(childName);
		}
		return this;
	}
	
	public FirebaseConnector parent(String parentName) {
//		this.navigationStack.push(this.firebase.child(parentName));
		this.firebasePointer = this.firebasePointer.getParent();
		return this;
	}
	
	public Firebase push(Object value) {
		Firebase pushRef = this.firebase.push();
		if(null == value) {
			System.err.println("Value is null");
			return this.firebase;
		}
		else if (null != firebasePointer) {
			pushRef = this.firebasePointer.push();
		}
//		else if(null != this.navigationStack && this.navigationStack.size() > 0) {
//			pushRef = this.navigationStack.pop().push();
//		}
		pushRef.setValue(value, new Firebase.CompletionListener() {
			@Override
			public void onComplete(FirebaseError arg0, Firebase arg1) {
				System.out.println("Pushing data completed");
			}
		});
		
		return pushRef;
	}
	
	public Firebase testPush() {
		Map<String, Object> toSet = new HashMap<String, Object>();
		toSet.put("first", "Fred");
		toSet.put("last", "Swanson");
		return push(toSet); 
	}
	
	public Firebase testPush1() {
		Person p = (Person) JsonHelper.getInstance().getObjectFromJson(Person.class);
		return push(p.toMap()); 
	}
	
	public static void powerUp(Level level, String url) {
		getInstance().setLogLevel(Logger.Level.DEBUG).newFirebase("https://boiii.firebaseio.com").testFirebase();
	}
	
	public FirebaseConnector shutdown() {
		this.firebase.onDisconnect().cancel();
		return this;
	}
	
	public static void main(String[] args) {
		FirebaseConnector.powerUp(Logger.Level.DEBUG, "https://boiii.firebaseio.com");
//		FirebaseConnector.getInstance().setLogLevel(Logger.Level.DEBUG).newFirebase("https://boiii.firebaseio.com").testFirebase();
//		FirebaseConnector.getInstance().testFirebase();
		
		System.out.println(FirebaseConnector.getInstance().child("maps").child("map").testPush1().getName());
//		System.out.println(FirebaseConnector.getInstance().child("TestTest").testPush().getName());
//		System.out.println(FirebaseConnector.getInstance().child("Test1").push("{user_id: 'wilma1', text: 'Hello1'}").getName());
//		System.out.println(FirebaseConnector.getInstance().child("Moos").push("moo4").getName());
		
//		FirebaseConnector.getInstance().newFirebase("https://boiii.firebaseio.com").testFirebase().navigate("/person");
		
		FirebaseConnector.getInstance().shutdown();
	}

	
}
