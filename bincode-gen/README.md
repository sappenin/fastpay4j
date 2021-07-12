The Rust implementation of Fastpay uses [Bincode](https://github.com/bincode-org/bincode) as a serialization format for
RPC. This format is not natively supported by Java, but [serde-reflection](https://github.com/novifinancial/serde-reflection)
provides a reflection tool (to generate Bincode description files in yaml) and
a generator tool (to generate (de)serialization files) called `serdegen`.

Execute the following commands to generate Fastpay Bincode files in Java:

```shell
# From the same directory as this README
cargo build
```

Generate the YAML file:

```shell
cargo run --package fastpay_gen --bin fp-gen
```

Install serde-generate

```shell
cargo install serde-generate
```

Generate the Java files:

```shell
serdegen \
  --language java \
  --with-runtimes serde bincode \
  --module-name money.fluid.fastpay.bincode \
  --target-source-dir "./generated-java" \
  ./fastpay_types.yaml > ./fastpay_types.java
```


