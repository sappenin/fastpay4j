package com.sappenin.fastpay.server.spring.config;

import static org.assertj.core.api.Assertions.assertThat;

import com.sappenin.fastpay.server.settings.AuthoritySettings;
import com.sappenin.fastpay.core.NetworkProtocol;
import com.sappenin.fastpay.server.settings.ServerSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.function.Supplier;

/**
 * Unit test to validate loading of properties into a {@link ServerSettingsFromPropertyFile}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ServerSettingsFromPropertyFileTest.TestConfiguration.class)
@ActiveProfiles("property-unit-tests")
public class ServerSettingsFromPropertyFileTest {

  @Autowired
  ServerSettingsFromPropertyFile serverSettings;

  @Test
  public void configShouldLoad() {
    assertThat(serverSettings.authority().networkProtocol()).isEqualTo(NetworkProtocol.TCP);
    assertThat(serverSettings.authority().fastPayAddress().toString())
      .isEqualTo("du14iIv21IxHW2tNO1htzuEMQBlXrjsK7pp+ySGO2Ak=");
    assertThat(serverSettings.authority().host()).isEqualTo("0.0.0.0");
    assertThat(serverSettings.authority().basePort()).isEqualTo(9100);
    assertThat(serverSettings.authority().numShards()).isEqualTo(4);
    assertThat(serverSettings.serverKey().asBase64())
      .isEqualTo("D5siqT/vgcwKGCgK5peCDKolzUcmOYbdqxNXcGJ8RaR27XiIi/bUjEdba007WG3O4QxAGVeuOwrumn7JIY7YCQ==");
    // Ensure that toString has private-key redacted.
    assertThat(serverSettings.serverKey().toString()).isEqualTo("DefaultEd25519PrivateKey{}");

    final AuthoritySettings committee0 = serverSettings.committees().get(0);
    assertThat(committee0.networkProtocol()).isEqualTo(NetworkProtocol.TCP);
    assertThat(committee0.fastPayAddress().toString()).isEqualTo("du14iIv21IxHW2tNO1htzuEMQBlXrjsK7pp+ySGO2Ak=");
    assertThat(committee0.host()).isEqualTo("127.0.0.1");
    assertThat(committee0.basePort()).isEqualTo(9100);
    assertThat(committee0.numShards()).isEqualTo(4);

    final AuthoritySettings committee1 = serverSettings.committees().get(1);
    assertThat(committee1.networkProtocol()).isEqualTo(NetworkProtocol.TCP);
    assertThat(committee1.fastPayAddress().toString()).isEqualTo("e8SS0ifNr9w3veVNJOlEDfvOmO5A4hrmUSdTfVjDHG8=");
    assertThat(committee1.host()).isEqualTo("127.0.0.1");
    assertThat(committee1.basePort()).isEqualTo(9200);
    assertThat(committee1.numShards()).isEqualTo(4);

    final AuthoritySettings committee2 = serverSettings.committees().get(2);
    assertThat(committee2.networkProtocol()).isEqualTo(NetworkProtocol.TCP);
    assertThat(committee2.fastPayAddress().toString()).isEqualTo("vMfxmo3uNERsDJ8pLSOTly32rWlCdjHypmNFac4ZrkU=");
    assertThat(committee2.host()).isEqualTo("127.0.0.1");
    assertThat(committee2.basePort()).isEqualTo(9300);
    assertThat(committee2.numShards()).isEqualTo(4);

    final AuthoritySettings committee3 = serverSettings.committees().get(3);
    assertThat(committee3.networkProtocol()).isEqualTo(NetworkProtocol.TCP);
    assertThat(committee3.fastPayAddress().toString()).isEqualTo("tR9hCqW/EuGV73bWPjLjgFF1wv2CgTOYXWee0mcWGlQ=");
    assertThat(committee3.host()).isEqualTo("127.0.0.1");
    assertThat(committee3.basePort()).isEqualTo(9400);
    assertThat(committee3.numShards()).isEqualTo(4);

  }

  @EnableConfigurationProperties(ServerSettingsFromPropertyFile.class)
  @Import(ConverterConfig.class)
  public static class TestConfiguration {

    @Bean
    public Supplier<ServerSettings> connectorSettingsSupplier(ServerSettingsFromPropertyFile settings) {
      return () -> settings;
    }

  }
}