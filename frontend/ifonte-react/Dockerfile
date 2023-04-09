FROM node:19-alpine
ARG api_base_url
WORKDIR /app
COPY package*.json .
RUN yarn install --silent
COPY . .

RUN echo "VITE_API_BASE_URL=${api_base_url}" > .env
EXPOSE 5173
CMD ["yarn", "dev"]
