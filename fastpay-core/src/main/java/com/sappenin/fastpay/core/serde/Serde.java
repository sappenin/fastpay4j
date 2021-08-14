package com.sappenin.fastpay.core.serde;

/**
 * Serialization and Deserialization (Serde) for Fastpay binary objects.
 */
public interface Serde {

  /**
   * Serialize an {@link T} to an array of bytes.
   *
   * @param input A {@link T} to serialize.
   *
   * @return an array of bytes.
   */
  <T> byte[] serialize(final T input);

  /**
   * Deserialize an array of bytes into a typed object.
   *
   * @param bytes A byte array to deserialize.
   *
   * @return an instance of {@link T}.
   */
  <T> T deserialize(Class<T> clazz, byte[] bytes);
}
