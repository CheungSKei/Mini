 
package com.mini.mn.network.socket;

import java.util.Collection;

import org.apache.mina.core.session.IoSession;

/**
 *  �ͻ��˵� session����ӿ�
 *  ������ʵ�ִ˽ӿڹ���session
 *  
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public interface  SessionManager  {

	
	/**
	 * ����µ�session
	 */
	public void addSession(String account,IoSession session);
	
	/**
	 * 
	 * @param account �ͻ���session�� key һ����� �û��˺�����Ӧsession
	 * @return
	 */
	IoSession getSession(String account);
	
	/**
	 * ��ȡ����session
	 * @return
	 */
	public Collection<IoSession> getSessions();
	
	/**
	 * ɾ��session
	 * @param session
	 */
    public void  removeSession(IoSession session) ;
    
    
    /**
	 * ɾ��session
	 * @param session
	 */
    public void  removeSession(String account);
    
    /**
	 * session�Ƿ����
	 * @param session
	 */
    public boolean containsIoSession(IoSession ios);
    
    /**
	 * session��ȡ��Ӧ�� �û� key  
	 * @param session
	 */
    public String getAccount(IoSession ios);
}