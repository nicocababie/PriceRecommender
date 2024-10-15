# Etapa de construcción
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm install typescript -g
RUN npm run build

# Etapa final
FROM node:18-alpine
WORKDIR /app
COPY --from=builder /app/dist ./dist
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/.env ./
EXPOSE 3000

COPY ./start.sh ./
RUN chmod +x start.sh

EXPOSE 3000

# Comando para iniciar la aplicación
CMD ["sh", "start.sh"]
