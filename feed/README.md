# Feed — a Twitter-like app (Jakarta EE 11)

Post short messages (280 chars), like them, delete your own. Built with
Servlets only, a `ServletContextListener` and an in-memory store; styled with
Tailwind CSS.

| URL | Servlet | Role |
| --- | --- | --- |
| `GET /feed` | `FeedServlet` | timeline + form |
| `POST /feed` | `FeedServlet` | publish (Post/Redirect/Get) |
| `POST /like` | `LikeServlet` | add a like |
| `POST /delete` | `DeleteServlet` | delete own post (author kept in the session) |

Data lives in memory (`FeedStore`) and is lost on restart.

```bash
npm install && npm run css      # builds css/styles.css
mvn clean package               # target/feed.war
cp target/feed.war $CATALINA_HOME/webapps/
```

Open <http://localhost:8080/feed/>.
