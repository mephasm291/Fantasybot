package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.TimeZone;

public class TimeZoneData {
    private static final Logger log = LogManager.getLogger(TimeZoneData.class);

    private static TimeZone timezone = TimeZone.getTimeZone("EST");

    private static boolean checkedTimezone = false;

    static void checkTimezoneEnv() {
        if (!checkedTimezone) {
            final String env = EnvHandler.TIMEZONE.getValue();
            if (env != null) {
                final boolean isRealTimezone = Arrays.asList(TimeZone.getAvailableIDs()).contains(env.toUpperCase());
                if (isRealTimezone) {
                    log.debug("Valid timezone set.");
                    timezone = TimeZone.getTimeZone(env.toUpperCase());
                } else {
                    log.debug("Invalid timezone set.  Falling back to default - EST.");
                }
            } else {
                log.debug("No timezone set.  Falling back to default - EST.");
            }

            checkedTimezone = true;
        }
    }

    public static TimeZone getTimezone() {
        checkTimezoneEnv();
        return timezone;
    }

    public static ZoneId getZoneId() {
        checkTimezoneEnv();
        return timezone.toZoneId();
    }
}
