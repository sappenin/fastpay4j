package com.sappenin.fastpay.bincode;


public final class UserData {
    public final java.util.Optional<java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte>> value;

    public UserData(java.util.Optional<java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte>> value) {
        java.util.Objects.requireNonNull(value, "value must not be null");
        this.value = value;
    }

    public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.increase_container_depth();
        TraitHelpers.serialize_option_array32_u8_array(value, serializer);
        serializer.decrease_container_depth();
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static UserData deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        deserializer.increase_container_depth();
        Builder builder = new Builder();
        builder.value = TraitHelpers.deserialize_option_array32_u8_array(deserializer);
        deserializer.decrease_container_depth();
        return builder.build();
    }

    public static UserData bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        UserData value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UserData other = (UserData) obj;
        if (!java.util.Objects.equals(this.value, other.value)) { return false; }
        return true;
    }

    public int hashCode() {
        int value = 7;
        value = 31 * value + (this.value != null ? this.value.hashCode() : 0);
        return value;
    }

    public static final class Builder {
        public java.util.Optional<java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte>> value;

        public UserData build() {
            return new UserData(
                value
            );
        }
    }
}
