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
RUN echo "
    http {
        server {
            listen 80 default_server;
            root /usr/share/nginx/html;
            index index.html index.htm;

            # Redirect all requests to the backend API URL
            location / {
                proxy_pass http://otms-backend-service:8080;
                proxy_set_header Host $host;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_http_version 1.1;
                proxy_buffering on;
                proxy_cache_bypass $http_upgrade;
                proxy_set_cache_key $scheme$host$request_uri;

                # Error handling
                error_page 404 /not-found.html;
                location /not-found.html {
                    root /usr/share/nginx/html;
                }
            }

            # Serve static assets directly from Nginx
            location ~ ^/(assets|static) {
                try_files $uri $uri/ /index.html;
            }
        }
    }
    " > /etc/nginx/conf.d/default.conf

# Expose port 80 to allow outside access to the app
EXPOSE 80

# Start NGINX to serve the React app
CMD ["nginx", "-g", "daemon off;"]
