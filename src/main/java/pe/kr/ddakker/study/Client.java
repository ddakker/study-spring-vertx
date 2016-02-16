package pe.kr.ddakker.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetSocket;

public class Client {
	private static Logger log = LoggerFactory.getLogger(Client.class);
	
	public static void main(String[] args) {
		
		//final Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
		final Vertx vertx = Vertx.vertx();
		/*long l = System.currentTimeMillis();
		vertx.createNetClient().connect(9999, "localhost", res -> {

			log.info("T: " + (System.currentTimeMillis()-l));
			
			if (res.succeeded()) {
				NetSocket socket = res.result();
				socket.handler(buffer -> {
					log.debug("Net client receiving: " + buffer.toString("UTF-8"));
				});

				// Now send some data
				log.info("SS 1");
				socket.write("Hi~ 1");
				log.info("SS 2");
				socket.write("Hi~ 2");
				log.info("SS 3");
				vertx.close();
			} else {
				log.error("Failed to connect " + res.cause());
			}
		});*/
		
		
		
		try {
			long ll = System.currentTimeMillis();
			vertx.createNetClient().connect(9999, "localhost", res -> {
				
				//log.info("T1: " + (System.currentTimeMillis() - ll));
				
				if (res.succeeded()) {
					long diff = System.currentTimeMillis() - ll;
					log.info("T2: {}", (diff));
					//NetSocket socket = res.result();
					Client.socket = res.result();
				} else {
					//log.info("T3: " + (System.currentTimeMillis() - ll));
				}
			});
			
			while (Client.socket == null) {
				System.out.print(".");
			}
			
			for (int i = 0; i < 1000; i++) {
				String msg = "hi ~~~ " + i + "\n";
				log.debug("send msg: {}", msg);
				Client.socket.write(msg);
			}
			vertx.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static NetSocket socket = null;

}
