package ru.kaznacheev.wallet.operationservice.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.ZoneId;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeZoneContextHolder {

    private static final ThreadLocal<ZoneId> timeZoneContextHolder = new ThreadLocal<>();

    public static void setTimeZone(ZoneId zoneId) {
        timeZoneContextHolder.set(zoneId);
    }

    public static ZoneId getTimeZone() {
        ZoneId zoneId = timeZoneContextHolder.get();
        if (Objects.isNull(zoneId)) {
            return ZoneId.of("UTC");
        }
        return zoneId;
    }

    public static void clear() {
        timeZoneContextHolder.remove();
    }


}
