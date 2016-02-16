package pe.kr.ddakker.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetSocket;

public class Client2 {
	private static Logger log = LoggerFactory.getLogger(Client2.class);
	
	public static void main(String[] args) {
		
		//final Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
		final Vertx vertx = Vertx.vertx();
		try {
			for (int i = 0; i < 1000; i++) {
				long ll = System.currentTimeMillis();
				vertx.createNetClient().connect(9999, "localhost", res -> {
					
					//log.info("T1: " + (System.currentTimeMillis() - ll));
					
					if (res.succeeded()) {
						long diff = System.currentTimeMillis() - ll;
						log.info("T2: {}", (diff));
						NetSocket socket = res.result();
						String msg = "hi ~~~ \n";
						log.debug("send msg: {}", msg);
						socket.write(msg);
						
						vertx.close();
					} else {
						//log.info("T3: " + (System.currentTimeMillis() - ll));
					}
				});
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
