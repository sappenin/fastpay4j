package com.sappenin.fastpay.bincode;


public final class Signature {
    public final java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> part1;
    public final java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> part2;

    public Signature(java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> part1, java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> part2) {
        java.util.Objects.requireNonNull(part1, "part1 must not be null");
        java.util.Objects.requireNonNull(part2, "part2 must not be null");
        this.part1 = part1;
        this.part2 = part2;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        TraitHelpers.serialize_array32_u8_array(part1, serializer);
        TraitHelpers.serialize_array32_u8_array(part2, serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static Signature deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.part1 = TraitHelpers.deserialize_array32_u8_array(deserializer);
        builder.part2 = TraitHelpers.deserialize_array32_u8_array(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static Signature bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        Signature value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Signature other = (Signature) obj;
        if (!java.util.Objects.equals(this.part1, other.part1)) { return false; }
        if (!java.util.Objects.equals(this.part2, other.part2)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.part1 != null ? this.part1.hashCode() : 0);
        value = 31 * value + (this.part2 != null ? this.part2.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> part1;
        public java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> part2;

        public Signature build() {
            return new Signature(
                part1,
                part2
            );
        }
    }
}
