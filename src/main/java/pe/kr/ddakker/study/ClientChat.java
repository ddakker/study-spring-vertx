package pe.kr.ddakker.study;

import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

public class ClientChat {
	private static Logger log = LoggerFactory.getLogger(ClientChat.class);
	
	public static void main(String[] args) {
		
		//final Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
		final Vertx vertx = Vertx.vertx();
		try {
			EventBus eb = vertx.eventBus();

			String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));

			eb.publish("chat.to.client", timestamp + ": 테스트여");

			log.info("end");
		} catch (Exception e) {
			log.error("", e);
		}
	}

}
