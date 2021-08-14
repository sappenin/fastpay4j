package com.sappenin.fastpay.core;

/**
 * A marker interface for all model objects in fp4j. This hierarchy includes only immutables-compatible objects, and is
 * distinct from any bincode or other generated encoding classes (this heirarchy exists so that fp4j code doesn't need
 * to change when encoding objects change).
 */
public interface FastpayModel {

}
