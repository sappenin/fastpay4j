package com.sappenin.fastpay.bincode;


public final class Transfer {
    public final EdPublicKeyBytes sender;
    public final Address recipient;
    public final Amount amount;
    public final SequenceNumber sequence_number;
    public final UserData user_data;

    public Transfer(EdPublicKeyBytes sender, Address recipient, Amount amount, SequenceNumber sequence_number, UserData user_data) {
        java.util.Objects.requireNonNull(sender, "sender must not be null");
        java.util.Objects.requireNonNull(recipient, "recipient must not be null");
        java.util.Objects.requireNonNull(amount, "amount must not be null");
        java.util.Objects.requireNonNull(sequence_number, "sequence_number must not be null");
        java.util.Objects.requireNonNull(user_data, "user_data must not be null");
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.sequence_number = sequence_number;
        this.user_data = user_data;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        sender.serialize(serializer);
        recipient.serialize(serializer);
        amount.serialize(serializer);
        sequence_number.serialize(serializer);
        user_data.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static Transfer deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.sender = EdPublicKeyBytes.deserialize(deserializer);
        builder.recipient = Address.deserialize(deserializer);
        builder.amount = Amount.deserialize(deserializer);
        builder.sequence_number = SequenceNumber.deserialize(deserializer);
        builder.user_data = UserData.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static Transfer bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        Transfer value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Transfer other = (Transfer) obj;
        if (!java.util.Objects.equals(this.sender, other.sender)) { return false; }
        if (!java.util.Objects.equals(this.recipient, other.recipient)) { return false; }
        if (!java.util.Objects.equals(this.amount, other.amount)) { return false; }
        if (!java.util.Objects.equals(this.sequence_number, other.sequence_number)) { return false; }
        if (!java.util.Objects.equals(this.user_data, other.user_data)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.sender != null ? this.sender.hashCode() : 0);
        value = 31 * value + (this.recipient != null ? this.recipient.hashCode() : 0);
        value = 31 * value + (this.amount != null ? this.amount.hashCode() : 0);
        value = 31 * value + (this.sequence_number != null ? this.sequence_number.hashCode() : 0);
        value = 31 * value + (this.user_data != null ? this.user_data.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public EdPublicKeyBytes sender;
        public Address recipient;
        public Amount amount;
        public SequenceNumber sequence_number;
        public UserData user_data;

        public Transfer build() {
            return new Transfer(
                sender,
                recipient,
                amount,
                sequence_number,
                user_data
            );
        }
    }
}
