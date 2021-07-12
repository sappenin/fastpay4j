package money.fluid.fastpay4j.bincode;


public final class TransferOrder {
    public final Transfer transfer;
    public final Signature signature;

    public TransferOrder(Transfer transfer, Signature signature) {
        java.util.Objects.requireNonNull(transfer, "transfer must not be null");
        java.util.Objects.requireNonNull(signature, "signature must not be null");
        this.transfer = transfer;
        this.signature = signature;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        transfer.serialize(serializer);
        signature.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static TransferOrder deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.transfer = Transfer.deserialize(deserializer);
        builder.signature = Signature.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static TransferOrder bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        TransferOrder value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        TransferOrder other = (TransferOrder) obj;
        if (!java.util.Objects.equals(this.transfer, other.transfer)) { return false; }
        if (!java.util.Objects.equals(this.signature, other.signature)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.transfer != null ? this.transfer.hashCode() : 0);
        value = 31 * value + (this.signature != null ? this.signature.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public Transfer transfer;
        public Signature signature;

        public TransferOrder build() {
            return new TransferOrder(
                transfer,
                signature
            );
        }
    }
}
