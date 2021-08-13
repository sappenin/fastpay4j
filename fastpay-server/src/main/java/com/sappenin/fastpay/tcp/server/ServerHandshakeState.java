package com.sappenin.fastpay.tcp.server;

import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum ServerHandshakeState {

  RCV_INIT_REQ(new byte[] {1, 2, 3}, new byte[] {2, 3, 4}, 1),
  RCV_FINAL_REQ(new byte[] {5, 6, 7}, new byte[] {6, 7, 8}, null);

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final byte[] expectedPayload;

  private final byte[] responsePayload;

  private final Integer nextStateOrdinal;

 ServerHandshakeState(byte[] expectedPayload, byte[] responsePayload, Integer nextStateOrdinal) {
    this.expectedPayload = expectedPayload;
    this.responsePayload = responsePayload;
    this.nextStateOrdinal = nextStateOrdinal;
  }

  public boolean receivedPayloadMatchesExpected(byte[] receivedPayload) {
    logger.info(
      "Comparing received payload (HEX): {} and expected payload (HEX): {}",
      BaseEncoding.base16().encode(receivedPayload), BaseEncoding.base16().encode(expectedPayload)
    );
    return Arrays.equals(receivedPayload, expectedPayload);
  }

  public byte[] getExpectedPayload() {
    return expectedPayload;
  }

  public byte[] getResponsePayload() {
    return responsePayload;
  }

  public Integer getNextStateOrdinal() {
    return nextStateOrdinal;
  }
}
