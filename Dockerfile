#############
### build ###
#############

# base image
FROM maven:3-jdk-11 as build

# set working directory
WORKDIR /app

# add app
COPY . .

RUN export MAVEN_OPTS="-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN -Dorg.slf4j.simpleLogger.showDateTime=true -Djava.awt.headless=true" && export MAVEN_CLI_OPTS="-B -U --batch-mode --errors --fail-at-end --show-version -DinstallAtEnd=true -DdeployAtEnd=true"

RUN mvn $MAVEN_CLI_OPTS clean install

############
### prod ###
############

# Yea this isn't right, but it crashes before it gets to this point. This is for example purposes only.
FROM openjdk:15-jdk-alpine
COPY --from=build /app/reproducer-testcontainer/target/reproducer-testcontainer.jar /reproducer-testcontainer.jar
CMD java -jar reproducer-testcontainer.jar
