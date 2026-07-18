# 🛑 STOP! This is NOT the Dockerfile you are looking for.
# This is a monorepo root. You should NOT build from this root unless you want this error message.
#
# To build individual services, please use their respective Dockerfiles:
#
# Backend:
#   docker build -t backend-movie -f backend-movie/Dockerfile ./backend-movie
#
# Frontend:
#   docker build -t frontend-movie -f frontend-movie/Dockerfile ./frontend-movie
#
# Alternatively, use Docker Compose:
#   docker-compose up --build

FROM alpine
RUN echo "ERROR: You are trying to build from the root directory. Please build from backend-movie/ or frontend-movie/ subdirectories." && exit 1
