# Kotlin Multiplatform Implementation of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

This projects outlines a sample architecture and usage of kotlin MPP for code sharing (via MPP architecture).
## Implemented Platforms
* JS/WASM (via shared browser UI library, requires local installation of [KVDOM](https://gitlab.com/lt.petuska/kvdom/tree/feature/rework))
* JS (with React)
* JVM (with TornadoFX)
* Native/Desktop for Linux & Windows (with GTK3)

##Running
### Browser
* JS (React): No extra setup required, just run `./gradlew reactBrowserRun`
* JS(KVDOM): Install KVDOM library to local maven cache and run `./gradlew jsBrowserRun`
* WASM (KVDOM): Install KVDOM library to local maven cache, install `http-server` globally via npm and run `./gradlew wasmBrowserRun`

### JVM (TornadoFX)
Make sure you have JDK8 installed as your default JDK and run `./gradlew jvmRun`

### Desktop (GTK3)
* Linux: Install GTK3 toolchain from [here](https://www.gtk.org/download/linux.php) and `pkg-config` package and run `./gradlew runReleaseExecutableDesktop`
* Windows: Install GTK3 toolchain from [here](https://www.gtk.org/download/windows.php) via [MSYS2](https://www.msys2.org/) and `pkg-config` package and run `./gradlew runReleaseExecutableDesktop`
