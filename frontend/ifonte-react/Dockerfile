FROM node:19-alpine
WORKDIR /app
COPY package*.json .
RUN yarn install --silent
COPY . .

RUN echo "VITE_API_BASE_URL=http://localhost:8080" > .env
EXPOSE 5173
CMD ["yarn", "dev"]
