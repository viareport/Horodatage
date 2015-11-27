package fr.inativ.mob.horodatage;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TimeMachine {

	private static Clock clock = Clock.systemDefaultZone();
	private static ZoneId zoneId = ZoneId.systemDefault();

	public static Instant now() {
		return Instant.now(getClock());
	}

	public static void useFixedClockAt(Instant date) {
		clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId);
	}

	public static void useSystemDefaultZoneClock() {
		clock = Clock.systemDefaultZone();
	}

	private static Clock getClock() {
		return clock;
	}

}
