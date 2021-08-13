package com.sappenin.fastpay.bincode;


public final class SignedTransferOrder {
    public final TransferOrder value;
    public final EdPublicKeyBytes authority;
    public final Signature signature;

    public SignedTransferOrder(TransferOrder value, EdPublicKeyBytes authority, Signature signature) {
        java.util.Objects.requireNonNull(value, "value must not be null");
        java.util.Objects.requireNonNull(authority, "authority must not be null");
        java.util.Objects.requireNonNull(signature, "signature must not be null");
        this.value = value;
        this.authority = authority;
        this.signature = signature;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        value.serialize(serializer);
        authority.serialize(serializer);
        signature.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static SignedTransferOrder deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.value = TransferOrder.deserialize(deserializer);
        builder.authority = EdPublicKeyBytes.deserialize(deserializer);
        builder.signature = Signature.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static SignedTransferOrder bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        SignedTransferOrder value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SignedTransferOrder other = (SignedTransferOrder) obj;
        if (!java.util.Objects.equals(this.value, other.value)) { return false; }
        if (!java.util.Objects.equals(this.authority, other.authority)) { return false; }
        if (!java.util.Objects.equals(this.signature, other.signature)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.value != null ? this.value.hashCode() : 0);
        value = 31 * value + (this.authority != null ? this.authority.hashCode() : 0);
        value = 31 * value + (this.signature != null ? this.signature.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public TransferOrder value;
        public EdPublicKeyBytes authority;
        public Signature signature;

        public SignedTransferOrder build() {
            return new SignedTransferOrder(
                value,
                authority,
                signature
            );
        }
    }
}
