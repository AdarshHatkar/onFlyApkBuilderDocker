#!/bin/bash

# Set noninteractive mode for Debian frontend
export DEBIAN_FRONTEND=noninteractive

# Update package repository
apt-get update

# Install curl
apt-get install -y curl

# Install Node.js
curl -sL https://deb.nodesource.com/setup_20.x | bash -
apt-get upgrade -y
apt-get install -y nodejs

# Install Git
apt-get install -y git

# Support multiarch and install essential tools
JDK_VERSION=17
GRADLE_VERSION=8.3
KOTLIN_VERSION=1.9.10
ANDROID_SDK_VERSION=10406996

dpkg --add-architecture i386 
apt-get update 
apt-get dist-upgrade -y 
apt-get install -y --no-install-recommends libncurses5:i386 libc6:i386 libstdc++6:i386 lib32gcc-s1 lib32ncurses6 lib32z1 zlib1g:i386 
apt-get install -y --no-install-recommends openjdk-${JDK_VERSION}-jdk 
apt-get install -y --no-install-recommends git wget unzip 
apt-get clean && rm -rf /var/lib/apt/lists/*

# Download and install Gradle
cd /opt && \
    wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle*.zip && \
    mv gradle-${GRADLE_VERSION} gradle && \
    rm gradle*.zip

# Download and install Kotlin compiler
cd /opt && \
    wget -q https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip && \
    unzip *kotlin*.zip && \
    rm *kotlin*.zip

# Download and install Android SDK
ANDROID_HOME=/opt/android-sdk
mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip && \
    unzip *tools*linux*.zip -d ${ANDROID_HOME}/cmdline-tools && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/tools && \
    rm *tools*linux*.zip

# Set environment variables
JAVA_HOME=/usr/lib/jvm/java-${JDK_VERSION}-openjdk-amd64
GRADLE_HOME=/opt/gradle
KOTLIN_HOME=/opt/kotlinc
export PATH=${PATH}:${GRADLE_HOME}/bin:${KOTLIN_HOME}/bin:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/cmdline-tools/tools/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/emulator
export LD_LIBRARY_PATH=${ANDROID_HOME}/emulator/lib64:${ANDROID_HOME}/emulator/lib64/qt/lib
export QTWEBENGINE_DISABLE_SANDBOX=1

# Accept the license agreements of the SDK components
/opt/license_accepter.sh $ANDROID_HOME

# Set working directory


# Copy files
# chmod +x main.sh
# chmod +x /app/gaming_app_apk_v106/buildAbb.sh
# chmod +x /app/gaming_app_apk_v106/buildApk.sh
export BUILDER_ENVIRONMENT=production
