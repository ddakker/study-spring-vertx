package pe.kr.ddakker.study;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import pe.kr.ddakker.study.config.AppContext;
import pe.kr.ddakker.study.verticle.DelayExecVerticle;
import pe.kr.ddakker.study.verticle.ServerVerticle;
import pe.kr.ddakker.study.verticle.ServerVerticle2;
import pe.kr.ddakker.study.verticle.SockjsVerticle;

public class Server {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppContext.class);
		final Vertx vertx = Vertx.vertx();
		//vertx.deployVerticle(new SpringDemoVerticle(context));
		//vertx.deployVerticle(new DelayExecVerticle(), new DeploymentOptions().setWorker(true).setInstances(10));
		vertx.deployVerticle(DelayExecVerticle.class.getName(), new DeploymentOptions().setWorker(true).setInstances(10));
		vertx.deployVerticle(new ServerVerticle());
		vertx.deployVerticle(new ServerVerticle2());
		vertx.deployVerticle(new SockjsVerticle());
	}
}
