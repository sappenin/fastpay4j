package com.sappenin.fastpay.server.spring;

import org.springframework.core.env.Environment;

import java.util.Arrays;

public class SpringProfileUtils {

  /**
   * Checks if a given profile name is an active profile in the environment.
   *
   * @param environment An environment containing profiles.
   * @param profile     A profile to check.
   *
   * @return {@code ture} if the requested profile is active.
   */
  public static boolean isProfileActive(Environment environment, String profile) {
    return Arrays.asList(environment.getActiveProfiles())
      .stream()
      .anyMatch(active -> active.trim().equalsIgnoreCase(profile.trim()));
  }

}
