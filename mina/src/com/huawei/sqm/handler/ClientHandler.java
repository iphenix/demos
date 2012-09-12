package com.huawei.sqm.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.huawei.sqm.RequestProtos.Request;

public class ClientHandler extends IoHandlerAdapter {

	public void sessionCreated(IoSession session) throws Exception {
		Request.Builder builder = Request.newBuilder();
    	builder.setId(1);
    	builder.setStr("ABC");
		session.write(builder.build());
	}
	
    public void messageReceived(IoSession session, Object message)
            throws Exception {
    	
        System.out.print(message);
    }

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		cause.printStackTrace();
		session.close(true);
	}
}
