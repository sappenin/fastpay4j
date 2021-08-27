package com.sappenin.fastpay.server.spring.converters;

import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.keys.Ed25519PublicKey;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * A {@link Converter} for {@link FastPayAddress}.
 */
@Component
@ConfigurationPropertiesBinding
public class StringToFastPayAddressConverter implements Converter<String, FastPayAddress> {

  @Override
  public FastPayAddress convert(final String s) {
    Objects.requireNonNull(s);
    return FastPayAddress.builder()
      .edPublicKey(Ed25519PublicKey.of(Base64.decode(s)))
      .build();
  }
}
