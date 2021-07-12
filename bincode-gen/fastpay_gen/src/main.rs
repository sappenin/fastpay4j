use serde_reflection::*;
use serde_yaml;
use std::fs::File;
use std::io::Write;
use fastpay_core::{messages::*};

fn main() {
    println!("Generating Fastpay Serde IDL");

    // Start the tracing session.
    let mut tracer = Tracer::new(TracerConfig::default());

// Trace the desired top-level type(s).
    tracer.trace_simple_type::<FundingTransaction>().unwrap();
    tracer.trace_simple_type::<PrimarySynchronizationOrder>().unwrap();
    tracer.trace_simple_type::<Address>().unwrap();
    tracer.trace_simple_type::<Transfer>().unwrap();
    tracer.trace_simple_type::<TransferOrder>().unwrap();
    tracer.trace_simple_type::<SignedTransferOrder>().unwrap();
    tracer.trace_simple_type::<CertifiedTransferOrder>().unwrap();
    tracer.trace_simple_type::<RedeemTransaction>().unwrap();
    tracer.trace_simple_type::<ConfirmationOrder>().unwrap();
    tracer.trace_simple_type::<AccountInfoRequest>().unwrap();
    tracer.trace_simple_type::<AccountInfoResponse>().unwrap();
    tracer.trace_simple_type::<CrossShardUpdate>().unwrap();

// Also trace each enum type separately to fix any `MissingVariants` error.
//     tracer.trace_simple_type::<Choice>()?;

// Obtain the registry of Serde formats and serialize it in YAML (for instance).
    let registry = tracer.registry();
    let data = serde_yaml::to_string(&registry.unwrap()).unwrap();

    let mut file = File::create("fastpay_types.yaml").unwrap();
    // write_all!(&mut file, data.).unwrap();
    let _ = file.write_all(data.as_bytes());
}
