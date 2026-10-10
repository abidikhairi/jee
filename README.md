# Jakarta EE (JEE) — Learning Materials

A small collection of projects for learning **Jakarta EE** (formerly Java EE):
a hands-on example web application and an introductory slide deck.

## Contents

| Path | Description |
| ---- | ----------- |
| [`hello-world/`](hello-world/) | A minimal Jakarta EE 11 web app (Maven) with a Servlet and a JSP |
| [`presentation/introduction/`](presentation/introduction/) | A 10-slide introduction to Jakarta EE, Servlets, JSP, and Apache Tomcat |

## Requirements

- **JDK 17 or newer** (Jakarta EE 11 requires Java 17+)
- **Maven 3.9+**
- A **Jakarta EE 11** compatible container (e.g. Apache Tomcat 11) to run the app

## Quick start

Build and run the example application:

```bash
cd hello-world
JAVA_HOME=/usr/lib/jvm/java-21-openjdk mvn clean package
```

This produces `hello-world/target/hello-world.war`. Deploy it to your
application server, then open:

- <http://localhost:8080/hello-world/> — the JSP welcome page
- <http://localhost:8080/hello-world/hello?name=Jakarta> — the Servlet

See [`hello-world/README.md`](hello-world/README.md) for the full build, deploy,
and troubleshooting guide.

## The presentation

The deck in [`presentation/introduction/`](presentation/introduction/) is built
with [Marp](https://marp.app/) from `slides.md` and exported to HTML, PDF, and
PowerPoint. See its [README](presentation/introduction/README.md) to regenerate
the exports.

## License

Educational material — use freely for learning and teaching.
