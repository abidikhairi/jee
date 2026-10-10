# Hello World — Jakarta EE 11

A minimal **Jakarta EE 11** web application built with **Maven**. It contains two
classic web components:

| Component | File | URL |
| --------- | ---- | --- |
| **Servlet** | `src/main/java/com/example/helloworld/HelloServlet.java` | `/hello?name=Jakarta` |
| **JSP** (Jakarta Pages) | `src/main/webapp/index.jsp` | `/index.jsp` |

The **Servlet** is mapped with the `@WebServlet` annotation (Jakarta Servlet 6.1).
The **JSP** is translated and compiled into a servlet by the container the first
time it is requested.

## Requirements

- **JDK 17 or newer** (Jakarta EE 11 requires Java 17+)
- **Maven 3.9+**

## Build

```bash
mvn package
```

This produces the deployable web archive:

```
target/hello-world.war
```

> The build uses `jakarta.platform:jakarta.jakartaee-api:11.0.0` with `provided`
> scope — the API classes come from the application server at runtime, not from
> the WAR.

## Run

Deploy `target/hello-world.war` to any **Jakarta EE 11** compatible server, for
example **WildFly** 36+, **GlassFish** 8, **Payara** 7, **Open Liberty**, or
**Apache Tomcat** 11 (web profile — Servlets + JSP).

### Run it on Apache Tomcat 11

Tomcat 11 implements **Jakarta Servlet 6.1**, so it is a true Jakarta EE 11 web
container and needs no other configuration.

**1. Prerequisites** — a JDK 17+ and Maven:

```bash
java -version      # must be 17 or newer
mvn -version
```

> On this machine the default `java` is 8 (too old). Point `JAVA_HOME` at a
> newer JDK for every Maven and Tomcat command:
>
> ```bash
> export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
> ```

**2. Install Tomcat 11** (check <https://tomcat.apache.org/download-11.cgi> for
the latest version):

```bash
cd ~
curl -LO https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.26/bin/apache-tomcat-11.0.26.tar.gz
tar xzf apache-tomcat-11.0.26.tar.gz
mv apache-tomcat-11.0.26 ~/tomcat11
export CATALINA_HOME=~/tomcat11
```

**3. Build the WAR** from the project directory:

```bash
cd /home/khairi/work/fsjegj/jee/hello-world
JAVA_HOME=/usr/lib/jvm/java-21-openjdk mvn clean package
```

**4. Deploy** — copy the WAR into Tomcat's `webapps/` folder. The file name
becomes the context path, so `hello-world.war` is served under `/hello-world`:

```bash
cp target/hello-world.war "$CATALINA_HOME/webapps/"
```

**5. Start Tomcat:**

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
"$CATALINA_HOME/bin/catalina.sh" run     # foreground — Ctrl+C to stop
```

or run it in the background and stop it later:

```bash
"$CATALINA_HOME/bin/startup.sh"
"$CATALINA_HOME/bin/shutdown.sh"
```

**6. Open the app** at <http://localhost:8080/hello-world/>:

| URL | Component |
| --- | --------- |
| <http://localhost:8080/hello-world/> | welcome file → `index.jsp` |
| <http://localhost:8080/hello-world/index.jsp> | the JSP page |
| <http://localhost:8080/hello-world/hello?name=Jakarta> | the Servlet |

### Install with a package manager (optional)

For a genuine **Jakarta EE 11 (Servlet 6.1)** container, prefer the Tomcat 11
tarball above. Distro packages are often one version behind:

- **Arch / Manjaro** — `tomcat10` (Servlet 6.0). It runs this simple app:

  ```bash
  sudo pacman -S tomcat10
  sudo systemctl enable --now tomcat10
  sudo cp target/hello-world.war /var/lib/tomcat10/webapps/
  # → http://localhost:8080/hello-world/
  # logs:   /var/log/tomcat10/catalina.out
  # config: /etc/tomcat10/server.xml
  ```

- **Debian / Ubuntu** — `sudo apt install tomcat10` (Servlet 6.0).
- **macOS (Homebrew)** — `brew install tomcat` (installs the latest, Tomcat 11).

### Troubleshooting

| Symptom | Fix |
| ------- | --- |
| `Neither the JAVA_HOME nor the JRE_HOME ...` | `export JAVA_HOME=/usr/lib/jvm/java-21-openjdk` before starting Tomcat |
| `Address already in use` (port 8080) | change the port in `$CATALINA_HOME/conf/server.xml`, or stop the other process (`ss -ltnp \| grep 8080`) |
| HTTP **404** | the context path is the WAR file name — check the URL, or rename the WAR to `ROOT.war` to serve it at `/` |
| Nothing appears on startup | read the log: `tail -f "$CATALINA_HOME/logs/catalina.out"` |

## Project layout

```
hello-world/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/example/helloworld/
    │   └── HelloServlet.java      # @WebServlet("/hello")
    └── webapp/
        ├── index.jsp              # HTML + Java template
        └── WEB-INF/web.xml        # minimal deployment descriptor
```
