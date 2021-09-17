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
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "client",
    mixinStandardHelpOptions = true,
    description = "FastPay client."
)
public final class Client {

  private static final Logger logger = LoggerFactory.getLogger(Client.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Command(
      name = "create_accounts",
      mixinStandardHelpOptions = true,
      description = "Creates accounts with initial funding."
  )
  private int createAccounts(
      @Option(names = {"--accounts"}, description = "Accounts JSON.") String accounts,
      @Option(names = {"--quantity"}, description = "Quantity of accounts to create.") int quantity,
      @Option(names = {"--initial_funding"}, description = "Initial funding for each account.") long initialFunding) {

    final File file = new File(accounts);

    try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
      for (int i = 0; i < quantity; ++i) {
        final UserAccount account = Utils.generateUserAccountWithInitialFunding(initialFunding, new SecureRandom());
        System.out.println(account.getAddress());
        final String json = objectMapper.writeValueAsString(account);
        writer.write(json);
        writer.write("\n");
      }
    } catch (final IOException e) {
      logger.error("Cannot write into file", e);
      return 1;
    }
    return 0;
  }
}
