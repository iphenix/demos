package com.huawei.sqm;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.huawei.sqm.encode.ProtocolBufferCodecFilter;
import com.huawei.sqm.handler.ClientHandler;

public class Client {
	private static final int CONNECT_TIMEOUT = 30000;
	private static final String HOSTNAME = "127.0.0.1";
	private static final int PORT = 8888;

	public static void main(String[] args) throws Throwable {
		NioSocketConnector connector = new NioSocketConnector();

		// Configure the service.
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));
		connector.getFilterChain().addLast("pbcodec",
				new ProtocolBufferCodecFilter());
		
		connector.setHandler(new ClientHandler());
		
		IoSession session;
		for (;;) {
			try {
				ConnectFuture future = connector.connect(new InetSocketAddress(
						HOSTNAME, PORT));
				future.awaitUninterruptibly();
				session = future.getSession();
				break;
			} catch (RuntimeIoException e) {
				System.err.println("Failed to connect.");
				e.printStackTrace();
				Thread.sleep(5000);
			}
		}

		// wait until the summation is done
		session.getCloseFuture().awaitUninterruptibly();
		connector.dispose();
	}
}
