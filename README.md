# ee7-ejb-cdi-scopemix

JavaEE 7 Example Application to Demonstrate how CDI Scopes behaves in EJB Containert


## Clean

```
./clean.sh
```

## Build

```
./build.sh
```

## Deploy / Redploy

```
./deploy.sh
```

## Run Payara

```
./run-payara.sh
```

Konsole: http://localhost:4848
User   : <ohne>

## Run Wildfly

```
./run-wildfly.sh
./add-wildfly-admin-user.sh
```

Konsole : http://localhost:9990
User    : admin/admin

In wildfly werden die logs nicht ins example.log geschrieben. wildfly schreibt diese
ins /opt/jboss/wildfly/standalone/log/server.log

```
docker exec -it ee7-ejb-cdi-scopemix-wildfly cp /opt/jboss/wildfly/standalone/log/server.log /opt/jboss/logs/example.log
```

## Run Weblogic

```
./run-weblogic.sh
```

Konsole : http://localhost:8001/console
User    : weblogic/Abc123465


## Do Requests

```
./do-webrequest.sh
```


## Analize Logs

```
./logs-ejbstartup.sh
```

```
./logs-webstartup.sh
```

```
./logs-webrequest.sh
```