FROM openjdk:11

WORKDIR /app

RUN apt-get update
RUN apt-get -y install maven
RUN apt-get -y install pandoc
RUN apt-get -y install texlive-latex-base
RUN apt-get -y install texlive-fonts-recommended

COPY . /app

RUN mvn package
#ENV PORT=8080
#EXPOSE 5000
CMD ["sh", "target/bin/simplewebapp"]

