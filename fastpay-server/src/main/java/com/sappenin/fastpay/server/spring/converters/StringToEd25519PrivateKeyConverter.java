package com.sappenin.fastpay.server.spring.converters;

import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * A {@link Converter} for {@link Ed25519PrivateKey}.
 */
@Component
@ConfigurationPropertiesBinding
public class StringToEd25519PrivateKeyConverter implements Converter<String, Ed25519PrivateKey> {

  @Override
  public Ed25519PrivateKey convert(final String s) {
    Objects.requireNonNull(s);
    return Ed25519PrivateKey.of(Base64.decode(s));
  }
}
