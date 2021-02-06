package money.fluid.fastpay4j.server.spring.converters;

import money.fluid.fastpay4j.core.keys.Ed25519PrivateKey;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A {@link Converter} for {@link Ed25519PrivateKey}.
 */
@Component
@ConfigurationPropertiesBinding
public class StringToEd25519PrivateKeyConverter implements Converter<String, Ed25519PrivateKey> {

  @Override
  public Ed25519PrivateKey convert(final String s) {
    return Ed25519PrivateKey.builder()
      .bytes(Base64.decode(s))
      .build();
  }
}
