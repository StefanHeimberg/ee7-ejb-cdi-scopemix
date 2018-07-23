# ee7-ejb-cdi-scopemix

JavaEE 7 Example Application to Demonstrate how CDI Scopes behaves in WEB and EJB Container inside an Enterprise Application Archive


## Quickstart

1. Start Application Server in Terminal 1 (Payara in this Example):

	```
	./clean.sh && ./build.sh && ./deploy.sh && ./run-payara.sh
	```

2. Analize Logs in Terminal 2:

	```
	./logs-ejbstartup.sh
	./logs-webstartup.sh
	./logs-webrequest.sh
	```
	
3. Do simple Web Request in Terminal 2:

	```
	./do-webrequest.sh
	```

4. Analize Logs again in Terminal 2:

	```
	./logs-webrequest.sh
	```
	
5. Stop Application Server and Clean up

	Press ```Ctr-C``` in Terminal 1 to Stop the Payara Docker container

	```
	./clean.sh
	```


## Requirements

- Java Development Kit 8 or Later
- Docker
- Linux / Mac Bash


## Conclusion

//TODO: Write conclusion...


## Common Shell scripts

- ```./clean.sh``` Cleanup all temporary created files and folders
- ```./build.sh``` Compile, Package Application into EAR file (ee7-ejb-cdi-scopemix-ear.ear)
- ```./deploy.sh``` Deploy EAR into Runnung Application Server (copy EAR into deployments/ directory)
- ```./do-webrequest.ch``` Create a simple GET Request to the ExampleWebRequest Servlet
- ```./logs-ejbstartup.sh``` Show all logs produced by ExampleEjbStartup
- ```./logs-webstartup.sh``` Show all logs produced by ExampleWebStartup
- ```./logs-webrequest.sh``` Show all logs produced by ExampleWebRequest


## Payara Specifics

- ```./run-payara.sh``` Start Payara Application Server Container (Management: http://localhost:4848)
- ```./shell-payara.sh``` Open a new Shell in the running Payara Container


## Wildfly Specifics

- ```./run-wildfly.sh``` Start Wildfly Application Server Container (Management: http://localhost:9990)
- ```./add-wildfly-admin-user.sh``` Create a Management Admin User (User: admin/admin)
- ```./shell-wildfly.sh``` Open a new Shell in the running Wildfly Container

Wildfly itself is using logback and slf4j as logging implementation. This is the reason why all the logs ar written into to server.log and not the example.log as implemented in the EAR.

But you can copy the server.log to example.log that all the ```./logs-*.sh``` scripts are working as expected.

```
docker exec -it ee7-ejb-cdi-scopemix-wildfly cp /opt/jboss/wildfly/standalone/log/server.log /opt/jboss/logs/example.log
```

## Weblogic Specifics

```./run-weblogic.sh```Start Weblogic Application Server Container (Management: http://localhost:8001, User: weblogic/Abc123465)


```
docker exec -it ee7-ejb-cdi-scopemix-weblogic cp servers/AdminServer/logs/AdminServer.log logs/example.log
```
