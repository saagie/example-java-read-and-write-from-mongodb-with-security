Example for read & write into MongoDB with security
===================================================
Package for saagie : mvn package and get the package in target.

Usage in local :

 - mvn package
 - java -jar target/example-java-read-and-write-from-mongodb-with-security-1.0-SNAPSHOT-jar-with-dependencies.jar mongodb://user:password@host:27017/database

Usage in Saagie :

 - mvn package (in local, to generate jar file)
 - create new Java Job
 - upload the jar (target/example-java-read-and-write-from-mongodb-with-security-1.0-SNAPSHOT-jar-with-dependencies.jar)
 - copy URL from mongo connection details panel and add it in first argument in the command line. Replace username and password with their respective values. Be careful, if your password contains special characters, you might have to escape them or it won't work.
 - choose java 8
 - create and launch.
