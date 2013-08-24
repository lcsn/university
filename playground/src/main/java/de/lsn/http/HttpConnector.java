package de.lsn.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;

import de.lsn.jackson.JsonHelper;
import de.lsn.model.JsonObject;

public class HttpConnector {
	
	private static HttpConnector httpConnector;
	
	private HttpClient client;
	
	public HttpConnector() {
		this.client = new HttpClient();
		Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory) new SSLProtocolSocketFactory(), 443));
	}
	
	public static HttpConnector getInstance() {
		if (null == httpConnector) {
			httpConnector = new HttpConnector();
		}
		return httpConnector;
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
		GetMethod get = null;
		JsonObject o = null;
		try {
			get = new GetMethod(uri.toString());
			client.executeMethod(get);
			String res = get.getResponseBodyAsString().trim();
			o = JsonHelper.getInstance().getObjectFromJson(res.getBytes());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (get != null) {
				get.releaseConnection();
			}
		}
		return o;
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
			client.executeMethod(postMethod);
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
