package money.fluid.fastpay4j.bincode;


public final class CrossShardUpdate {
    public final @com.novi.serde.Unsigned Integer shard_id;
    public final CertifiedTransferOrder transfer_certificate;

    public CrossShardUpdate(@com.novi.serde.Unsigned Integer shard_id, CertifiedTransferOrder transfer_certificate) {
        java.util.Objects.requireNonNull(shard_id, "shard_id must not be null");
        java.util.Objects.requireNonNull(transfer_certificate, "transfer_certificate must not be null");
        this.shard_id = shard_id;
        this.transfer_certificate = transfer_certificate;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        serializer.serialize_u32(shard_id);
        transfer_certificate.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static CrossShardUpdate deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.shard_id = deserializer.deserialize_u32();
        builder.transfer_certificate = CertifiedTransferOrder.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static CrossShardUpdate bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        CrossShardUpdate value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CrossShardUpdate other = (CrossShardUpdate) obj;
        if (!java.util.Objects.equals(this.shard_id, other.shard_id)) { return false; }
        if (!java.util.Objects.equals(this.transfer_certificate, other.transfer_certificate)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.shard_id != null ? this.shard_id.hashCode() : 0);
        value = 31 * value + (this.transfer_certificate != null ? this.transfer_certificate.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public @com.novi.serde.Unsigned Integer shard_id;
        public CertifiedTransferOrder transfer_certificate;

        public CrossShardUpdate build() {
            return new CrossShardUpdate(
                shard_id,
                transfer_certificate
            );
        }
    }
}
