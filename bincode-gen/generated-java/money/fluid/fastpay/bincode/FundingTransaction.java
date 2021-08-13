package com.sappenin.fastpay.bincode;


public final class FundingTransaction {
    public final EdPublicKeyBytes recipient;
    public final Amount primary_coins;

    public FundingTransaction(EdPublicKeyBytes recipient, Amount primary_coins) {
        java.util.Objects.requireNonNull(recipient, "recipient must not be null");
        java.util.Objects.requireNonNull(primary_coins, "primary_coins must not be null");
        this.recipient = recipient;
        this.primary_coins = primary_coins;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        recipient.serialize(serializer);
        primary_coins.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static FundingTransaction deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.recipient = EdPublicKeyBytes.deserialize(deserializer);
        builder.primary_coins = Amount.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static FundingTransaction bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        FundingTransaction value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        FundingTransaction other = (FundingTransaction) obj;
        if (!java.util.Objects.equals(this.recipient, other.recipient)) { return false; }
        if (!java.util.Objects.equals(this.primary_coins, other.primary_coins)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.recipient != null ? this.recipient.hashCode() : 0);
        value = 31 * value + (this.primary_coins != null ? this.primary_coins.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public EdPublicKeyBytes recipient;
        public Amount primary_coins;

        public FundingTransaction build() {
            return new FundingTransaction(
                recipient,
                primary_coins
            );
        }
    }
}
