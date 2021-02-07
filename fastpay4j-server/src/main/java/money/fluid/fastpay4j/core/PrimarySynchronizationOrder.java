package money.fluid.fastpay4j.core;

import com.google.common.primitives.UnsignedLong;
import org.immutables.value.Value;

@Value.Immutable
public interface PrimarySynchronizationOrder {

  FastPayAddress recipient();

  UnsignedLong amount();

  TransactionIndex transactionIndex();
}
