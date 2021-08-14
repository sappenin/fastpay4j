package com.sappenin.fastpay.core.messages;

import com.google.common.primitives.UnsignedLong;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.FastpayModel;
import com.sappenin.fastpay.core.TransactionIndex;
import org.immutables.value.Value;

@Value.Immutable
public interface PrimarySynchronizationOrder extends FastpayModel {

  FastPayAddress recipient();

  UnsignedLong amount();

  TransactionIndex transactionIndex();
}
