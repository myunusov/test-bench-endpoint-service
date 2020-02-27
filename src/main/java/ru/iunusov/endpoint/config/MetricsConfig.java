package ru.iunusov.endpoint.config;

import io.github.mweirauch.micrometer.jvm.extras.ProcessMemoryMetrics;
import io.github.mweirauch.micrometer.jvm.extras.ProcessThreadMetrics;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

/*  @Value("${host}")
  private String host;

  @Value("${service}")
  private String service;

  @Value("${region}")
  private String region;*/

  @Bean
  MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {

    final MeterFilter meterFilter = new MeterFilter() {

      @NotNull
      @Override
      public DistributionStatisticConfig configure(Meter.Id id, @NotNull DistributionStatisticConfig config) {
          return DistributionStatisticConfig.builder()
              .percentiles(0.9, 0.95)
              .build()
              .merge(config);
        }
    };


    return registry -> registry.config()
//        .commonTags("application", "endpoint");
//        .commonTags("host", host, "service", service, "region", region)
        .meterFilter(meterFilter)
        .meterFilter(MeterFilter.deny(id -> {
          String uri = id.getTag("uri");
          return uri != null && uri.startsWith("/actuator");
        }))
        .meterFilter(MeterFilter.deny(id -> {
          String uri = id.getTag("uri");
          return uri != null && uri.contains("favicon");
        }));
  }

  @Bean
  public MeterBinder processMemoryMetrics() {
    return new ProcessMemoryMetrics();
  }

  @Bean
  public MeterBinder processThreadMetrics() {
    return new ProcessThreadMetrics();
  }

  @Bean
  public TimedAspect timedAspect(final MeterRegistry registry) {
    return new TimedAspect(registry);
  }

}