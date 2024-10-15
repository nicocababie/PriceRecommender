FROM node:18-alpine

# Instalar Git, AWS CLI y dependencias para descargar Terraform
RUN apk add --no-cache git aws-cli curl unzip

# Instalar Terraform
RUN curl -Lo /tmp/terraform.zip https://releases.hashicorp.com/terraform/1.6.0/terraform_1.6.0_linux_amd64.zip && \
    unzip /tmp/terraform.zip -d /usr/local/bin/ && \
    chmod +x /usr/local/bin/terraform && \
    rm /tmp/terraform.zip

# Crear el directorio para instalaciones globales y ajustar permisos
RUN mkdir -p /home/node/.npm-global && chown -R node:node /home/node/.npm-global

# Cambiar al usuario node
USER node

# Configurar npm para usar la nueva ruta global
RUN npm config set prefix /home/node/.npm-global

# Añadir el directorio de npm-global al PATH
ENV PATH=/home/node/.npm-global/bin:$PATH

# Copiar package.json y package-lock.json (si existe)
COPY --chown=node:node package*.json /workspaces/Obligatorio_273470_203656_267553/

# Instalar paquetes globales
RUN npm install -g typescript eslint --unsafe-perm
RUN npm install -g npm@10.8.3

# Verificar la instalación de Terraform
RUN terraform -version
