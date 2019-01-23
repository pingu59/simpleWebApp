FROM openjdk:11

WORKDIR /app

RUN sudo apt-get update
RUN sudo apt-get -y install maven pandoc texlive-latex-base texlive-fonts-recommended

COPY . /app

RUN mvn package
#ENV PORT=8080
#EXPOSE 5000
CMD ["sh", "target/bin/simplewebapp"]

