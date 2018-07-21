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

## Run in Payara

```
./run-payara.sh
```

Konsole: http://localhost:4848
User   : <ohne>

## Run in Wildfly

```
./run-wildfly.sh
./add-wildfly-admin-user.sh
```

Konsole : http://localhost:9990
User    : admin/admin

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