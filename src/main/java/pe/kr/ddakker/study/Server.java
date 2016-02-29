package pe.kr.ddakker.study;

import io.vertx.core.net.NetSocket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import pe.kr.ddakker.study.config.AppContext;
import pe.kr.ddakker.study.verticle.DelayExecVerticle;
import pe.kr.ddakker.study.verticle.ServerVerticle;
import pe.kr.ddakker.study.verticle.ServerVerticle2;
import pe.kr.ddakker.study.verticle.SockjsVerticle;

import static java.lang.Thread.sleep;

public class Server {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);
		final Vertx vertx = Vertx.vertx();
		//vertx.deployVerticle(new SpringDemoVerticle(context));
		//vertx.deployVerticle(new DelayExecVerticle(), new DeploymentOptions().setWorker(true).setInstances(10));
		vertx.deployVerticle(DelayExecVerticle.class.getName(), new DeploymentOptions().setWorker(true).setInstances(2));
		//vertx.deployVerticle(new SockjsVerticle());
		vertx.deployVerticle(SockjsVerticle.class.getName(), new DeploymentOptions().setWorker(true).setInstances(2));
		vertx.deployVerticle(new ServerVerticle());
		vertx.deployVerticle(new ServerVerticle2());

		new Thread(new Runnable() {
			@Override
			public void run() {

				while (true) {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					/*System.out.println("ServerVerticle.clientSocketList: " + ServerVerticle.clientSocketList.size());
					for (NetSocket s : ServerVerticle.clientSocketList) {
						System.out.println("S: " + s);
						s.write("s: " + s).closeHandler(event -> {
							ServerVerticle.clientSocketList.remove(s);
					kill -	});
					}*/
				}
			}
		}).start();
	}
}
