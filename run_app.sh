#!/bin/sh

echo "Please enter run, down or cancel"
read user_input

if [ "$user_input" = "run" ]; then
    echo "Running the app"

    echo "Backend starting..."
    cd ./backend
    docker compose up -d

elif [ "$user_input" = "down" ]; then
    echo "Shutting down the app"

    echo "Backend off..."
    cd ./backend
    docker compose down

    echo "Removing images..."
    docker rmi backend-app
else
    echo "Cancelled"
fi
