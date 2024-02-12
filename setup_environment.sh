#!/bin/bash

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    # Install Node.js
    curl -sL https://deb.nodesource.com/setup_20.x | bash -
    apt-get install -y nodejs
else
    echo "Node.js is already installed."
fi

# Check if Git is installed
if ! command -v git &> /dev/null; then
    # Install Git
    apt-get install -y --no-install-recommends git
else
    echo "Git is already installed."
fi

# Support multiarch and install essential tools
JDK_VERSION=17
GRADLE_VERSION=8.3
KOTLIN_VERSION=1.9.10
ANDROID_SDK_VERSION=10406996

# Check if Gradle is installed
if [ ! -d "/opt/gradle" ]; then
    # Download and install Gradle
    cd /opt && \
        wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
        unzip gradle*.zip && \
        mv gradle-${GRADLE_VERSION} gradle && \
        rm gradle*.zip
else
    echo "Gradle is already installed."
fi

# Check if Kotlin compiler is installed
if [ ! -d "/opt/kotlinc" ]; then
    # Download and install Kotlin compiler
    cd /opt && \
        wget -q https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip && \
        unzip *kotlin*.zip && \
        rm *kotlin*.zip
else
    echo "Kotlin compiler is already installed."
fi

# Check if Android SDK is installed
if [ ! -d "/opt/android-sdk" ]; then
    # Download and install Android SDK
    export ANDROID_HOME="/opt/android-sdk"
    mkdir -p ${ANDROID_HOME}/cmdline-tools && \
        wget -q https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip && \
        unzip *tools*linux*.zip -d ${ANDROID_HOME}/cmdline-tools && \
        mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/tools && \
        rm *tools*linux*.zip
else
    echo "Android SDK is already installed."
fi

# Check if license accepter script is already copied
if [ ! -f "/opt/license_accepter.sh" ]; then
    # Copy license accepter script
    cp /app/license_accepter.sh /opt/license_accepter.sh
    chmod +x /opt/license_accepter.sh
    /opt/license_accepter.sh $ANDROID_HOME
else
    echo "License accepter script is already copied."
fi

# Check if output directory exists
OUTPUT_DIR="/app/outputApks"
if [ ! -d "$OUTPUT_DIR" ]; then
    echo "Directory $OUTPUT_DIR does not exist. Creating..."
    mkdir -p "$OUTPUT_DIR"
    echo "Directory $OUTPUT_DIR created."
else
    echo "Directory $OUTPUT_DIR already exists."
fi

# Set environment variables
export JAVA_HOME="/usr/lib/jvm/java-${JDK_VERSION}-openjdk-amd64"
export GRADLE_HOME="/opt/gradle"
export KOTLIN_HOME="/opt/kotlinc"
export PATH=${PATH}:"${GRADLE_HOME}/bin:${KOTLIN_HOME}/bin":"${ANDROID_HOME}/cmdline-tools/latest/bin":"${ANDROID_HOME}/cmdline-tools/tools/bin":"${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/emulator"
export LD_LIBRARY_PATH="${ANDROID_HOME}/emulator/lib64:${ANDROID_HOME}/emulator/lib64/qt/lib"
export QTWEBENGINE_DISABLE_SANDBOX=1
export BUILDER_ENVIRONMENT="production"

# Change working directory
cd /app

# Run npm installation if not already installed
if [ ! -f "node_modules" ]; then
    npm install
else
    echo "npm packages are already installed."
fi

# Run npm script to run the app
npm run run_app
