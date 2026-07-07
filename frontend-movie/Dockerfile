# 1. Build stage
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# This uses the ARG passed by Render to bake the URL into the frontend
ARG VITE_API_BASE_URL
ENV VITE_API_BASE_URL=$VITE_API_BASE_URL
RUN npm run build

# 2. Serve stage using a lightweight static server
FROM node:18-alpine
WORKDIR /app
RUN npm install -g serve
COPY --from=build /app/dist ./dist
# Render provides the $PORT variable automatically
CMD serve -s dist -l tcp://0.0.0.0:$PORT