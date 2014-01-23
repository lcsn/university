package de.lsn.connector;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;

import de.lsn.playground.json.JsonHelper;
import de.lsn.playground.json.JsonObject;

public class HttpConnector {
	
	private static HttpConnector httpConnector;
	
	private HttpClient httpClient;
	
	public HttpConnector() {
		this.httpClient = new HttpClient();
		Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory) new SSLProtocolSocketFactory(), 443));
//		Protocol.registerProtocol("http", new Protocol("http", new DefaultProtocolSocketFactory(), 80));
	}
	
	public static HttpConnector getInstance() {
		if (null == httpConnector) {
			httpConnector = new HttpConnector();
		}
		return httpConnector;
	}
	
	public JsonObject put(String path, JsonObject o) {
		URI uri = null;
		try {
			uri = new URI(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return put(uri, o);
	}
	
	public JsonObject put(URI uri, JsonObject o) {
		PutMethod put = null;
		try {
			put = new PutMethod(uri.toString());
			put.setRequestEntity(new StringRequestEntity(o.toJson(), MediaType.APPLICATION_JSON, null));
			int returnCode = httpClient.executeMethod(put);
			if(200 == returnCode) {
				System.out.println("["+returnCode+"] Ok!");
			}
			else {
				System.out.println("["+returnCode+"] Error!");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (put != null) {
				put.releaseConnection();
			}
		}
		return o;
	}
	
	public JsonObject get(String path) {
		URI uri = null;
		try {
			uri = new URI(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return get(uri);
	}
	
	public JsonObject get(URI uri) {
		return get(uri, JsonObject.class);
	}
	
	public <T> T get(URI uri, Class<T> clazz) {
		GetMethod get = null;
		T t = null;
		try {
			get = new GetMethod(uri.toString());
			int returnCode = httpClient.executeMethod(get);
			if(200 == returnCode) {
				System.out.println("["+returnCode+"] Ok!");
			}
			else {
				System.out.println("["+returnCode+"] Error!");
			}
			String res = get.getResponseBodyAsString().trim();
			t = JsonHelper.getInstance().getObjectFromJson(res.getBytes(), clazz);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
		return t;
	}
	
	public void post(String path) {
		URI uri = null;
		try {
			uri = new URI(path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		post(uri);
	}
	
	public void post(URI uri) {
		PostMethod postMethod = null;
		try {
			postMethod = new PostMethod(uri.toString());
			int returnCode = httpClient.executeMethod(postMethod);
			if(200 == returnCode) {
				System.out.println("["+returnCode+"] Ok!");
			}
			else {
				System.out.println("["+returnCode+"] Error!");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}

}
