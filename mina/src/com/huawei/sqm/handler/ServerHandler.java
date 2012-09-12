package com.huawei.sqm.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class ServerHandler extends IoHandlerAdapter {

	public void messageReceived(IoSession session, Object message)
			throws Exception {
		session.write(message);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		cause.printStackTrace();
		session.close(true);
	}
}
