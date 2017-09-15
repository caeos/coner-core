#!/usr/bin/env bash

cd client-generator/target/generated-sources/swagger-codegen/coner-core-client-java

# Post-process the coner-core-client-java/.bintray.json
BINTRAY_JSON=".bintray.json"
jq_mutate_file ".version.name = \"${TRAVIS_TAG}\"" $BINTRAY_JSON
jq_mutate_file ".version.desc = \"${TRAVIS_TAG}\"" $BINTRAY_JSON
TODAY=`date +%Y-%m-%d`
jq_mutate_file ".version.released = \"${TODAY}\"" $BINTRAY_JSON
jq_mutate_file ".version.vcs_tag = \"$TRAVIS_COMMIT\"" $BINTRAY_JSON

# Remove any Gradle files from the template (to stop Travis preferring it over Maven)
rm -rf *gradle*

# Push generated client repos
git init
git add .
git commit -m "Generated coner-core-client-java $TRAVIS_TAG"
git tag "$TRAVIS_TAG"
git remote add origin "https://carltonwhitehead:${GITHUB_JAVA_CLIENT_API_KEY}@github.com/caeos/coner-core-client-java.git"
git push --tags --force origin master
