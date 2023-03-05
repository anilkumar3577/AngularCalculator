FROM node:16 as build
WORKDIR /node-app
COPY ./ /node-app
RUN npm install
RUN npm install -g @angular/cli
RUN ng build

FROM httpd:latest
COPY --from=build /node-app/dist/angularCalc /usr/local/apache2/htdocs
