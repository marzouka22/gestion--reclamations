# ====== ÉTAPE 1 : Build Angular ======
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# ====== ÉTAPE 2 : Serveur Nginx pour servir le frontend ======
FROM nginx:alpine
COPY --from=build /app/dist/reclamations-frontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
