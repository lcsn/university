package de.lsn.playground.framwork.remote;

import java.util.HashMap;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;

import de.lsn.playground.framwork.ZgameConstants;

public class RemoteInterfaceFactory {

	private static RemoteInterfaceFactory rif;

	private InitialContext ctx;

	private HashMap<Class<?>, Object> remoteMap = new HashMap<Class<?>, Object>();
	
	public RemoteInterfaceFactory() {
	}

	public static RemoteInterfaceFactory getInstance() {
		if (null == rif) {
			rif = new RemoteInterfaceFactory();
		}
		return rif;
	}
	
	public static void recreateInitialContext(Properties props) {
		try {
			getInstance().setCtx(new InitialContext(props));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void setCtx(InitialContext ctx) {
		this.ctx = ctx;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getCachedRemote(Class<T> remoteClass) {
		if(null == this.ctx) {
//			TODO LOG WARNING
			System.out.println("Context is not set!");
		}
		T remote = null;
		if(remoteMap.containsKey(remoteClass)) {
			remote = (T) remoteMap.get(remoteClass);
		}
		else {
			try {
				remote = (T) ctx.lookup(getJndi(remoteClass));
				remoteMap.put(remoteClass, remote);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return (T) remote;
	}
	
	private String getJndi(Class<?> remoteClass) {
		String jndi = "";
		if(StringUtils.endsWith(remoteClass.getSimpleName(), "Remote")) {
			String beanName = StringUtils.replace(remoteClass.getSimpleName(), "Remote", "");
			jndi = "java:global/"+ZgameConstants.EAR+"/"+ZgameConstants.MODUL_I+"/"+beanName+"!"+remoteClass.getCanonicalName();
		}
		else {
			// log
		}
		return jndi;
	}
	
}
