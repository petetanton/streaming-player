#!/usr/bin/env bash
git push
rm pom.xml.releaseBackup
rm release.properties
mvn clean
mvn install
mvn release:prepare
git push

scm_tag=$(grep "^scm.tag=" "release.properties" | cut -d'=' -f2)

curl -X POST http://192.168.1.5:8080/job/sr-player-build/build \
  --data-urlencode json='{"parameter": [{"name":"tag", "value":"'"${scm_tag}"'"}]}'