## This is Helidon app with configurable Latency for Benchmark Testing

## Build and run

With JDK11+
```bash
mvn package
java -jar target/helidon-benchmark-se.jar
```

## Exercise the application

```
curl -X GET http://localhost:8080/latency?delay=100
{"message":"Hello World!"}

```

### Key Observations with GraalVM & Native Image
* Image Size: 22.4 MB
* Native image generation super easy using Maven
* ./target/helidon-benchmark-se

### Startup Time with Native image
* 10 Runs: 17ms, 17ms, 17ms, 18ms, 19ms, 17ms, 17ms,18ms,19ms,17ms
* set ulimit: ulimit -n 2048



## Try health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .

```

## Build the Docker Image

```
docker build -t helidon-benchmark-se .
```

## Start the application with Docker

```
docker run --rm -p 8080:8080 helidon-benchmark-se:latest
```

Exercise the application as described above

## Deploy the application to Kubernetes

```
kubectl cluster-info                        # Verify which cluster
kubectl get pods                            # Verify connectivity to cluster
kubectl create -f app.yaml                  # Deploy application
kubectl get service helidon-benchmark-se   # Get service info
```

## Build a native image with GraalVM

GraalVM allows you to compile your programs ahead-of-time into a native
 executable. See https://www.graalvm.org/docs/reference-manual/aot-compilation/
 for more information.

You can build a native executable in 2 different ways:
* With a local installation of GraalVM
* Using Docker

### Local build

Download Graal VM at https://www.graalvm.org/downloads, the versions
 currently supported for Helidon are `19.3` and `20.0`.

```
# Setup the environment
export GRAALVM_HOME=/path
# build the native executable
mvn package -Pnative-image
```

You can also put the Graal VM `bin` directory in your PATH, or pass
 `-DgraalVMHome=/path` to the Maven command.

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-native-image
 for more information.

Start the application:

```
./target/helidon-benchmark-se
```

### Multi-stage Docker build

Build the "native" Docker Image

```
docker build -t helidon-benchmark-se-native -f Dockerfile.native .
```

Start the application:

```
docker run --rm -p 8080:8080 helidon-benchmark-se-native:latest
```

## Build a Java Runtime Image using jlink

You can build a custom Java Runtime Image (JRI) containing the application jars and the JDK modules
on which they depend. This image also:

* Enables Class Data Sharing by default to reduce startup time.
* Contains a customized `start` script to simplify CDS usage and support debug and test modes.

You can build a custom JRI in two different ways:
* Local
* Using Docker


### Local build

```
# build the JRI
mvn package -Pjlink-image
```

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-jlink-image
 for more information.

Start the application:

```
./target/helidon-benchmark-se/bin/start
```

### Multi-stage Docker build

Build the "jlink" Docker Image

```
docker build -t helidon-benchmark-se-jlink -f Dockerfile.jlink .
```

Start the application:

```
docker run --rm -p 8080:8080 helidon-benchmark-se-jlink:latest
```

See the start script help:

```
docker run --rm helidon-benchmark-se-jlink:latest --help
```
