# Introduction #

The eID Java EE 6 Archetype artifact provides an automatic generation of a blueprint for a Java EE applications with full eID integration.
The following technology stack is being using:
  * CDI 1.0
  * EJB 3.1
  * JSF 2.0
  * JPA 2.0
  * PrimeFaces 3.3.1

# Requirements #

  * Java JDK 6.0
  * Maven 3.0.3+
  * Working smart card reader
  * a Belgian eID card
  * a Java EE 6 application server like:
    * JBoss AS 7.1.1.Final (Web Profile is sufficient) together with Apache HTTPD Server
    * Oracle GlassFish 3.1.2

# Status #

For now only the admin portal has been implemented.

# Usage #

Run the following command:
```
mvn archetype:generate -DarchetypeGroupId=be.fedict.eid-archetype -DarchetypeArtifactId=eid-archetype-javaee6 -DarchetypeVersion=1.0.0.RC4 -DarchetypeRepository=https://www.e-contract.be/maven2/
```

This will generate the application blueprint.

Now simply enter the project directory and run:
```
mvn clean install
```


# JBoss AS 7.1.1.Final #

Add to `socket-binding-group` of `standalone/configuration/standalone.xml`
```
<socket-binding name="ajp" port="8009"/>
```
Also add to `subsystem xmlns="urn:jboss:domain:web:1.0"`
```
<connector name="ajp" protocol="AJP/1.3" scheme="https" socket-binding="ajp"/>
```
Start the standalone configuration via:
```
bin/standalone.sh
```

Enter the ...-ear directory of the project and deploy the EAR file on the local running JBoss AS via:
```
mvn jboss-as:deploy
```

Configure your Apache front-end server via the `/etc/httpd/conf.d/eid-example.conf` config file (CentOS/Fedora):
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

Restart the Apache server via:
```
sudo /etc/init.d/httpd restart
```

Configure Apache2 on Ubuntu as follows.
```
sudo a2ensite default-ssl
sudo a2enmod ssl
sudo /etc/init.d/apache2 restart
```

Now you can navigate to:

  * https://localhost/eid-example-admin/

# GlassFish 3.1.2 #

While creating the application using the Maven eID Archetype, you have to select a different value for `data-source`.
```
jdbc/__default
```

Start the default domain of GlassFish via:
```
./asadmin
asadmin> list-domains
asadmin> start-domain domain1
```

Start the default database via:
```
asadmin> start-database
```

Copy the EAR file to the GlassFish autodeploy directory:
```
cp the-ear-file.ear glassfish/domains/domain1/autodeploy/
```

Or deploy via the console:
```
asadmin> deploy /path/to/the-ear-file.ear
```

Now you can navigate to:

  * https://localhost:8181/eid-example-admin/

Install Apache `mod_jk`. Configure `/etc/httpd/conf.d/mod_jk.conf`
```
LoadModule jk_module modules/mod_jk.so

JkWorkersFile location-of-your-glassfish/domains/domain1/config/glassfish-jk.properties

JkMount /eid-example-admin/* eid-example-admin
JkAutoAlias /eid-example-admin
```

The `glassfish-jk.properties` configuration file should contain:
```
worker.list=worker1
worker.worker1.type=ajp13
worker.worker1.host=localhost.localdomain
worker.worker1.port=8009
worker.worker1.lbfactor=50
worker.worker1.cachesize=10
worker.worker1.cache_timeout=600
worker.worker1.socket_keepalive=1
worker.worker1.socket_timeout=300
```

Create an AJP connector via:
```
./asadmin
asadmin> create-network-listener --listenerport 8009 --jkenabled=true --protocol http-listener-1 ajp-listener
asadmin> list-network-listeners
asadmin> get server-config.network-config.network-listeners.network-listener.ajp-listener.*
asadmin> stop-domain domain1
asadmin> start-domain domain1
```

Now you can navigate to:

  * https://localhost/eid-example-admin/