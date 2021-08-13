package com.sappenin.fastpay.bincode;


public final class RedeemTransaction {
    public final CertifiedTransferOrder transfer_certificate;

    public RedeemTransaction(CertifiedTransferOrder transfer_certificate) {
        java.util.Objects.requireNonNull(transfer_certificate, "transfer_certificate must not be null");
        this.transfer_certificate = transfer_certificate;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        transfer_certificate.serialize(serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static RedeemTransaction deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.transfer_certificate = CertifiedTransferOrder.deserialize(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static RedeemTransaction bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        RedeemTransaction value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        RedeemTransaction other = (RedeemTransaction) obj;
        if (!java.util.Objects.equals(this.transfer_certificate, other.transfer_certificate)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.transfer_certificate != null ? this.transfer_certificate.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public CertifiedTransferOrder transfer_certificate;

        public RedeemTransaction build() {
            return new RedeemTransaction(
                transfer_certificate
            );
        }
    }
}
