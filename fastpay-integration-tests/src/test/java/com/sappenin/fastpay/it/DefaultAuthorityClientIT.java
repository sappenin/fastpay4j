package com.sappenin.fastpay.it;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.common.primitives.UnsignedLong;
import com.sappenin.fastpay.client.AuthorityClient;
import com.sappenin.fastpay.client.AuthorityClientState;
import com.sappenin.fastpay.client.ClientNetworkOptions;
import com.sappenin.fastpay.client.DefaultAuthorityClient;
import com.sappenin.fastpay.core.AuthorityName;
import com.sappenin.fastpay.core.Committee;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.NetworkProtocol;
import com.sappenin.fastpay.core.SequenceNumber;
import com.sappenin.fastpay.core.bincode.AccountInfoRequest;
import com.sappenin.fastpay.core.bincode.AccountInfoResponse;
import com.sappenin.fastpay.core.keys.Ed25519PrivateKey;
import com.sappenin.fastpay.core.serde.BincodeSerdeUtils;
import com.sappenin.fastpay.core.serde.BincodeSerde;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * An integration test that validates the functionality of the {@link DefaultAuthorityClient}.
 */
class DefaultAuthorityClientIT extends AbstractIT {

  // These correspond to the default accounts in Fastpay Rust.
  private FastPayAddress CLIENT_ADDRESS =
    FastPayAddress.fromEd25519PublicKeyBase64("AzKoFE7qtEvQk+GHKt1YFJj28Pvk56rjV2T+fLowB+E=");

  private Ed25519PrivateKey CLIENT_PRIVATE_KEY = Ed25519PrivateKey.fromBase64(
    "D5siqT/vgcwKGCgK5peCDKolzUcmOYbdqxNXcGJ8RaR27XiIi/bUjEdba007WG3O4QxAGVeuOwrumn7JIY7YCQ=="
  );

  private FastPayAddress AUTHORITY_TWO =
    FastPayAddress.fromEd25519PublicKeyBase64("AQButm+RAgo/LOJLdpMwHItLbjX7ZOIExN3PkQYHlHI=");

  private FastPayAddress AUTHORITY_THREE =
    FastPayAddress.fromEd25519PublicKeyBase64("ATzH4SukNH+jkHwglWUlx9PizEwy+oMxTYda/HFpkDA=");

  private FastPayAddress AUTHORITY_FOUR =
    FastPayAddress.fromEd25519PublicKeyBase64("AUzCYx8H6hDxxaIe8b+O8FdmaYXUW/NMBWrGdNF6eV0=");

  /**
   * @deprecated Will go away once the Environment is wired up via Docker.
   */
  @Deprecated
  //private String HOST = "0.0.0.0";
  private String HOST = "localhost";

  /**
   * @deprecated Will go away once the Environment is wired up via Docker.
   */
  @Deprecated
  private int PORT = 9101;

  private AuthorityClient authorityClient;

  @BeforeEach
  void setUp() {
    final TreeMap<AuthorityName, UnsignedLong> votingRights = new TreeMap<>();

    // TODO: Does the client go into the voting rights maps?

    votingRights.put(AuthorityName.builder()
        .edPublicKey(AUTHORITY_TWO.edPublicKey())
        .build(),
      UnsignedLong.ONE
    );
    votingRights.put(AuthorityName.builder()
        .edPublicKey(AUTHORITY_THREE.edPublicKey())
        .build(),
      UnsignedLong.ONE
    );
    votingRights.put(AuthorityName.builder()
        .edPublicKey(AUTHORITY_FOUR.edPublicKey())
        .build(),
      UnsignedLong.ONE
    );

    this.authorityClient = new DefaultAuthorityClient(
      ClientNetworkOptions.builder()
        .baseAddress(HOST)
        .basePort(PORT)
        .networkProtocol(NetworkProtocol.UDP)
        .numShards(1)
        .build(),
      AuthorityClientState.builder()
        .address(CLIENT_ADDRESS)
        .secret(CLIENT_PRIVATE_KEY)
        .balance(BigInteger.TEN)
        .nextSequenceNumber(SequenceNumber.of(UnsignedLong.ONE))
        .authorityClients(Map.of())
        .committee(Committee.builder()
          .votingRights(votingRights)
          .build())
        .build(),
      new BincodeSerde()
    );
  }

  @Test
  void getAccountInfo() {
    AccountInfoRequest.Builder builder = new AccountInfoRequest.Builder();
    builder.sender = BincodeSerdeUtils.toEdPublicKeyBytes(CLIENT_ADDRESS.edPublicKey());
    builder.request_sequence_number = Optional.of(
      BincodeSerdeUtils.toSerializableSequenceNumber(SequenceNumber.of(UnsignedLong.ONE)));
    builder.request_received_transfers_excluding_first_nth = Optional.empty();
    final AccountInfoRequest request = builder.build();

    AccountInfoResponse response = this.authorityClient
      .getAccountInfo(request)
      .block(Duration.ofSeconds(5));

    assertThat(response).isNotNull();
    assertThat(response.balance.value).isEqualTo(BigInteger.TEN);
  }

}
