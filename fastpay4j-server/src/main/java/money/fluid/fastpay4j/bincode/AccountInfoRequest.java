package money.fluid.fastpay4j.bincode;


public final class AccountInfoRequest {
    public final EdPublicKeyBytes sender;
    public final java.util.Optional<SequenceNumber> request_sequence_number;
    public final java.util.Optional<@com.novi.serde.Unsigned Long> request_received_transfers_excluding_first_nth;

    public AccountInfoRequest(EdPublicKeyBytes sender, java.util.Optional<SequenceNumber> request_sequence_number, java.util.Optional<@com.novi.serde.Unsigned Long> request_received_transfers_excluding_first_nth) {
        java.util.Objects.requireNonNull(sender, "sender must not be null");
        java.util.Objects.requireNonNull(request_sequence_number, "request_sequence_number must not be null");
        java.util.Objects.requireNonNull(request_received_transfers_excluding_first_nth, "request_received_transfers_excluding_first_nth must not be null");
        this.sender = sender;
        this.request_sequence_number = request_sequence_number;
        this.request_received_transfers_excluding_first_nth = request_received_transfers_excluding_first_nth;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        sender.serialize(serializer);
        TraitHelpers.serialize_option_SequenceNumber(request_sequence_number, serializer);
        TraitHelpers.serialize_option_u64(request_received_transfers_excluding_first_nth, serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static AccountInfoRequest deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.sender = EdPublicKeyBytes.deserialize(deserializer);
        builder.request_sequence_number = TraitHelpers.deserialize_option_SequenceNumber(deserializer);
        builder.request_received_transfers_excluding_first_nth = TraitHelpers.deserialize_option_u64(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static AccountInfoRequest bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        AccountInfoRequest value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AccountInfoRequest other = (AccountInfoRequest) obj;
        if (!java.util.Objects.equals(this.sender, other.sender)) { return false; }
        if (!java.util.Objects.equals(this.request_sequence_number, other.request_sequence_number)) { return false; }
        if (!java.util.Objects.equals(this.request_received_transfers_excluding_first_nth, other.request_received_transfers_excluding_first_nth)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.sender != null ? this.sender.hashCode() : 0);
        value = 31 * value + (this.request_sequence_number != null ? this.request_sequence_number.hashCode() : 0);
        value = 31 * value + (this.request_received_transfers_excluding_first_nth != null ? this.request_received_transfers_excluding_first_nth.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public EdPublicKeyBytes sender;
        public java.util.Optional<SequenceNumber> request_sequence_number;
        public java.util.Optional<@com.novi.serde.Unsigned Long> request_received_transfers_excluding_first_nth;

        public AccountInfoRequest build() {
            return new AccountInfoRequest(
                sender,
                request_sequence_number,
                request_received_transfers_excluding_first_nth
            );
        }
    }
}
