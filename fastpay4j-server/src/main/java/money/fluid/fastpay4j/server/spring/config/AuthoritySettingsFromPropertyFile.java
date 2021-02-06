package money.fluid.fastpay4j.server.spring.config;

import money.fluid.fastpay4j.core.FastPayAddress;
import money.fluid.fastpay4j.server.settings.AuthoritySettings;
import money.fluid.fastpay4j.server.settings.NetworkProtocol;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A pojo for automatic mapping of configuration properties via Spring's {@link ConfigurationProperties} annotation.
 */
public class AuthoritySettingsFromPropertyFile implements AuthoritySettings {

  private NetworkProtocol networkProtocol;
  private FastPayAddress address;
  private String host;
  private int basePort;
  private int numShards;

  public NetworkProtocol getNetworkProtocol() {
    return networkProtocol;
  }

  @Override
  public NetworkProtocol networkProtocol() {
    return networkProtocol;
  }

  public void setNetworkProtocol(NetworkProtocol networkProtocol) {
    this.networkProtocol = networkProtocol;
  }

  public FastPayAddress getAddress() {
    return address;
  }

  @Override
  public FastPayAddress fastPayAddress() {
    return address;
  }

  public void setAddress(FastPayAddress address) {
    this.address = address;
  }

  public String getHost() {
    return host;
  }

  @Override
  public String host() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getBasePort() {
    return basePort;
  }

  @Override
  public int basePort() {
    return basePort;
  }

  public void setBasePort(int basePort) {
    this.basePort = basePort;
  }

  public int getNumShards() {
    return numShards;
  }

  @Override
  public int numShards() {
    return numShards;
  }

  public void setNumShards(int numShards) {
    this.numShards = numShards;
  }


}
