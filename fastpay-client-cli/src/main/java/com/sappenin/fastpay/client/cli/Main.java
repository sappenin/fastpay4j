package com.sappenin.fastpay.client.cli;

import picocli.CommandLine;

public final class Main {

  public static void main(final String... args) {
    final CommandLine cmd = new CommandLine(new Client());
    final int exitCode = cmd.execute(args);
    System.exit(exitCode);
  }
}
