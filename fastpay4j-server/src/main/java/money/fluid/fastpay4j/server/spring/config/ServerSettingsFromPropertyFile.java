package money.fluid.fastpay4j.server.spring.config;

import com.google.common.collect.Lists;
import money.fluid.fastpay4j.core.keys.Ed25519PrivateKey;
import money.fluid.fastpay4j.server.settings.AuthoritySettings;
import money.fluid.fastpay4j.server.settings.ServerSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A pojo for automatic mapping of configuration properties via Spring's {@link ConfigurationProperties} annotation.
 */
@ConfigurationProperties(prefix = "fastpay4j.server")
public class ServerSettingsFromPropertyFile implements ServerSettings {

  private Ed25519PrivateKey serverKey;
  private AuthoritySettingsFromPropertyFile authority;
  private List<AuthoritySettingsFromPropertyFile> committees = Lists.newArrayList();

  public Ed25519PrivateKey getServerKey() {
    return serverKey;
  }

  @Override
  public Ed25519PrivateKey serverKey() {
    return serverKey;
  }

  public void setServerKey(Ed25519PrivateKey serverKey) {
    this.serverKey = serverKey;
  }

  public AuthoritySettingsFromPropertyFile getAuthority() {
    return authority;
  }

  @Override
  public AuthoritySettingsFromPropertyFile authority() {
    return authority;
  }


  public void setAuthority(AuthoritySettingsFromPropertyFile authority) {
    this.authority = authority;
  }

  public List<AuthoritySettingsFromPropertyFile> getCommittees() {
    return committees;
  }

  @Override
  public List<AuthoritySettings> committees() {
    return this.committees.stream()
      .map($ -> (AuthoritySettings) $)
      .collect(Collectors.toList());
  }

  public void setCommittees(List<AuthoritySettingsFromPropertyFile> committees) {
    this.committees = committees;
  }


}
