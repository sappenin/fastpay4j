package money.fluid.fastpay4j.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import money.fluid.fastpay4j.core.keys.PublicKey;
import org.immutables.value.Generated;

/**
 * A wrapped {@link PublicKey} representing an address for a FastPay account.
 */
@Generated(from = "Ids._FastPayAddress", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class FastPayAddress extends Ids._FastPayAddress {
  private final PublicKey value;

  private FastPayAddress(PublicKey value) {
    this.value = Objects.requireNonNull(value, "value");
  }

  private FastPayAddress(FastPayAddress original, PublicKey value) {
    this.value = value;
  }

  /**
   * @return The value of the {@code value} attribute
   */
  @JsonProperty("value")
  @Override
  public PublicKey value() {
    return value;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link FastPayAddress#value() value} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for value
   * @return A modified copy of the {@code this} object
   */
  public final FastPayAddress withValue(PublicKey value) {
    if (this.value == value) return this;
    PublicKey newValue = Objects.requireNonNull(value, "value");
    return validate(new FastPayAddress(this, newValue));
  }

  /**
   * This instance is equal to all instances of {@code FastPayAddress} that have equal attribute values.
   * As instances of the {@code FastPayAddress} class are interned, the {@code equals} method is implemented
   * as an efficient reference equality check.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    return this == another;
  }

  private boolean equalTo(FastPayAddress another) {
    return super.equals(another);
  }

  /**
   * Utility type used to correctly read immutable object from JSON representation.
   * @deprecated Do not use this type directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Generated(from = "Ids._FastPayAddress", generator = "Immutables")
  @Deprecated
  @SuppressWarnings("Immutable")
  @JsonDeserialize
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE)
  static final class Json extends Ids._FastPayAddress {
    @Nullable PublicKey value;
    @JsonProperty("value")
    public void setValue(PublicKey value) {
      this.value = value;
    }
    @Override
    public PublicKey value() { throw new UnsupportedOperationException(); }
  }

  /**
   * @param json A JSON-bindable data structure
   * @return An immutable value type
   * @deprecated Do not use this method directly, it exists only for the <em>Jackson</em>-binding infrastructure
   */
  @Deprecated
  @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
  static FastPayAddress fromJson(Json json) {
    FastPayAddress.Builder builder = FastPayAddress.builder();
    if (json.value != null) {
      builder.value(json.value);
    }
    return builder.build();
  }

  @Generated(from = "Ids._FastPayAddress", generator = "Immutables")
  private static class InternProxy {
    final FastPayAddress instance;

    InternProxy(FastPayAddress instance) {
      this.instance = instance;
    }

    @Override
    public boolean equals(@Nullable Object another) {
      return another != null && instance.equalTo(((InternProxy) another).instance);
    }

    @Override
    public int hashCode() {
      return instance.hashCode();
    }
  }

  private static final Interner<InternProxy> INTERNER = Interners.newStrongInterner();

  /**
   * Construct a new immutable {@code FastPayAddress} instance.
   * @param value The value for the {@code value} attribute
   * @return An immutable FastPayAddress instance
   */
  public static FastPayAddress of(PublicKey value) {
    return validate(new FastPayAddress(value));
  }

  private static FastPayAddress validate(FastPayAddress instance) {
    return INTERNER.intern(new InternProxy(instance)).instance;
  }

  /**
   * Creates an immutable copy of a {@link Ids._FastPayAddress} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable FastPayAddress instance
   */
  static FastPayAddress copyOf(Ids._FastPayAddress instance) {
    if (instance instanceof FastPayAddress) {
      return (FastPayAddress) instance;
    }
    return FastPayAddress.builder()
        .from(instance)
        .build();
  }

  private Object readResolve() throws ObjectStreamException {
    return validate(this);
  }

  /**
   * Creates a builder for {@link FastPayAddress FastPayAddress}.
   * <pre>
   * FastPayAddress.builder()
   *    .value(money.fluid.fastpay4j.core.keys.PublicKey) // required {@link FastPayAddress#value() value}
   *    .build();
   * </pre>
   * @return A new FastPayAddress builder
   */
  public static FastPayAddress.Builder builder() {
    return new FastPayAddress.Builder();
  }

  /**
   * Builds instances of type {@link FastPayAddress FastPayAddress}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "Ids._FastPayAddress", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_VALUE = 0x1L;
    private long initBits = 0x1L;

    private @Nullable PublicKey value;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code FastPayAddress} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(FastPayAddress instance) {
      return from((Ids._FastPayAddress) instance);
    }

    /**
     * Copy abstract value type {@code _FastPayAddress} instance into builder.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    final Builder from(Ids._FastPayAddress instance) {
      Objects.requireNonNull(instance, "instance");
      value(instance.value());
      return this;
    }

    /**
     * Initializes the value for the {@link FastPayAddress#value() value} attribute.
     * @param value The value for value 
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    @JsonProperty("value")
    public final Builder value(PublicKey value) {
      this.value = Objects.requireNonNull(value, "value");
      initBits &= ~INIT_BIT_VALUE;
      return this;
    }

    /**
     * Builds a new {@link FastPayAddress FastPayAddress}.
     * @return An immutable instance of FastPayAddress
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public FastPayAddress build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return FastPayAddress.validate(new FastPayAddress(null, value));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_VALUE) != 0) attributes.add("value");
      return "Cannot build FastPayAddress, some of required attributes are not set " + attributes;
    }
  }
}
