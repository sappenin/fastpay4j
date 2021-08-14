package com.sappenin.fastpay.core;

/**
 * A {@link RuntimeException} parent class for all Fastpay errors.
 */
public class FastpayException extends RuntimeException {

  private final FastpayError fastpayError;

  public FastpayException(String message) {
    super(message);
    this.fastpayError = null;
  }

  public FastpayException(FastpayError fastpayError) {
    super();
    this.fastpayError = fastpayError;
  }

  public FastpayException(String message, FastpayError fastpayError) {
    super(message);
    this.fastpayError = fastpayError;
  }

  public FastpayException(String message, Throwable cause, FastpayError fastpayError) {
    super(message, cause);
    this.fastpayError = fastpayError;
  }

  public FastpayException(Throwable cause, FastpayError fastpayError) {
    super(cause);
    this.fastpayError = fastpayError;
  }

  protected FastpayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
    FastpayError fastpayError) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.fastpayError = fastpayError;
  }

  public FastpayError getFastpayError() {
    return fastpayError;
  }

  @Override
  public String toString() {
    return "FastpayException{" +
      "message=" + getMessage() + "; " +
      "fastpayError=" + fastpayError +
      '}';
  }
}
