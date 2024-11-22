# Use official Flutter image
FROM dart:stable

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

# Get dependencies
RUN flutter pub get

# Build for web
RUN flutter build web

# Expose the Flutter development server port
EXPOSE 8077

# Run Flutter's development server
CMD ["flutter", "run", "-d", "web-server", "--web-port", "8077", "--web-hostname", "localhost"]