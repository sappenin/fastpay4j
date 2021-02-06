package money.fluid.fastpay4j.tcp.client;

import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public enum ClientHandshakeState {

  SEND_INIT_REQ(new byte[] {1, 2, 3}, new byte[] {2, 3, 4}, 1),
  SEND_FINAL_REQ(new byte[] {5, 6, 7}, new byte[] {6, 7, 8}, null);

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private byte[] payload;

  private byte[] responsePayload;

  private Integer nextStateOrdinal;

  ClientHandshakeState(byte[] payload, byte[] responsePayload, Integer nextStateOrdinal) {
    this.payload = payload;
    this.responsePayload = responsePayload;
    this.nextStateOrdinal = nextStateOrdinal;
  }

  public boolean receivedPayloadMatchesExpected(byte[] receivedPayload) {
    logger.info(
      "Comparing received payload (HEX): {} and expected response payload (HEX): {}",
      BaseEncoding.base16().encode(receivedPayload), BaseEncoding.base16().encode(responsePayload)
    );
    return Arrays.equals(receivedPayload, responsePayload);
  }

  public byte[] getPayload() {
    return payload;
  }

  public byte[] getResponsePayload() {
    return responsePayload;
  }

  public Integer getNextStateOrdinal() {
    return nextStateOrdinal;
  }
}
