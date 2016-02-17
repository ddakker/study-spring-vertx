package pe.kr.ddakker.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetSocket;

public class Client2 {
	private static Logger log = LoggerFactory.getLogger(Client2.class);

	public static int iii = 0;
	
	public static void main(String[] args) {

		
		//final Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
		final Vertx vertx = Vertx.vertx();
		try {
			for (int i = 0; i < 100; i++) {
				log.info("i: {}", i);
				Client2.iii = i;

				long ll = System.currentTimeMillis();
				vertx.createNetClient().connect(9999, "localhost", res -> {
					
					//log.info("T1: " + (System.currentTimeMillis() - ll));
					
					if (res.succeeded()) {
						long diff = System.currentTimeMillis() - ll;
						//log.info("T2: {}", (diff));
						NetSocket socket = res.result();
						//log.info("Client2.iii: {}", Client2.iii);
						String msg = "hi ~~~ " + Client2.iii + " \n";
						log.debug("send i: {}, msg: {}", Client2.iii, msg);
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
