FROM node:22-alpine

WORKDIR /app

# Install dependencies
COPY package.json package-lock.json ./
RUN npm ci

# Copy the rest of the frontend code
COPY . .

# Vite default dev port (keep in sync with docker-compose)
EXPOSE 5173

# Run Vite dev server, accessible from outside the container
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0", "--port", "5173"]