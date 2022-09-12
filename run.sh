DEBUG_PORT=$1
MAVEN_RELOAD=$2
mvn clean -T 1C package $MAVEN_RELOAD -DskipTests
if [ -n $JREBEL_HOME ]; then
    export JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$DEBUG_PORT"
    export JAVA_OPTS="$JAVA_OPTS -noverify -agentpath:$JREBEL_HOME/lib/libjrebel64.dylib"
    echo "SETTING JREBEL=$JREBEL_HOME/lib/libjrebel64.dylib"
fi
java $JAVA_OPTS \
    -Xms1024m -Xmx1024m \
    -Dspring.profiles.active=local \
    -Duser.timezone=GMT+08 \
    -jar ./target/transactionTest-0.0.1-SNAPSHOT.jar
