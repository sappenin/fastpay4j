package com.sappenin.fastpay.client.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static void main(final String... args) {
    final String accountsPath = args[0];
    final int numAccounts = 1000;

    final File file = new File(accountsPath);

    try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
      for (int i = 0; i < numAccounts; ++i) {

        final UserAccount account = Utils.generateUserAccountWithInitialFunding(100, new SecureRandom());
        System.out.println(account.getAddress());
        final String json = objectMapper.writeValueAsString(account);
        writer.write(json);
        writer.write("\n");
      }
    } catch (final IOException e) {
      logger.error("Cannot write into file", e);
    }
  }
}
