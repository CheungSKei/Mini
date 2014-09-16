 
package com.mini.mn.network.socket;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * �Դ�Ĭ�� session����ʵ�֣� ��λ��������ʵ�� AbstractSessionManager�ӿ���ʵ���Լ��� session����
 *
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class DefaultSessionManager implements SessionManager{

	protected final Logger logger = LoggerFactory.getLogger(DefaultSessionManager.class);  

    static final String SESSION_KEY ="account";
    private static HashMap<String,IoSession> sessions =new  HashMap<String,IoSession>();

    private static final AtomicInteger connectionsCounter = new AtomicInteger(0);

    /**
     *  
     */
    public void addSession(String account,IoSession session) {
        if(session!=null)
        {
        	session.setAttribute(SESSION_KEY, account);
        	sessions.put(account, session);
        	connectionsCounter.incrementAndGet();
        }
        
    }

    public IoSession getSession(String account) {
    	
    	 
       return sessions.get(account);
    }

    public Collection<IoSession> getSessions() {
        return sessions.values();
    }
 
    public void  removeSession(IoSession session) {

    	sessions.remove(session.getAttribute(SESSION_KEY));
    }

     
    public void  removeSession(String account) {
        
    	sessions.remove(account);
    	
    }
    
    public boolean containsIoSession(IoSession ios)
    {
    	return sessions.containsKey(ios.getAttribute(SESSION_KEY)) || sessions.containsValue(ios);
    }

    public String getAccount(IoSession ios)
    {
    	 if(ios.getAttribute(SESSION_KEY)==null)
    	 {
    		for(String key:sessions.keySet())
    		{
    			if(sessions.get(key).equals(ios) || sessions.get(key).getId()==ios.getId())
    			{
    				return key;
    			}
    		}
    	 }else
    	 {
    	    return ios.getAttribute(SESSION_KEY).toString();
    	 }
    	 
    	 return null;
    }
 
}
