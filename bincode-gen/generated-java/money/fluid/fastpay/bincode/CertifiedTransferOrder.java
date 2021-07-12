package money.fluid.fastpay.bincode;


public final class CertifiedTransferOrder {
    public final TransferOrder value;
    public final java.util.List<com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>> signatures;

    public CertifiedTransferOrder(TransferOrder value, java.util.List<com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>> signatures) {
        java.util.Objects.requireNonNull(value, "value must not be null");
        java.util.Objects.requireNonNull(signatures, "signatures must not be null");
        this.value = value;
        this.signatures = signatures;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        value.serialize(serializer);
        TraitHelpers.serialize_vector_tuple2_EdPublicKeyBytes_Signature(signatures, serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static CertifiedTransferOrder deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.value = TransferOrder.deserialize(deserializer);
        builder.signatures = TraitHelpers.deserialize_vector_tuple2_EdPublicKeyBytes_Signature(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static CertifiedTransferOrder bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        CertifiedTransferOrder value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CertifiedTransferOrder other = (CertifiedTransferOrder) obj;
        if (!java.util.Objects.equals(this.value, other.value)) { return false; }
        if (!java.util.Objects.equals(this.signatures, other.signatures)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.value != null ? this.value.hashCode() : 0);
        value = 31 * value + (this.signatures != null ? this.signatures.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public TransferOrder value;
        public java.util.List<com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>> signatures;

        public CertifiedTransferOrder build() {
            return new CertifiedTransferOrder(
                value,
                signatures
            );
        }
    }
}
