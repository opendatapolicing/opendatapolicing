package com.opendatapolicing.enus.java;

import static org.junit.Assert.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opendatapolicing.enus.trafficstop.TrafficStop;

import io.vertx.core.json.JsonObject;

public class ZonedDateTimeDeserializerTest {

	@Test
	public void test() {
		TrafficStop o1 = new TrafficStop();
		o1.setStopDateTime(ZonedDateTime.of(2021, 05, 14, 22, 24, 23, 1000000, ZoneId.of("America/Denver")));
		o1.addPersonRaceTitles("White");
		o1.addPersonRaceTitles("Black");
		JsonObject json1 = JsonObject.mapFrom(o1);
		TrafficStop o2 = json1.mapTo(TrafficStop.class);
		Assert.assertEquals(o1.getPersonRaceTitles(), o2.getPersonRaceTitles());
		Assert.assertEquals(o1.getStopDateTime(), o2.getStopDateTime());
	}

}
