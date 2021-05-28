package com.opendatapolicing.enus.vertx;

import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendatapolicing.enus.config.ConfigKeys;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;

public class SemaphoreVerticle extends SemaphoreVerticleGen<AbstractVerticle> {
	private static final Logger LOG = LoggerFactory.getLogger(Thread.currentThread().getName());

	private Semaphore semaphore;

	public SemaphoreVerticle setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
		return this;
	}

	/**
	 * Val.Complete.enUS:SemaphoreVerticle start completed. 
	 * Val.Fail.enUS:SemaphoreVerticle start failed. 
	 * Val.SemaphoreComplete.enUS:%s semaphore completed. 
	 * Val.SemaphoreFail.enUS:%s semaphore failed. 
	 */
	@Override
	public void start(Promise<Void> startPromise) throws Exception {
		try {
			vertx.eventBus().consumer(ConfigKeys.SEMAPHORE_VERTICLE_EVENT_NAME).handler(message -> {
				vertx.executeBlocking(blockingCodeHandler -> {
					String eventName = message.headers().get(ConfigKeys.SEMAPHORE_VERTICLE_NEXT_EVENT_NAME);
					String eventAction = message.headers().get(ConfigKeys.SEMAPHORE_VERTICLE_EVENT_ACTION);
					try {
						Long pk = (Long)message.body();
						LOG.info("IMPORT: {} {}", pk, Thread.currentThread().getName());
						semaphore.acquire();
						LOG.info("ACQUIRED {} {}", pk, Thread.currentThread().getName());
						try {
			
							JsonObject params = new JsonObject();
							params.put("body", new JsonObject().put("pk", pk.toString()));
							params.put("path", new JsonObject());
							params.put("cookie", new JsonObject());
							params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + pk)).put("var", new JsonArray().add("refresh:false")));
							JsonObject context = new JsonObject().put("params", params);
							JsonObject json = new JsonObject().put("context", context);
							vertx.eventBus().request(eventName, json, new DeliveryOptions().addHeader("action", eventAction)).onSuccess(a -> {
								LOG.info("RELEASED {}", pk);
								semaphore.release();
								blockingCodeHandler.complete();
							}).onFailure(ex -> {
								LOG.error(String.format(startSemaphoreFail, eventAction), ex);
								semaphore.release();
								blockingCodeHandler.fail(ex);
							});
						} catch (Exception ex) {
							LOG.error(String.format(startSemaphoreFail, eventAction), ex);
							semaphore.release();
							blockingCodeHandler.fail(ex);
						}
					} catch (Exception ex) {
						LOG.error(String.format(startSemaphoreFail, eventAction), ex);
						semaphore.release();
						blockingCodeHandler.fail(ex);
					}
				}).onSuccess(a -> {
					message.reply(new JsonObject());
				}).onFailure(ex -> {
					LOG.error(String.format(startFail), ex);
					message.fail(500, String.format(startFail));
				});
			});
			startPromise.complete();
		} catch (Exception ex) {
			LOG.error(String.format(startFail), ex);
			startPromise.fail(ex);
		}
	}
}
