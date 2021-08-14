package com.sappenin.fastpay.core.bincode;


public abstract class FastPayError {

    abstract public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError;

    public static FastPayError deserialize(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
        int index = deserializer.deserialize_variant_index();
        switch (index) {
            case 0: return InvalidSignature.load(deserializer);
            case 1: return UnknownSigner.load(deserializer);
            case 2: return CertificateRequiresQuorum.load(deserializer);
            case 3: return IncorrectTransferAmount.load(deserializer);
            case 4: return UnexpectedSequenceNumber.load(deserializer);
            case 5: return InsufficientFunding.load(deserializer);
            case 6: return PreviousTransferMustBeConfirmedFirst.load(deserializer);
            case 7: return ErrorWhileProcessingTransferOrder.load(deserializer);
            case 8: return ErrorWhileRequestingCertificate.load(deserializer);
            case 9: return MissingEalierConfirmations.load(deserializer);
            case 10: return UnexpectedTransactionIndex.load(deserializer);
            case 11: return CertificateNotfound.load(deserializer);
            case 12: return UnknownSenderAccount.load(deserializer);
            case 13: return CertificateAuthorityReuse.load(deserializer);
            case 14: return InvalidSequenceNumber.load(deserializer);
            case 15: return SequenceOverflow.load(deserializer);
            case 16: return SequenceUnderflow.load(deserializer);
            case 17: return AmountOverflow.load(deserializer);
            case 18: return AmountUnderflow.load(deserializer);
            case 19: return BalanceOverflow.load(deserializer);
            case 20: return BalanceUnderflow.load(deserializer);
            case 21: return WrongShard.load(deserializer);
            case 22: return InvalidCrossShardUpdate.load(deserializer);
            case 23: return InvalidDecoding.load(deserializer);
            case 24: return UnexpectedMessage.load(deserializer);
            case 25: return ClientIoError.load(deserializer);
            default: throw new com.novi.serde.DeserializationError("Unknown variant index for FastPayError: " + index);
        }
    }

    public byte[] bincodeSerialize() throws com.novi.serde.SerializationError {
        com.novi.serde.Serializer serializer = new com.novi.bincode.BincodeSerializer();
        serialize(serializer);
        return serializer.get_bytes();
    }

    public static FastPayError bincodeDeserialize(byte[] input) throws com.novi.serde.DeserializationError {
        if (input == null) {
             throw new com.novi.serde.DeserializationError("Cannot deserialize null array");
        }
        com.novi.serde.Deserializer deserializer = new com.novi.bincode.BincodeDeserializer(input);
        FastPayError value = deserialize(deserializer);
        if (deserializer.get_buffer_offset() < input.length) {
             throw new com.novi.serde.DeserializationError("Some input bytes were not read");
        }
        return value;
    }

    public static final class InvalidSignature extends FastPayError {
        public final String error;

        public InvalidSignature(String error) {
            java.util.Objects.requireNonNull(error, "error must not be null");
            this.error = error;
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(0);
            serializer.serialize_str(error);
            serializer.decrease_container_depth();
        }

        static InvalidSignature load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.error = deserializer.deserialize_str();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            InvalidSignature other = (InvalidSignature) obj;
            if (!java.util.Objects.equals(this.error, other.error)) { return false; }
            return true;
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + (this.error != null ? this.error.hashCode() : 0);
            return value;
        }

        public static final class Builder {
            public String error;

