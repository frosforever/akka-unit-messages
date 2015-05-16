# akka-unit-messages

Run tests with `sbt test`

Project showing that sending `Unit` as a message to actors will succeed if on the same VM where serialization is not required, but will fail if sent to another VM. `()` will work both on the same VM and between VMs. This is because `()`, the value, is serializable, while `Unit`, the type, is not.
