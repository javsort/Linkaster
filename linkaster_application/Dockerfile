
#
#  Title: linkaster_application/Dockerfile -> flutter container
#  Author: Ortega Mendoza, Javier
#  Date: 2024
#  Code Version: 1.0
#  Availability: https://github.com/javsort/Linkaster
#

# Use official Flutter image
FROM dart:stable AS build

# Allow Flutter to run as root
ENV FLUTTER_ALLOW_ROOT="true"

# Install necessary dependencies
RUN apt-get update && apt-get install -y \
    git \
    wget \
    unzip \
    curl \
    xz-utils \
    libglu1-mesa \
    && rm -rf /var/lib/apt/lists/*

# Clone Flutter SDK
RUN git clone https://github.com/flutter/flutter.git /flutter
ENV PATH="/flutter/bin:${PATH}"

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Enable web support
RUN flutter doctor -v && flutter config --enable-web

# Clean the project (ensure a clean build)
RUN flutter clean

# Get dependencies
RUN flutter pub get

# Upgrade dependencies to the latest compatible versions
RUN flutter pub upgrade

# Build for web
RUN flutter build web --release

# Build on nginx image
FROM nginx:stable-alpine

# Copy the build output to serve
COPY --from=build /app/build/web /usr/share/nginx/html

# Expose the Flutter development server port
EXPOSE 8077

# Run Flutter's development server
CMD ["nginx", "-g", "daemon off;"]