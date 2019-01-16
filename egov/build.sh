mvn clean package -s settings.xml -U -Ddb.url=jdbc:postgresql://localhost:5432/digitbpa -Ddb.password=postgres -Ddb.user=postgres -Ddb.driver=org.postgresql.Driver >> build.log &

tail -f build.log
