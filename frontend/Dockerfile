# Use an official Node.js runtime as the base image
FROM node:18.18.2 as build

# Set the working directory in the container
WORKDIR /app

# Remove any existing files in the working directory
RUN rm -rf *

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install --force

# Copy the entire project to the working directory
COPY . .

# Build the React app for production
RUN npm run build

# Use NGINX as the base image for serving the React app
FROM nginx:alpine

# Copy the built React app from the build stage to the NGINX html directory
COPY --from=build /app/build /usr/share/nginx/html

# Configure Nginx location block for proxy
# Copy the Nginx configuration file
COPY default.conf /etc/nginx/conf.d/default.conf

# Expose port 80 to allow outside access to the app
EXPOSE 80

# Start NGINX to serve the React app
CMD ["nginx", "-g", "daemon off;"]
