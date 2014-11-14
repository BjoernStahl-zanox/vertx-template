FROM my-aws-external-ip:5000/base-image:1.0

ADD template-${project.version}-fat.jar /
EXPOSE 8090
EXPOSE 8080
CMD java -server -XX:+DoEscapeAnalysis -XX:+UseStringDeduplication -XX:+UseCompressedOops -XX:+UseG1GC -XX:MaxGCPauseMillis=5 -XX:MaxTenuringThreshold=5 -XX:+UseLargePages -Xmx1024M -Xms1024M -XX:+AggressiveOpts -jar trackingppv-${project.version}-fat.jar
