package money.fluid.fastpay4j.server.spring.converters;

import money.fluid.fastpay4j.core.FastPayAddress;
import money.fluid.fastpay4j.core.keys.Ed25519PublicKey;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A {@link Converter} for {@link FastPayAddress}.
 */
@Component
@ConfigurationPropertiesBinding
public class StringToFastPayAddressConverter implements Converter<String, FastPayAddress> {

  @Override
  public FastPayAddress convert(final String s) {
    return FastPayAddress.builder()
      .edPublicKey(Ed25519PublicKey.builder()
        .bytes(Base64.decode(s))
        .build())
      .build();
  }
}
