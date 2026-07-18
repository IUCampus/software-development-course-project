# This is a monorepo root Dockerfile.
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
RUN echo "Please build from the service subdirectories (backend-movie/ or frontend-movie/) or use docker-compose up." && exit 1
