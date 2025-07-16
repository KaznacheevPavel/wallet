package ru.kaznacheev.wallet.operationservice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.ZoneId;
import java.util.Objects;

public class TimeZoneInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String timeZoneHeader = request.getHeader("Wallet-User-TimeZone");

        if (Objects.isNull(timeZoneHeader) || !ZoneId.getAvailableZoneIds().contains(timeZoneHeader)) {
            TimeZoneContextHolder.setTimeZone(null);
            return true;
        }
        ZoneId zoneId = ZoneId.of(timeZoneHeader);
        TimeZoneContextHolder.setTimeZone(zoneId);
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        TimeZoneContextHolder.clear();
    }

}
