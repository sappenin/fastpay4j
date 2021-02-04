package money.fluid.fastpay4j.core.keys;

import com.google.common.base.MoreObjects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.Var;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link Ed25519PublicKey}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableEd25519PublicKey.builder()}.
 */
@Generated(from = "Ed25519PublicKey", generator = "Immutables")
@SuppressWarnings({"all"})
@ParametersAreNonnullByDefault
@javax.annotation.processing.Generated("org.immutables.processor.ProxyProcessor")
@Immutable
@CheckReturnValue
public final class ImmutableEd25519PublicKey implements Ed25519PublicKey {
  private final byte[] bytes;
  private transient final String asBase64;

  private ImmutableEd25519PublicKey(byte[] bytes) {
    this.bytes = bytes;
    this.asBase64 = Objects.requireNonNull(Ed25519PublicKey.super.asBase64(), "asBase64");
  }

  /**
   * The bytes of this public key.
   * @return A byte array.
   */
  @Override
  public byte[] bytes() {
    return bytes.clone();
  }

  /**
   * @return The computed-at-construction value of the {@code asBase64} attribute
   */
  @Override
  public String asBase64() {
    return asBase64;
  }

  /**
   * Copy the current immutable object with elements that replace the content of {@link Ed25519PublicKey#bytes() bytes}.
   * The array is cloned before being saved as attribute values.
   * @param elements The non-null elements for bytes
   * @return A modified copy of {@code this} object
   */
  public final ImmutableEd25519PublicKey withBytes(byte... elements) {
    byte[] newValue = elements.clone();
    return new ImmutableEd25519PublicKey(newValue);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableEd25519PublicKey} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(@Nullable Object another) {
    if (this == another) return true;
    return another instanceof ImmutableEd25519PublicKey
        && equalTo((ImmutableEd25519PublicKey) another);
  }

  private boolean equalTo(ImmutableEd25519PublicKey another) {
    return Arrays.equals(bytes, another.bytes)
        && asBase64.equals(another.asBase64);
  }

  /**
   * Computes a hash code from attributes: {@code bytes}, {@code asBase64}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    @Var int h = 5381;
    h += (h << 5) + Arrays.hashCode(bytes);
    h += (h << 5) + asBase64.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code Ed25519PublicKey} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return MoreObjects.toStringHelper("Ed25519PublicKey")
        .omitNullValues()
        .add("bytes", Arrays.toString(bytes))
        .add("asBase64", asBase64)
        .toString();
  }

  /**
   * Creates an immutable copy of a {@link Ed25519PublicKey} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable Ed25519PublicKey instance
   */
  public static ImmutableEd25519PublicKey copyOf(Ed25519PublicKey instance) {
    if (instance instanceof ImmutableEd25519PublicKey) {
      return (ImmutableEd25519PublicKey) instance;
    }
    return ImmutableEd25519PublicKey.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableEd25519PublicKey ImmutableEd25519PublicKey}.
   * <pre>
   * ImmutableEd25519PublicKey.builder()
   *    .bytes(byte) // required {@link Ed25519PublicKey#bytes() bytes}
   *    .build();
   * </pre>
   * @return A new ImmutableEd25519PublicKey builder
   */
  public static ImmutableEd25519PublicKey.Builder builder() {
    return new ImmutableEd25519PublicKey.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableEd25519PublicKey ImmutableEd25519PublicKey}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "Ed25519PublicKey", generator = "Immutables")
  @NotThreadSafe
  public static final class Builder {
    private static final long INIT_BIT_BYTES = 0x1L;
    private long initBits = 0x1L;

    private @Nullable byte[] bytes;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code money.fluid.fastpay4j.core.keys.PublicKey} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(PublicKey instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code money.fluid.fastpay4j.core.keys.Ed25519PublicKey} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder from(Ed25519PublicKey instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      if (object instanceof PublicKey) {
        PublicKey instance = (PublicKey) object;
        bytes(instance.bytes());
      }
    }

    /**
     * Initializes the value for the {@link Ed25519PublicKey#bytes() bytes} attribute.
     * @param bytes The elements for bytes
     * @return {@code this} builder for use in a chained invocation
     */
    @CanIgnoreReturnValue 
    public final Builder bytes(byte... bytes) {
      this.bytes = bytes.clone();
      initBits &= ~INIT_BIT_BYTES;
      return this;
    }

    /**
     * Builds a new {@link ImmutableEd25519PublicKey ImmutableEd25519PublicKey}.
     * @return An immutable instance of Ed25519PublicKey
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableEd25519PublicKey build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableEd25519PublicKey(bytes);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_BYTES) != 0) attributes.add("bytes");
      return "Cannot build Ed25519PublicKey, some of required attributes are not set " + attributes;
    }
  }
}
