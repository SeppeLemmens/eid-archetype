# Introduction #

The eID Seam Archetype artifact provides an automatic generation of a blueprint for a Java EE applications with full eID integration.
The following technology stack is being using:
  * JBoss AS 5.1.0.GA, 6.0.0.Final, or 6.1.0.Final
  * Seam 2.2
  * EJB 3.0
  * JPA 1.0
  * JSF 1.2
  * Facelets 1.1
  * Richfaces 3.3

# Requirements #

  * Java 6.0
  * Maven 3.0.3+
  * JBoss AS 6.1.0.Final
  * Apache HTTPD Server
  * Working smart card reader
  * a Belgian eID card

# Status #

Fully implemented.

# Usage #

Run the following command:
```
mvn archetype:generate -DarchetypeGroupId=be.fedict.eid-archetype -DarchetypeArtifactId=eid-archetype-seam -DarchetypeVersion=1.0.0.RC3 -DarchetypeRepository=https://www.e-contract.be/maven2/
```

This will generate the application blueprint.

Now simply enter the project directory and run:
```
mvn clean install
```

Launch (Linux) JBoss AS 6.1.0.Final via:
```
cd jboss-6.1.0.Final
cd bin
./run.sh
```

Enter the ...-ear directory of the project and deploy the EAR file on the local running JBoss AS via:
```
mvn jboss:deploy
```

Configure your Apache front-end server via the `/etc/httpd/conf.d/eid-example.conf` config file (Fedora/CentOS):
```
<Location /eid-example>
	Order allow,deny
	Allow from all
	ProxyPass ajp://localhost:8009/eid-example
</Location>

<Location /eid-example-admin>
	Order allow,deny
	Allow from all
	ProxyPass ajp://localhost:8009/eid-example-admin
</Location>
```

Also make sure that `mod_ssl` has been installed as the application requires SSL.

Restart the Apache server via (Fedora/CentOS):
```
sudo /etc/init.d/httpd restart
```

Now you can navigate to:

  * https://localhost/eid-example-admin/
  * https://localhost/eid-example/