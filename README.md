# crash-heapdump

## Build and Install

```
git clone https://github.com/pdprof/crash-heapdump.git
cd crash-heapdump/crash.heapdump
mvn build
mvn install
```

You can build crash.heapdump.war file to deploy it to liberty server.



## Create crash.heapdump project

1. Create eclipse maven project (using Archetype = artifact id webapp-jee7-liberty)
2. Create src/main/liberty/config direcotory
3. Copy liberty server.xml to src/main/liberty/config directory
4. Create Dockerfile under project directory
5. Commit and push to distribute repository

sample server.xml and Dockerfile are placed on this git repositry.
