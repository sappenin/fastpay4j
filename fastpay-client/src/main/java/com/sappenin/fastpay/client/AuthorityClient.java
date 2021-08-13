package com.sappenin.fastpay.client;

import com.google.common.primitives.UnsignedLong;
import com.sappenin.fastpay.core.CertifiedTransferOrder;
import com.sappenin.fastpay.core.FastPayAddress;
import com.sappenin.fastpay.core.UserData;
import com.sappenin.fastpay.core.bincode.AccountInfoRequest;
import com.sappenin.fastpay.core.bincode.AccountInfoResponse;
import reactor.core.publisher.Mono;

/**
 * A client for communicating with a Fastpay authority. Note that operations are considered successful when they
 * successfully reach a quorum of authorities.
 */
public interface AuthorityClient {

  /**
   * Send money to a FastPay account.
   */
  Mono<CertifiedTransferOrder> transferToFastpayAccount(
    UnsignedLong amount,
    FastPayAddress recipient,
    UserData userData
  );


  Mono<AccountInfoResponse> getAccountInfo(AccountInfoRequest accountInfoRequest);
}
