## This is Micronaut app with configurable Latency for Benchmark Testing

### Followed below guide for GraalVM configuration
[Using GraalVM] (https://guides.micronaut.io/creating-your-first-micronaut-app/guide/index.html)

### Generating Micronaut App using SDKMAN
* sdk install micronaut
* mn create-app com.perf.benchmark.micronaut-benchmark-app

### Running the application locally

* ./gradlew run
* curl http://localhost:8080/latency?delay=1000

### Generating Native Image (GraalVM already installed)
* ./gradlew nativeImage
* Run the app: ./build/native-image/application

### Overall Comments while generating
* Generating Native Image super easy (mn utility takes care of all)
* Native Image Size: 54MB (during generation CPU usage is high)

### Startup Time with Native image
* 10 Runs: 21ms, 25ms, 22ms, 22ms, 24ms, 22ms, 24ms, 22ms, 24ms, 27ms
