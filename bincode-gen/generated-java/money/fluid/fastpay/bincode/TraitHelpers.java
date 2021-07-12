package money.fluid.fastpay.bincode;

final class TraitHelpers {
    static void serialize_array32_u8_array(java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        if (value.size() != 32) {
            throw new java.lang.IllegalArgumentException("Invalid length for fixed-size array: " + value.size() + " instead of "+ 32);
        }
        for (@com.novi.serde.Unsigned Byte item : value) {
            serializer.serialize_u8(item);
        }
    }

    static java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte> deserialize_array32_u8_array(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        java.util.List<@com.novi.serde.Unsigned Byte> obj = new java.util.ArrayList<@com.novi.serde.Unsigned Byte>(32);
        for (long i = 0; i < 32; i++) {
            obj.add(deserializer.deserialize_u8());
        }
        return obj;
    }

    static void serialize_option_CertifiedTransferOrder(java.util.Optional<CertifiedTransferOrder> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            value.get().serialize(serializer);
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<CertifiedTransferOrder> deserialize_option_CertifiedTransferOrder(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(CertifiedTransferOrder.deserialize(deserializer));
        }
    }

    static void serialize_option_SequenceNumber(java.util.Optional<SequenceNumber> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            value.get().serialize(serializer);
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<SequenceNumber> deserialize_option_SequenceNumber(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(SequenceNumber.deserialize(deserializer));
        }
    }

    static void serialize_option_SignedTransferOrder(java.util.Optional<SignedTransferOrder> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            value.get().serialize(serializer);
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<SignedTransferOrder> deserialize_option_SignedTransferOrder(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(SignedTransferOrder.deserialize(deserializer));
        }
    }

    static void serialize_option_array32_u8_array(java.util.Optional<java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte>> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            TraitHelpers.serialize_array32_u8_array(value.get(), serializer);
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<java.util.@com.novi.serde.ArrayLen(length=32) List<@com.novi.serde.Unsigned Byte>> deserialize_option_array32_u8_array(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(TraitHelpers.deserialize_array32_u8_array(deserializer));
        }
    }

    static void serialize_option_u64(java.util.Optional<@com.novi.serde.Unsigned Long> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        if (value.isPresent()) {
            serializer.serialize_option_tag(true);
            serializer.serialize_u64(value.get());
        } else {
            serializer.serialize_option_tag(false);
        }
    }

    static java.util.Optional<@com.novi.serde.Unsigned Long> deserialize_option_u64(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        boolean tag = deserializer.deserialize_option_tag();
        if (!tag) {
            return java.util.Optional.empty();
        } else {
            return java.util.Optional.of(deserializer.deserialize_u64());
        }
    }

    static void serialize_tuple2_EdPublicKeyBytes_Signature(com.novi.serde.Tuple2<EdPublicKeyBytes, Signature> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        value.field0.serialize(serializer);
        value.field1.serialize(serializer);
    }

    static com.novi.serde.Tuple2<EdPublicKeyBytes, Signature> deserialize_tuple2_EdPublicKeyBytes_Signature(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        return new com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>(
            EdPublicKeyBytes.deserialize(deserializer),
            Signature.deserialize(deserializer)
        );
    }

    static void serialize_vector_CertifiedTransferOrder(java.util.List<CertifiedTransferOrder> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.serialize_len(value.size());
        for (CertifiedTransferOrder item : value) {
            item.serialize(serializer);
        }
    }

    static java.util.List<CertifiedTransferOrder> deserialize_vector_CertifiedTransferOrder(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<CertifiedTransferOrder> obj = new java.util.ArrayList<CertifiedTransferOrder>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(CertifiedTransferOrder.deserialize(deserializer));
        }
        return obj;
    }

    static void serialize_vector_tuple2_EdPublicKeyBytes_Signature(java.util.List<com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>> value, com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
        serializer.serialize_len(value.size());
        for (com.novi.serde.Tuple2<EdPublicKeyBytes, Signature> item : value) {
            TraitHelpers.serialize_tuple2_EdPublicKeyBytes_Signature(item, serializer);
        }
    }

    static java.util.List<com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>> deserialize_vector_tuple2_EdPublicKeyBytes_Signature(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        long length = deserializer.deserialize_len();
        java.util.List<com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>> obj = new java.util.ArrayList<com.novi.serde.Tuple2<EdPublicKeyBytes, Signature>>((int) length);
        for (long i = 0; i < length; i++) {
            obj.add(TraitHelpers.deserialize_tuple2_EdPublicKeyBytes_Signature(deserializer));
        }
        return obj;
    }

}

