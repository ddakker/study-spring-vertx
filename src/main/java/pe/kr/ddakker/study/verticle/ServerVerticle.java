package pe.kr.ddakker.study.verticle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.parsetools.RecordParser;

public class ServerVerticle extends AbstractVerticle {
	private static Logger log = LoggerFactory.getLogger(ServerVerticle.class);
	
	@Override
	public void start() throws Exception {
		vertx.createNetServer().connectHandler(socket -> {

			String handlerID = socket.writeHandlerID();

			socket.handler(RecordParser.newDelimited("\n", buffer -> {

				String message = buffer.toString().trim();

				log.debug(socket.remoteAddress() + " (" + handlerID + ") => " + message + "\r\n");


				/*vertx.eventBus().<String> send(DelayExecVerticle.ALL_PRODUCTS_ADDRESS, message, result -> {
					if (result.succeeded()) {
						log.debug("SS result.result().body(): " + result.result().body());
						socket.write("Receive => " + message + "\r\n");
					} else {
						log.error("FF result.cause().toString(): " + result.cause().toString());
					}
				});*/
				vertx.eventBus().<String> send(DelayExecVerticle.ALL_PRODUCTS_ADDRESS, message);

			}));

		}).listen(9999);
	}
}
