package money.fluid.fastpay4j.core.messages;

import com.google.common.primitives.UnsignedLong;
import money.fluid.fastpay4j.core.FastPayAddress;
import money.fluid.fastpay4j.core.TransactionIndex;
import org.immutables.value.Value;

@Value.Immutable
public interface PrimarySynchronizationOrder {

  FastPayAddress recipient();

  UnsignedLong amount();

  TransactionIndex transactionIndex();
}
