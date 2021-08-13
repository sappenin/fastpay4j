package com.sappenin.fastpay.core.messages;

import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.PrimaryAddress;
import org.immutables.value.Value;

/**
 * A fully-qualified FastPay system address.
 */
@Value.Immutable
public interface Address {

  // TODO: If this class is kept around, make this optional because some authorities may not operate as a side-chain,
  // and thus may not have a primary.
  PrimaryAddress primaryAddress();

  FastPayAddress fastPayAddress();
}
