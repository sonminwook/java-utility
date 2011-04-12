package com.javainsights.iputils.workers;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLContext;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.filter.util.SessionAttributeInitializingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

package com.javainsights.DTO.IPConfiguration;
package com.javainsights.exceptions.IPException;
package com.javainsights.iputils.TCPIPHandler;
package com.javainsights.iputils.contants.ClientSessionConstant;
package com.javainsights.iputils.contants.ErrorCodeConstant;
package com.javainsights.iputils.filters.coders.IPSSLCodecFactory;
package com.javainsights.iputils.filters.logging.IPSSLLoggingFilter;
package com.javainsights.iputils.filters.utils.IPSSLContextGenerator;

public class Initialize implements Callable<Boolean> {
	/*
	 * for TCP/IP Communication
	 */
	private IPConfiguration ipconfig = null;
	private Stack<IoSession> ipSessionQueue = null;
	
	private IoSession clientSession = null;
	private static Logger log = Logger.getLogger(Initialize.class);
	
	public Initialize(IPConfiguration config,
			Stack<IoSession> ipSessionQueue){
				this.ipconfig = config;
				this.ipSessionQueue = ipSessionQueue;
	}

	@Override
	public Boolean call() throws Exception {
		this.intialize(this.ipconfig);
		this.ipSessionQueue.push(this.clientSession);
		return true;
	}
	
	private IoSession intialize(IPConfiguration ipConfiguration) throws IPException {

		int CONNECT_TIMEOUT = ipConfiguration.getConnectionTimeOut();
		String HOSTNAME = ipConfiguration.getIPAddress();
		int PORT = ipConfiguration.getPort();
		
		NioSocketConnector connector = new NioSocketConnector();

		// Configure the service.
		connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
		connector.setHandler(new TCPIPHandler());
		try {
			addFilterChain(connector.getFilterChain(), ipConfiguration);
			ConnectFuture future = connector.connect(new InetSocketAddress(
					HOSTNAME, PORT));
			future.awaitUninterruptibly();
			clientSession = future.getSession();
		} catch (Exception e) {
			log.error("Client is unable to connect to Host[" + HOSTNAME
					+ "] PORT [" + PORT + "]", e);
			throw new IPException(ErrorCodeConstant.COMMUNICATION_ERROR
					+ "Client is unable to connect to Host[" + HOSTNAME
					+ "] PORT [" + PORT + "]", e);
		}

		log.debug("________________________________________________________________________________");
		log.debug("Client connected to Host[" + HOSTNAME + "]@[" + PORT
				+ "] successfully");
		log.debug("________________________________________________________________________________");

		clientSession.setAttribute(ClientSessionConstant.TIME_OUT_IN_MS,CONNECT_TIMEOUT);
		return clientSession;
	}

	@SuppressWarnings("unchecked")
	private void addFilterChain(DefaultIoFilterChainBuilder chain,
			IPConfiguration ipConfiguration) throws Exception {

		SessionAttributeInitializingFilter sessionInitFilter = new SessionAttributeInitializingFilter();
		chain.addFirst(ClientSessionConstant.INIT_FILTER, sessionInitFilter);
		chain.addAfter(ClientSessionConstant.INIT_FILTER,
				ClientSessionConstant.LOGGING_FILTER, new IPSSLLoggingFilter());
		chain
				.addAfter(ClientSessionConstant.LOGGING_FILTER,
						ClientSessionConstant.CODEC_FILTER,
						new ProtocolCodecFilter(new IPSSLCodecFactory(Charset
								.forName(ClientSessionConstant.CODEC_CHARSET))));

		/*
		 * ADDING SSL
		 */
		if ((ipConfiguration.getSslCertificate() != null)
				&& (!"".equals(ipConfiguration.getSslCertificate().trim()))) {
			IPSSLContextGenerator sslGenerator = new IPSSLContextGenerator();
			SSLContext context = sslGenerator.getClientContext(ipConfiguration);
			if (context != null) {
				SslFilter filter = new SslFilter(context);
				filter.setUseClientMode(true);
				chain.addFirst(ClientSessionConstant.SSL_FILTER, filter);
			}
		}

}

	private IoSession intializeServer(IPConfiguration ipConfiguration) throws IPException{
		int CONNECT_TIMEOUT = ipConfiguration.getConnectionTimeOut();
		String HOSTNAME = ipConfiguration.getIPAddress();
		int PORT = ipConfiguration.getPort();
		
		IoAcceptor acceptor = new NioSocketAcceptor();

		// Configure the service.		
		acceptor.setHandler(new TCPIPHandler());
		acceptor.getSessionConfig().setReadBufferSize(ipConfiguration.getOutputBufferSize());	
		
		try {
			addFilterChain(acceptor.getFilterChain(), ipConfiguration);
			acceptor.bind(new InetSocketAddress(ipConfiguration.getPort()));
			int timePeriod = CONNECT_TIMEOUT/10;
			for(int i=0; i<10; i++){
				clientSession = TCPIPHandler.getClientSession();
				if(clientSession == null){
					Thread.sleep(timePeriod);
				}
			}			
			clientSession.setAttribute(ClientSessionConstant.TIME_OUT_IN_MS,CONNECT_TIMEOUT);
		} catch (Exception e) {
			log.error("Unable to start server at[" + HOSTNAME+ "] PORT [" + PORT + "]", e);
			throw new IPException(ErrorCodeConstant.COMMUNICATION_ERROR
					+ "Unable to start server at[" + HOSTNAME+ "] PORT [" + PORT + "]", e);
		}

		log.debug("________________________________________________________________________________");
		log.debug("Client connected to Host[" + HOSTNAME + "]@[" + PORT	+ "] successfully");
		log.debug("________________________________________________________________________________");

		
		return clientSession;
	}
}
