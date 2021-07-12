package money.fluid.fastpay.bincode;


public final class AccountInfoResponse {
    public final EdPublicKeyBytes sender;
    public final Balance balance;
    public final SequenceNumber next_sequence_number;
    public final java.util.Optional<SignedTransferOrder> pending_confirmation;
    public final java.util.Optional<CertifiedTransferOrder> requested_certificate;
    public final java.util.List<CertifiedTransferOrder> requested_received_transfers;

    public AccountInfoResponse(EdPublicKeyBytes sender, Balance balance, SequenceNumber next_sequence_number, java.util.Optional<SignedTransferOrder> pending_confirmation, java.util.Optional<CertifiedTransferOrder> requested_certificate, java.util.List<CertifiedTransferOrder> requested_received_transfers) {
        java.util.Objects.requireNonNull(sender, "sender must not be null");
        java.util.Objects.requireNonNull(balance, "balance must not be null");
        java.util.Objects.requireNonNull(next_sequence_number, "next_sequence_number must not be null");
        java.util.Objects.requireNonNull(pending_confirmation, "pending_confirmation must not be null");
        java.util.Objects.requireNonNull(requested_certificate, "requested_certificate must not be null");
        java.util.Objects.requireNonNull(requested_received_transfers, "requested_received_transfers must not be null");
        this.sender = sender;
        this.balance = balance;
        this.next_sequence_number = next_sequence_number;
        this.pending_confirmation = pending_confirmation;
        this.requested_certificate = requested_certificate;
        this.requested_received_transfers = requested_received_transfers;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        sender.serialize(serializer);
        balance.serialize(serializer);
        next_sequence_number.serialize(serializer);
        TraitHelpers.serialize_option_SignedTransferOrder(pending_confirmation, serializer);
        TraitHelpers.serialize_option_CertifiedTransferOrder(requested_certificate, serializer);
        TraitHelpers.serialize_vector_CertifiedTransferOrder(requested_received_transfers, serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static AccountInfoResponse deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.sender = EdPublicKeyBytes.deserialize(deserializer);
        builder.balance = Balance.deserialize(deserializer);
        builder.next_sequence_number = SequenceNumber.deserialize(deserializer);
        builder.pending_confirmation = TraitHelpers.deserialize_option_SignedTransferOrder(deserializer);
        builder.requested_certificate = TraitHelpers.deserialize_option_CertifiedTransferOrder(deserializer);
        builder.requested_received_transfers = TraitHelpers.deserialize_vector_CertifiedTransferOrder(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static AccountInfoResponse bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        AccountInfoResponse value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AccountInfoResponse other = (AccountInfoResponse) obj;
        if (!java.util.Objects.equals(this.sender, other.sender)) { return false; }
        if (!java.util.Objects.equals(this.balance, other.balance)) { return false; }
        if (!java.util.Objects.equals(this.next_sequence_number, other.next_sequence_number)) { return false; }
        if (!java.util.Objects.equals(this.pending_confirmation, other.pending_confirmation)) { return false; }
        if (!java.util.Objects.equals(this.requested_certificate, other.requested_certificate)) { return false; }
        if (!java.util.Objects.equals(this.requested_received_transfers, other.requested_received_transfers)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.sender != null ? this.sender.hashCode() : 0);
        value = 31 * value + (this.balance != null ? this.balance.hashCode() : 0);
        value = 31 * value + (this.next_sequence_number != null ? this.next_sequence_number.hashCode() : 0);
        value = 31 * value + (this.pending_confirmation != null ? this.pending_confirmation.hashCode() : 0);
        value = 31 * value + (this.requested_certificate != null ? this.requested_certificate.hashCode() : 0);
        value = 31 * value + (this.requested_received_transfers != null ? this.requested_received_transfers.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public EdPublicKeyBytes sender;
        public Balance balance;
        public SequenceNumber next_sequence_number;
        public java.util.Optional<SignedTransferOrder> pending_confirmation;
        public java.util.Optional<CertifiedTransferOrder> requested_certificate;
        public java.util.List<CertifiedTransferOrder> requested_received_transfers;

        public AccountInfoResponse build() {
            return new AccountInfoResponse(
                sender,
                balance,
                next_sequence_number,
                pending_confirmation,
                requested_certificate,
                requested_received_transfers
            );
        }
    }
}