            public InvalidSignature build() {
                return new InvalidSignature(
                    error
                );
            }
        }
    }

    public static final class UnknownSigner extends FastPayError {
        public UnknownSigner() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(1);
            serializer.decrease_container_depth();
        }

        static UnknownSigner load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            UnknownSigner other = (UnknownSigner) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public UnknownSigner build() {
                return new UnknownSigner(
                );
            }
        }
    }

    public static final class CertificateRequiresQuorum extends FastPayError {
        public CertificateRequiresQuorum() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(2);
            serializer.decrease_container_depth();
        }

        static CertificateRequiresQuorum load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            CertificateRequiresQuorum other = (CertificateRequiresQuorum) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public CertificateRequiresQuorum build() {
                return new CertificateRequiresQuorum(
                );
            }
        }
    }

    public static final class IncorrectTransferAmount extends FastPayError {
        public IncorrectTransferAmount() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(3);
            serializer.decrease_container_depth();
        }

        static IncorrectTransferAmount load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            IncorrectTransferAmount other = (IncorrectTransferAmount) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public IncorrectTransferAmount build() {
                return new IncorrectTransferAmount(
                );
            }
        }
    }

    public static final class UnexpectedSequenceNumber extends FastPayError {
        public UnexpectedSequenceNumber() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(4);
            serializer.decrease_container_depth();
        }

        static UnexpectedSequenceNumber load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            UnexpectedSequenceNumber other = (UnexpectedSequenceNumber) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public UnexpectedSequenceNumber build() {
                return new UnexpectedSequenceNumber(
                );
            }
        }
    }

    public static final class InsufficientFunding extends FastPayError {
        public final Balance current_balance;

        public InsufficientFunding(Balance current_balance) {
            java.util.Objects.requireNonNull(current_balance, "current_balance must not be null");
            this.current_balance = current_balance;
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(5);
            current_balance.serialize(serializer);
            serializer.decrease_container_depth();
        }

        static InsufficientFunding load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.current_balance = Balance.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            InsufficientFunding other = (InsufficientFunding) obj;
            if (!java.util.Objects.equals(this.current_balance, other.current_balance)) { return false; }
            return true;
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + (this.current_balance != null ? this.current_balance.hashCode() : 0);
            return value;
        }

        public static final class Builder {
            public Balance current_balance;

            public InsufficientFunding build() {
                return new InsufficientFunding(
                    current_balance
                );
            }
        }
    }

    public static final class PreviousTransferMustBeConfirmedFirst extends FastPayError {
        public final TransferOrder pending_confirmation;

        public PreviousTransferMustBeConfirmedFirst(TransferOrder pending_confirmation) {
            java.util.Objects.requireNonNull(pending_confirmation, "pending_confirmation must not be null");
            this.pending_confirmation = pending_confirmation;
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(6);
            pending_confirmation.serialize(serializer);
            serializer.decrease_container_depth();
        }

        static PreviousTransferMustBeConfirmedFirst load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.pending_confirmation = TransferOrder.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            PreviousTransferMustBeConfirmedFirst other = (PreviousTransferMustBeConfirmedFirst) obj;
            if (!java.util.Objects.equals(this.pending_confirmation, other.pending_confirmation)) { return false; }
            return true;
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + (this.pending_confirmation != null ? this.pending_confirmation.hashCode() : 0);
            return value;
        }

        public static final class Builder {
            public TransferOrder pending_confirmation;

            public PreviousTransferMustBeConfirmedFirst build() {
                return new PreviousTransferMustBeConfirmedFirst(
                    pending_confirmation
                );
            }
        }
    }

    public static final class ErrorWhileProcessingTransferOrder extends FastPayError {
        public ErrorWhileProcessingTransferOrder() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(7);
            serializer.decrease_container_depth();
        }

        static ErrorWhileProcessingTransferOrder load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            ErrorWhileProcessingTransferOrder other = (ErrorWhileProcessingTransferOrder) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public ErrorWhileProcessingTransferOrder build() {
                return new ErrorWhileProcessingTransferOrder(
                );
            }
        }
    }

    public static final class ErrorWhileRequestingCertificate extends FastPayError {
        public ErrorWhileRequestingCertificate() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(8);
            serializer.decrease_container_depth();
        }

        static ErrorWhileRequestingCertificate load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            ErrorWhileRequestingCertificate other = (ErrorWhileRequestingCertificate) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public ErrorWhileRequestingCertificate build() {
                return new ErrorWhileRequestingCertificate(
                );
            }
        }
    }

    public static final class MissingEalierConfirmations extends FastPayError {
        public final SequenceNumber current_sequence_number;

        public MissingEalierConfirmations(SequenceNumber current_sequence_number) {
            java.util.Objects.requireNonNull(current_sequence_number, "current_sequence_number must not be null");
            this.current_sequence_number = current_sequence_number;
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(9);
            current_sequence_number.serialize(serializer);
            serializer.decrease_container_depth();
        }

        static MissingEalierConfirmations load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.current_sequence_number = SequenceNumber.deserialize(deserializer);
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            MissingEalierConfirmations other = (MissingEalierConfirmations) obj;
            if (!java.util.Objects.equals(this.current_sequence_number, other.current_sequence_number)) { return false; }
            return true;
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + (this.current_sequence_number != null ? this.current_sequence_number.hashCode() : 0);
            return value;
        }

        public static final class Builder {
            public SequenceNumber current_sequence_number;

            public MissingEalierConfirmations build() {
                return new MissingEalierConfirmations(
                    current_sequence_number
                );
            }
        }
    }

    public static final class UnexpectedTransactionIndex extends FastPayError {
        public UnexpectedTransactionIndex() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(10);
            serializer.decrease_container_depth();
        }

        static UnexpectedTransactionIndex load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            UnexpectedTransactionIndex other = (UnexpectedTransactionIndex) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public UnexpectedTransactionIndex build() {
                return new UnexpectedTransactionIndex(
                );
            }
        }
    }

    public static final class CertificateNotfound extends FastPayError {
        public CertificateNotfound() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(11);
            serializer.decrease_container_depth();
        }

        static CertificateNotfound load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            CertificateNotfound other = (CertificateNotfound) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public CertificateNotfound build() {
                return new CertificateNotfound(
                );
            }
        }
    }

    public static final class UnknownSenderAccount extends FastPayError {
        public UnknownSenderAccount() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(12);
            serializer.decrease_container_depth();
        }

        static UnknownSenderAccount load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            UnknownSenderAccount other = (UnknownSenderAccount) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public UnknownSenderAccount build() {
                return new UnknownSenderAccount(
                );
            }
        }
    }

    public static final class CertificateAuthorityReuse extends FastPayError {
        public CertificateAuthorityReuse() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(13);
            serializer.decrease_container_depth();
        }

        static CertificateAuthorityReuse load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            CertificateAuthorityReuse other = (CertificateAuthorityReuse) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public CertificateAuthorityReuse build() {
                return new CertificateAuthorityReuse(
                );
            }
        }
    }

    public static final class InvalidSequenceNumber extends FastPayError {
        public InvalidSequenceNumber() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(14);
            serializer.decrease_container_depth();
        }

        static InvalidSequenceNumber load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            InvalidSequenceNumber other = (InvalidSequenceNumber) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public InvalidSequenceNumber build() {
                return new InvalidSequenceNumber(
                );
            }
        }
    }

    public static final class SequenceOverflow extends FastPayError {
        public SequenceOverflow() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(15);
            serializer.decrease_container_depth();
        }

        static SequenceOverflow load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            SequenceOverflow other = (SequenceOverflow) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public SequenceOverflow build() {
                return new SequenceOverflow(
                );
            }
        }
    }

    public static final class SequenceUnderflow extends FastPayError {
        public SequenceUnderflow() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(16);
            serializer.decrease_container_depth();
        }

        static SequenceUnderflow load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            SequenceUnderflow other = (SequenceUnderflow) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public SequenceUnderflow build() {
                return new SequenceUnderflow(
                );
            }
        }
    }

    public static final class AmountOverflow extends FastPayError {
        public AmountOverflow() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(17);
            serializer.decrease_container_depth();
        }

        static AmountOverflow load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            AmountOverflow other = (AmountOverflow) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public AmountOverflow build() {
                return new AmountOverflow(
                );
            }
        }
    }

    public static final class AmountUnderflow extends FastPayError {
        public AmountUnderflow() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(18);
            serializer.decrease_container_depth();
        }

        static AmountUnderflow load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            AmountUnderflow other = (AmountUnderflow) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public AmountUnderflow build() {
                return new AmountUnderflow(
                );
            }
        }
    }

    public static final class BalanceOverflow extends FastPayError {
        public BalanceOverflow() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(19);
            serializer.decrease_container_depth();
        }

        static BalanceOverflow load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            BalanceOverflow other = (BalanceOverflow) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public BalanceOverflow build() {
                return new BalanceOverflow(
                );
            }
        }
    }

    public static final class BalanceUnderflow extends FastPayError {
        public BalanceUnderflow() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(20);
            serializer.decrease_container_depth();
        }

        static BalanceUnderflow load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            BalanceUnderflow other = (BalanceUnderflow) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public BalanceUnderflow build() {
                return new BalanceUnderflow(
                );
            }
        }
    }

    public static final class WrongShard extends FastPayError {
        public WrongShard() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(21);
            serializer.decrease_container_depth();
        }

        static WrongShard load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            WrongShard other = (WrongShard) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public WrongShard build() {
                return new WrongShard(
                );
            }
        }
    }

    public static final class InvalidCrossShardUpdate extends FastPayError {
        public InvalidCrossShardUpdate() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(22);
            serializer.decrease_container_depth();
        }

        static InvalidCrossShardUpdate load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            InvalidCrossShardUpdate other = (InvalidCrossShardUpdate) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public InvalidCrossShardUpdate build() {
                return new InvalidCrossShardUpdate(
                );
            }
        }
    }

    public static final class InvalidDecoding extends FastPayError {
        public InvalidDecoding() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(23);
            serializer.decrease_container_depth();
        }

        static InvalidDecoding load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            InvalidDecoding other = (InvalidDecoding) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public InvalidDecoding build() {
                return new InvalidDecoding(
                );
            }
        }
    }

    public static final class UnexpectedMessage extends FastPayError {
        public UnexpectedMessage() {
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(24);
            serializer.decrease_container_depth();
        }

        static UnexpectedMessage load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            UnexpectedMessage other = (UnexpectedMessage) obj;
            return true;
        }

        public int hashCode() {
            int value = 7;
            return value;
        }

        public static final class Builder {
            public UnexpectedMessage build() {
                return new UnexpectedMessage(
                );
            }
        }
    }

    public static final class ClientIoError extends FastPayError {
        public final String error;

        public ClientIoError(String error) {
            java.util.Objects.requireNonNull(error, "error must not be null");
            this.error = error;
        }

        public void serialize(com.novi.serde.Serializer serializer) throws com.novi.serde.SerializationError {
            serializer.increase_container_depth();
            serializer.serialize_variant_index(25);
            serializer.serialize_str(error);
            serializer.decrease_container_depth();
        }

        static ClientIoError load(com.novi.serde.Deserializer deserializer) throws com.novi.serde.DeserializationError {
            deserializer.increase_container_depth();
            Builder builder = new Builder();
            builder.error = deserializer.deserialize_str();
            deserializer.decrease_container_depth();
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            ClientIoError other = (ClientIoError) obj;
            if (!java.util.Objects.equals(this.error, other.error)) { return false; }
            return true;
        }

        public int hashCode() {
            int value = 7;
            value = 31 * value + (this.error != null ? this.error.hashCode() : 0);
            return value;
        }

        public static final class Builder {
            public String error;

            public ClientIoError build() {
                return new ClientIoError(
                    error
                );
            }
        }
    }
}

