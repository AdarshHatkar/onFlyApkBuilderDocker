FROM ubuntu:focal
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update
RUN apt-get install -y curl
RUN curl -sL https://deb.nodesource.com/setup_20.x | bash -
RUN apt-get upgrade -y
RUN apt-get install -y nodejs

RUN apt-get install git -y

# support multiarch: i386 architecture
# install Java
# install essential tools
ARG JDK_VERSION=17
RUN dpkg --add-architecture i386 
RUN apt-get update 
RUN apt-get dist-upgrade -y 
RUN apt-get install -y --no-install-recommends libncurses5:i386 libc6:i386 libstdc++6:i386 lib32gcc-s1 lib32ncurses6 lib32z1 zlib1g:i386 
RUN apt-get install -y --no-install-recommends openjdk-${JDK_VERSION}-jdk 
RUN apt-get install -y --no-install-recommends git wget unzip 
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# download and install Gradle
# https://services.gradle.org/distributions/
ARG GRADLE_VERSION=8.3
ARG GRADLE_DIST=bin
RUN cd /opt && \
    wget -q https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-${GRADLE_DIST}.zip && \
    unzip gradle*.zip && \
    ls -d */ | sed 's/\/*$//g' | xargs -I{} mv {} gradle && \
    rm gradle*.zip

# download and install Kotlin compiler
# https://github.com/JetBrains/kotlin/releases/latest
ARG KOTLIN_VERSION=1.9.10
RUN cd /opt && \
    wget -q https://github.com/JetBrains/kotlin/releases/download/v${KOTLIN_VERSION}/kotlin-compiler-${KOTLIN_VERSION}.zip && \
    unzip *kotlin*.zip && \
    rm *kotlin*.zip


# download and install Android SDK
# https://developer.android.com/studio#command-line-tools-only
ARG ANDROID_SDK_VERSION=10406996
ENV ANDROID_HOME /opt/android-sdk
RUN mkdir -p ${ANDROID_HOME}/cmdline-tools && \
    wget -q https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip && \
    unzip *tools*linux*.zip -d ${ANDROID_HOME}/cmdline-tools && \
    mv ${ANDROID_HOME}/cmdline-tools/cmdline-tools ${ANDROID_HOME}/cmdline-tools/tools && \
    rm *tools*linux*.zip


# set the environment variables
ENV JAVA_HOME /usr/lib/jvm/java-${JDK_VERSION}-openjdk-amd64
ENV GRADLE_HOME /opt/gradle
ENV KOTLIN_HOME /opt/kotlinc
ENV PATH ${PATH}:${GRADLE_HOME}/bin:${KOTLIN_HOME}/bin:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/cmdline-tools/tools/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/emulator
# WORKAROUND: for issue https://issuetracker.google.com/issues/37137213
ENV LD_LIBRARY_PATH ${ANDROID_HOME}/emulator/lib64:${ANDROID_HOME}/emulator/lib64/qt/lib
# patch emulator issue: Running as root without --no-sandbox is not supported. See https://crbug.com/638180.
# https://doc.qt.io/qt-5/qtwebengine-platform-notes.html#sandboxing-support
ENV QTWEBENGINE_DISABLE_SANDBOX 1

# accept the license agreements of the SDK components
ADD license_accepter.sh /opt/
RUN chmod +x /opt/license_accepter.sh && /opt/license_accepter.sh $ANDROID_HOME

WORKDIR /app

COPY . .

RUN chmod +x main.sh

RUN chmod +x /app/gaming_app_apk_v106/buildAbb.sh
RUN chmod +x /app/gaming_app_apk_v106/buildApk.sh
ENV BUILDER_ENVIRONMENT production 

ENTRYPOINT [ "/app/main.sh" ]

# RUN node --version

# RUN npm install -g typescript

# RUN npm i



# CMD npm run  run_app