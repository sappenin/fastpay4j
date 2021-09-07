package com.sappenin.fastpay.client.cli;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import com.sappenin.fastpay.core.keys.KeyUtils;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;

public final class UserAccount {

  @JsonProperty("address")
  private String address;

  @JsonProperty("key")
  private String key;

  @JsonProperty("next_sequence_number")
  private long nextSequenceNumber;

  @JsonProperty("balance")
  private long balance;

  @JsonProperty("sent_certificates")
  private List<String> sentCertificates;

  @JsonProperty("received_certificates")
  private List<String> receivedCertificates;

  public UserAccount(final String address, final String key, long balance) {
    this.address = address;
    this.key = key;
    this.nextSequenceNumber = 0;
    this.balance = balance;
    this.sentCertificates = new ArrayList<>();
    this.receivedCertificates = new ArrayList<>();
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public long getNextSequenceNumber() {
    return nextSequenceNumber;
  }

  public void setNextSequenceNumber(long nextSequenceNumber) {
    this.nextSequenceNumber = nextSequenceNumber;
  }

  public long getBalance() {
    return balance;
  }

  public void setBalance(long balance) {
    this.balance = balance;
  }

  public List<String> getSentCertificates() {
    return sentCertificates;
  }

  public void setSentCertificates(List<String> sentCertificates) {
    this.sentCertificates = sentCertificates;
  }

  public List<String> getReceivedCertificates() {
    return receivedCertificates;
  }

  public void setReceivedCertificates(List<String> receivedCertificates) {
    this.receivedCertificates = receivedCertificates;
  }
}
