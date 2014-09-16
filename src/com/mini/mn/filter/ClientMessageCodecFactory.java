 
package com.mini.mn.filter;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * �ͻ�����Ϣ����빤����.<br>
 * {@link ProtocolCodecFactory}
 * ����{@link ClientMessageEncoder}Ϊ�����࣬�̳�ProtocolEncoderAdapter��
 * {@link ClientMessageDecoder}Ϊ�����࣬�̳�CumulativeProtocolDecoder��
 * 
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class ClientMessageCodecFactory implements ProtocolCodecFactory {

    private final ClientMessageEncoder encoder;

    private final ClientMessageDecoder decoder;

    /**
     * Constructor.
     */
    public ClientMessageCodecFactory() {
        encoder = new ClientMessageEncoder();
        decoder = new ClientMessageDecoder();
    }

    /**
     * Returns a new (or reusable) instance of ProtocolEncoder.
     */
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    /**
     * Returns a new (or reusable) instance of ProtocolDecoder.
     */
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

}
