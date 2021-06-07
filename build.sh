#export EZYFOX_SERVER_HOME=
mvn -pl . clean install
mvn -pl chat-tutorial-common -Pexport clean install
mvn -pl chat-tutorial-app-api -Pexport clean install
mvn -pl chat-tutorial-app-entry -Pexport clean install
mvn -pl chat-tutorial-plugin -Pexport clean install