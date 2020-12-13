package ru.otus.spring.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class FreeAppRamHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int mb = 1024 * 1024;
        long totalAppMemory = Runtime.getRuntime().totalMemory() / mb;
        long freeAppMemory = Runtime.getRuntime().freeMemory() / mb;
        long tenPercents = totalAppMemory / 10;

        boolean isNeedAddRam = (freeAppMemory < tenPercents);
        if (isNeedAddRam) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Free app RAM < 10%")
                    .build();
        } else {
            return Health.up()
                    .withDetail("message", "Free app RAM > 10%")
                    .build();
        }
    }
}
