package pe.kr.ddakker.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

public class Client3 {
	private static Logger log = LoggerFactory.getLogger(Client3.class);

	
	public static void main(String[] args) {

		
		//final Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
		final Vertx vertx = Vertx.vertx();
		try {
			long ll = System.currentTimeMillis();
			vertx.createNetClient().connect(9999, "localhost", res -> {

				//log.info("T1: " + (System.currentTimeMillis() - ll));

				if (res.succeeded()) {
					long diff = System.currentTimeMillis() - ll;
					//log.info("T2: {}", (diff));
					NetSocket socket = res.result();
					for (int i = 0; i < 1000; i++) {
						//log.info("Client2.iii: {}", Client2.iii);
						String msg = i + "\n";
						log.debug("send msg: {}", msg);

						long l = System.currentTimeMillis();

						socket.write(msg);
						
						try {
							Thread.sleep(100);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						long difff = System.currentTimeMillis() - l;
						if (difff > 500) {
							socket.write("ddd " + i + "\n");
						}
					}
					vertx.close();
				} else {
					//log.info("T3: " + (System.currentTimeMillis() - ll));
				}
			});
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
