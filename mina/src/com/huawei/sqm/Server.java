package com.huawei.sqm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.huawei.sqm.encode.ProtocolBufferCodecFilter;
import com.huawei.sqm.handler.ServerHandler;

public class Server {
	/**
	 * We will use a port above 1024 to be able to launch the server with a
	 * standard user
	 */
	private static final int PORT = 8888;

	/**
	 * The server implementation. It's based on TCP, and uses a logging filter
	 * plus a text line decoder.
	 */
	public static void main(String[] args) throws IOException {
		// Create the acceptor
		IoAcceptor acceptor = new NioSocketAcceptor();

		// Add two filters : a logger and a codec
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName("UTF-8"))));
		acceptor.getFilterChain().addLast("pbcodec",
				new ProtocolBufferCodecFilter());

		// Attach the business logic to the server
		acceptor.setHandler(new ServerHandler());

		// Configurate the buffer size and the iddle time
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// And bind !
		acceptor.bind(new InetSocketAddress(PORT));
	}
}
