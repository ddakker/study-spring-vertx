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
	public static final String BUS_SOCKJS_SERVER = "bus.sockjs.server";
	public static final String BUS_SOCKJS_CLIENT = "bus.sockjs.client";
	

	@Override
	public void start() throws Exception {

		Router router = Router.router(vertx);

		// Allow events for the designated addresses in/out of the event bus bridge
		BridgeOptions opts = new BridgeOptions()
				.addInboundPermitted(new PermittedOptions().setAddress(SockjsVerticle.BUS_SOCKJS_SERVER))
				.addOutboundPermitted(new PermittedOptions().setAddress(SockjsVerticle.BUS_SOCKJS_CLIENT));

		// Create the event bus bridge and add it to the router.
		SockJSHandler ebHandler = SockJSHandler.create(vertx).bridge(opts);
		router.route("/eventbus/*").handler(ebHandler);

		// Create a router endpoint for the static content.
		router.route().handler(StaticHandler.create());

		// Start the web server and tell it to use the router to handle requests.
		vertx.createHttpServer().requestHandler(router::accept).listen(9090);

		EventBus eb = vertx.eventBus();

		// Register to listen for messages coming IN to the server
		//eb.consumer("chat.to.server").handler(message -> {
		eb.consumer(SockjsVerticle.BUS_SOCKJS_SERVER, (Message<String> message) -> {
			//log.debug("---- message: " + message);
			log.debug("---- Sockjs message.body(): " + message.body());
			// Create a timestamp string
			//String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));
			// Send the message back out to all clients with the timestamp prepended.
			eb.publish(SockjsVerticle.BUS_SOCKJS_CLIENT, message.body());
		});

	}
}
