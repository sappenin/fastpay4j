package com.sappenin.fastpay.bincode;


public final class PrimarySynchronizationOrder {
    public final EdPublicKeyBytes recipient;
    public final Amount amount;
    public final SequenceNumber transaction_index;

    public PrimarySynchronizationOrder(EdPublicKeyBytes recipient, Amount amount, SequenceNumber transaction_index) {
        java.util.Objects.requireNonNull(recipient, "recipient must not be null");
        java.util.Objects.requireNonNull(amount, "amount must not be null");
        java.util.Objects.requireNonNull(transaction_index, "transaction_index must not be null");
        this.recipient = recipient;
        this.amount = amount;
        this.transaction_index = transaction_index;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        recipient.serialize(serializer);
        amount.serialize(serializer);
        transaction_index.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static PrimarySynchronizationOrder deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.recipient = EdPublicKeyBytes.deserialize(deserializer);
        builder.amount = Amount.deserialize(deserializer);
        builder.transaction_index = SequenceNumber.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static PrimarySynchronizationOrder bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        PrimarySynchronizationOrder value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PrimarySynchronizationOrder other = (PrimarySynchronizationOrder) obj;
        if (!java.util.Objects.equals(this.recipient, other.recipient)) { return false; }
        if (!java.util.Objects.equals(this.amount, other.amount)) { return false; }
        if (!java.util.Objects.equals(this.transaction_index, other.transaction_index)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.recipient != null ? this.recipient.hashCode() : 0);
        value = 31 * value + (this.amount != null ? this.amount.hashCode() : 0);
        value = 31 * value + (this.transaction_index != null ? this.transaction_index.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public EdPublicKeyBytes recipient;
        public Amount amount;
        public SequenceNumber transaction_index;

        public PrimarySynchronizationOrder build() {
            return new PrimarySynchronizationOrder(
                recipient,
                amount,
                transaction_index
            );
        }
    }
}
