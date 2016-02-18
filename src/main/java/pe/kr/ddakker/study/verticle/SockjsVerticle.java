package pe.kr.ddakker.study.verticle;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class SockjsVerticle extends AbstractVerticle {
	private static Logger log = LoggerFactory.getLogger(SockjsVerticle.class);
	
	private static final OperatingSystemMXBean osMBean;

	static {
		try {
			osMBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(),
					ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/*@Override
	public void start() {

		Router router = Router.router(vertx);
		
		SockJSHandlerOptions options = new SockJSHandlerOptions().setHeartbeatInterval(2000);

		SockJSHandler sockJSHandler = SockJSHandler.create(vertx, options).bridge(new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress("load")));
		router.route("/eventbus/*").handler(sockJSHandler);
		
		sockJSHandler.socketHandler(sockJSSocket -> {
			sockJSSocket.handler(sockJSSocket::write);
		});

		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
		router.route().handler(sockJSHandler);


		vertx.setPeriodic(1000, t -> vertx.eventBus().publish("load", new JsonObject()
				.put("creatTime", System.currentTimeMillis()).put("cpuTime", osMBean.getSystemLoadAverage())));
		
		
	}*/
	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		// Allow events for the designated addresses in/out of the event bus bridge
		BridgeOptions opts = new BridgeOptions()
				.addInboundPermitted(new PermittedOptions().setAddress("chat.to.server"))
				.addOutboundPermitted(new PermittedOptions().setAddress("chat.to.client"));

		// Create the event bus bridge and add it to the router.
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route("/eventbus/*").handler(ebHandler);

		// Create a router endpoint for the static content.
		router.route().handler(StaticHandler.create());

		// Start the web server and tell it to use the router to handle requests.
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);

		EventBus eb = vertx.eventBus();

		// Register to listen for messages coming IN to the server
		//eb.consumer("chat.to.server").handler(message -> {
		eb.consumer("chat.to.server", (Message<String> message) -> {
			log.debug("---- message: " + message);
			log.debug("---- message.body(): " + message.body());
			// Create a timestamp string
			String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
					.format(Date.from(Instant.now()));
			// Send the message back out to all clients with the timestamp prepended.
			eb.publish("chat.to.client", message.body());
		});

	}
}
