#!/bin/bash

# Set the Maven command
MVN_COMMAND="./mvnw"

if [ -z "$1" ]; then
    echo "Usage: ./run.sh [command]"
    echo "Use this script for [command] the project!"
    echo
    echo "Available commands:"
    echo "    build      - Clean install and build the project"
    echo "    buildWlogs - Clean install and build with full logging"
    echo "    package    - Package the project"
    echo "    validate   - Validate the project"
    echo "    test       - Run all tests"
    echo "    clean      - Clean the project"
    echo "    help       - Show usage"
    echo
    exit 1
fi

case "$1" in
    build)
        cd ../../linkaster-services || exit 1
        $MVN_COMMAND clean install
        ;;
    buildWlogs)
        cd ../../linkaster-services || exit 1
        $MVN_COMMAND clean install -X
        ;;
    package)
        cd ../../linkaster-services || exit 1
        $MVN_COMMAND clean package
        ;;
    validate)
        cd ../../linkaster-services || exit 1
        $MVN_COMMAND validate
        ;;
    test)
        cd ../../linkaster-services || exit 1
        $MVN_COMMAND test
        ;;
    clean)
        cd ../../linkaster-services || exit 1
        $MVN_COMMAND clean
        ;;
    help)
        echo "Usage: ./run.sh [command]"
        echo "Use this script for [command] the project!"
        echo
        echo "Available commands:"
        echo "    build      - Clean install and build the project"
        echo "    buildWlogs - Clean install and build with full logging"
        echo "    package    - Package the project"
        echo "    validate   - Validate the project"
        echo "    test       - Run all tests"
        echo "    clean      - Clean the project"
        echo "    help       - Show usage"
        echo
        ;;
    *)
        echo "Invalid command"
        echo "Type ./run.sh help for usage."
        ;;
esac
