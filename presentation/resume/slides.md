---
marp: true
theme: default
paginate: true
html: true
size: 16:9
title: "Résumé : les concepts clés du développement JEE (Servlets)"
description: Key Jakarta EE web concepts for students, focused on Servlets
---

<style>
:root {
  --navy: #1b1f3b;
  --indigo: #4b3f9e;
  --blue: #2f6fb3;
  --accent: #d94f86;
  --teal: #1f9c8f;
  --ink: #1c2330;
  --muted: #5b6472;
  --line: #dfe4ec;
  --code-bg: #0f172a;
}

section {
  font-family: "Segoe UI", "Helvetica Neue", Arial, sans-serif;
  color: var(--ink);
  background: #ffffff;
  font-size: 24px;
  line-height: 1.34;
  padding: 46px 58px 56px;
  letter-spacing: 0.1px;
}

h1 {
  color: var(--indigo);
  font-size: 38px;
  font-weight: 700;
  margin: 0 0 16px;
  letter-spacing: -0.2px;
}
h2 { color: var(--blue); font-size: 27px; margin: 0 0 10px; }
h3 { color: var(--navy); font-size: 23px; margin: 14px 0 6px; }
p, li { margin: 6px 0; }
li { margin-left: 2px; }
strong { color: var(--navy); }
a { color: var(--blue); }
ul, ol { margin: 8px 0 8px 2px; padding-left: 26px; }

code {
  font-family: "JetBrains Mono", "Fira Code", "Consolas", monospace;
  font-size: 0.9em;
}
pre {
  background: var(--code-bg);
  border-radius: 10px;
  padding: 12px 16px;
  font-size: 16px;
  line-height: 1.35;
  box-shadow: inset 0 0 0 1px #1e293b;
}
pre code { background: transparent; color: #e8eef7; }
:not(pre) > code {
  background: #eef2f8;
  color: #1d3a63;
  border-radius: 5px;
  padding: 1px 6px;
}

table {
  font-size: 19px;
  border-collapse: collapse;
  width: 100%;
  margin: 6px 0;
}
th {
  background: var(--navy);
  color: #fff;
  text-align: left;
  padding: 6px 11px;
  font-weight: 600;
}
td { padding: 5px 11px; border-bottom: 1px solid var(--line); vertical-align: top; }
tr:nth-child(even) td { background: #f6f8fc; }

section::after { color: #9aa4b2; font-size: 15px; right: 30px; bottom: 18px; }

/* Title slide */
section.title {
  background: linear-gradient(135deg, #1b1f3b 0%, #3b2f7a 58%, #b23563 125%);
  color: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 76px;
}
section.title h1 { color: #ffffff; font-size: 62px; margin: 0 0 2px; }
section.title .title-sub { color: #d7d3f4; font-size: 28px; font-weight: 500; margin: 0 0 22px; }
section.title .title-tags {
  display: inline-block;
  background: rgba(255,255,255,0.12);
  border: 1px solid rgba(255,255,255,0.28);
  border-radius: 24px;
  padding: 8px 22px;
  font-size: 19px;
  color: #ffffff;
  letter-spacing: 0.3px;
  align-self: flex-start;
}
section.title .title-meta { color: #c9c4e6; font-size: 19px; margin-top: 26px; }
section.title::after { color: rgba(255,255,255,0.55); }

/* Layout helpers */
.cols { display: flex; gap: 30px; align-items: flex-start; }
.cols > div { flex: 1; min-width: 0; }
.col-58 { flex: 0 0 58% !important; }
.col-46 { flex: 0 0 46% !important; }
.col-52 { flex: 0 0 52% !important; }
.col-48 { flex: 0 0 48% !important; }
.col-44 { flex: 0 0 44% !important; }
.col-56 { flex: 0 0 56% !important; }
.col-50 { flex: 0 0 50% !important; }
.col-42 { flex: 0 0 42% !important; }
.caption { color: var(--muted); font-size: 16px; margin-top: 8px; }
.muted { color: var(--muted); font-weight: 400; }

.note {
  background: #fff7ef;
  border-left: 5px solid #e8762c;
  padding: 9px 14px;
  border-radius: 6px;
  font-size: 19px;
  margin-top: 12px;
}

/* Flow diagram */
.flow {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  font-size: 18px;
  margin: 12px 0 18px;
  flex-wrap: wrap;
}
.flow .box {
  background: #f2f6fc;
  border: 1px solid #cdd9ea;
  border-radius: 8px;
  padding: 10px 16px;
  text-align: center;
}
.flow .arrow { color: #e8762c; font-weight: 700; }
.flow.v { flex-direction: column; gap: 4px; margin: 10px 0 14px; }
.flow.v .box { width: 100%; padding: 9px 12px; }

/* Screenshots */
.shots img {
  width: 100%;
  border: 1px solid var(--line);
  border-radius: 7px;
  box-shadow: 0 6px 16px rgba(20,35,70,0.14);
  display: block;
}
.shots figure { margin: 0 0 12px; }
.shots figcaption { color: var(--muted); font-size: 15px; margin-top: 5px; }

pre code [class^="hljs"] { color: #e8eef7 !important; }
pre code .hljs-comment { color: #8fa3bf !important; }
pre code .hljs-keyword, pre code .hljs-meta { color: #f2a6c8 !important; }
pre code .hljs-string { color: #9be3c8 !important; }

img { image-rendering: -webkit-optimize-contrast; }
</style>

<!-- _class: title -->
<!-- _paginate: false -->

# Résumé

<p class="title-sub">Les concepts clés du développement Jakarta EE &mdash; focus Servlets</p>

<p class="title-tags">Container &nbsp;·&nbsp; Servlet &nbsp;·&nbsp; Request / Response &nbsp;·&nbsp; Session &nbsp;·&nbsp; Filter &nbsp;·&nbsp; MVC</p>

<p class="title-meta">
Computer Science &mdash; Jakarta EE &nbsp;|&nbsp; Lecture 02<br>
Fiche de révision pour les étudiants
</p>

---

# Le tableau d'ensemble

<div class="cols">
<div class="col-50">

- **Jakarta EE** = des **spécifications** (pas un produit).
- Un **conteneur web** (Tomcat 11) implémente **Servlet 6.1** et **JSP**.
- Votre code = des **classes Java** déployées dans un **WAR**.
- Le conteneur gère : **réseau, threads, cycle de vie, sessions, sécurité**.

<div class="note">
Vous écrivez la <b>logique</b> ; le conteneur appelle <b>votre</b> code (inversion de contrôle).
</div>

</div>
<div class="col-50">

<div class="flow v">
  <div class="box"><b>Navigateur</b> &mdash; requête HTTP</div>
  <div class="arrow">&darr;</div>
  <div class="box"><b>Conteneur</b> (Tomcat) &mdash; crée <code>request</code> / <code>response</code></div>
  <div class="arrow">&darr;</div>
  <div class="box"><b>Filter</b> &rarr; <b>Servlet</b> &rarr; (JSP)</div>
  <div class="arrow">&darr;</div>
  <div class="box"><b>Réponse HTTP</b> &mdash; HTML / JSON</div>
</div>

</div>
</div>

---

# Rappel HTTP : ce que reçoit une servlet

<div class="cols">
<div class="col-50">

```http
POST /calculatrice/calc HTTP/1.1
Host: localhost:8080
Content-Type: application/x-www-form-urlencoded
Cookie: JSESSIONID=ABC123

a=7&b=2&op=mul
```

</div>
<div class="col-50">

| Partie HTTP | API Servlet |
| --- | --- |
| Méthode | `req.getMethod()` |
| Chemin | `req.getRequestURI()` |
| Paramètres | `req.getParameter("a")` |
| En-têtes | `req.getHeader("Cookie")` |
| Corps | `req.getReader()` |
| Cookies | `req.getCookies()` |

<p class="caption">Réponse : code (<code>200</code>, <code>302</code>, <code>404</code>&hellip;), en-têtes, corps.</p>

</div>
</div>

---

# Anatomie d'une Servlet

```java
@WebServlet(name = "calculatrice", urlPatterns = "/calc")      // 1. mapping URL
public class CalculatriceServlet extends HttpServlet {          // 2. hérite de HttpServlet

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {                                // 3. une méthode par verbe HTTP
        resp.setContentType("text/html;charset=UTF-8");          // 4. type + encodage
        resp.getWriter().print("<h1>Calculatrice</h1>");         // 5. écrire le corps
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) { /* ... */ }
}
```

| Verbe | Méthode | Usage typique |
| --- | --- | --- |
| GET / POST | `doGet` / `doPost` | afficher / soumettre un formulaire |
| PUT / DELETE | `doPut` / `doDelete` | modifier / supprimer (API REST) |

---

# Cycle de vie &amp; concurrence

<div class="cols">
<div class="col-46">

<div class="flow v">
  <div class="box"><b>Chargement</b> &mdash; 1 instance</div>
  <div class="arrow">&darr;</div>
  <div class="box"><b>init()</b> &mdash; une fois</div>
  <div class="arrow">&darr;</div>
  <div class="box"><b>service()</b> &rarr; <code>doGet</code> / <code>doPost</code><br><span class="muted">à chaque requête, dans un thread du pool</span></div>
  <div class="arrow">&darr;</div>
  <div class="box"><b>destroy()</b> &mdash; à l'arrêt</div>
</div>

</div>
<div class="col-54">

- **Une seule instance** par servlet, **plusieurs threads** en parallèle.
- &#10060; Pas d'état modifiable dans des **attributs d'instance**.
- &#9989; État par requête &rarr; **variables locales**.
- &#9989; État par utilisateur &rarr; **`HttpSession`**.

```java
// DANGER : partagé entre tous les utilisateurs !
private int compteur = 0;
```

<div class="note">
Une <b>race condition</b> sur un champ de servlet est l'erreur n°1 des débutants.
</div>

</div>
</div>

---

# Request, Response &amp; les 4 portées

<div class="cols">
<div class="col-50">

```java
String a  = req.getParameter("a");   // entrée
req.setAttribute("res", 15);          // partage interne
resp.setStatus(200);
resp.sendRedirect("calc");            // 302
```

- **Toujours** valider les paramètres : `getParameter` renvoie `null` ou du texte.
- **Toujours** échapper la sortie HTML (anti-**XSS**).
- `setCharacterEncoding("UTF-8")` avant de lire le corps.

</div>
<div class="col-50">

| Portée | Objet | Durée de vie |
| --- | --- | --- |
| Page | `PageContext` | la page JSP |
| **Request** | `HttpServletRequest` | une requête |
| **Session** | `HttpSession` | un utilisateur |
| **Application** | `ServletContext` | toute l'appli |

<p class="caption"><code>setAttribute</code> / <code>getAttribute</code> existent sur chacun d'eux.</p>

</div>
</div>

---

# Sessions &amp; état utilisateur

<div class="cols">
<div class="col-52">

HTTP est **sans état**. Le conteneur ajoute l'état avec un cookie **`JSESSIONID`**.

```java
HttpSession session = req.getSession();       // crée si absente
List<String> h = (List<String>)
        session.getAttribute("history");
session.setAttribute("history", h);
session.invalidate();                          // déconnexion
```

</div>
<div class="col-48">

<div class="flow v">
  <div class="box">1&nbsp;&nbsp;1<sup>re</sup> requête &rarr; pas de cookie</div>
  <div class="arrow">&darr;</div>
  <div class="box">2&nbsp;&nbsp;<code>Set-Cookie: JSESSIONID=ABC</code></div>
  <div class="arrow">&darr;</div>
  <div class="box">3&nbsp;&nbsp;requêtes suivantes envoient le cookie</div>
</div>

<p class="caption">Exemple : l'historique de la <b>calculatrice</b> vit dans la session.</p>

</div>
</div>

---

# Le pattern Post / Redirect / Get

<div class="flow">
  <div class="box"><b>POST</b> /calc<br>calcule</div>
  <div class="arrow">&rarr;</div>
  <div class="box"><b>302</b><br>Location: /calc</div>
  <div class="arrow">&rarr;</div>
  <div class="box"><b>GET</b> /calc<br>affiche</div>
</div>

- Évite le **double envoi** quand l'utilisateur actualise la page.
- Le résultat / l'erreur passe par la **session** (message « flash »), puis est supprimé.

```java
session.setAttribute("error", "Division par zéro impossible");
resp.sendRedirect(req.getContextPath() + "/calc");   // jamais de logique après un redirect
```

---

# Forward, Redirect &amp; MVC

<div class="cols">
<div class="col-50">

| | `forward` | `sendRedirect` |
| --- | --- | --- |
| Où ? | **serveur** | **navigateur** |
| URL change | non | oui |
| Requêtes | 1 | 2 |
| Partage `request` | oui | non |

```java
req.setAttribute("res", res);
req.getRequestDispatcher("/WEB-INF/result.jsp")
   .forward(req, resp);
```

</div>
<div class="col-50">

**MVC avec Jakarta EE**

<div class="flow v">
  <div class="box"><b>Model</b> &mdash; classes Java (POJO)</div>
  <div class="arrow">&uarr;&darr;</div>
  <div class="box"><b>Controller</b> &mdash; Servlet</div>
  <div class="arrow">&darr;</div>
  <div class="box"><b>View</b> &mdash; JSP (dans <code>WEB-INF</code>)</div>
</div>

<p class="caption">Servlet = logique, JSP = affichage. Ne pas mélanger.</p>

</div>
</div>

---

# Filters, Listeners &amp; configuration

<div class="cols">
<div class="col-50">

**Filter** &mdash; code avant / après la servlet (log, encodage, auth) :

```java
@WebFilter("/*")
public class LogFilter implements Filter {
  public void doFilter(ServletRequest rq,
        ServletResponse rs, FilterChain chain)
        throws IOException, ServletException {
    // avant
    chain.doFilter(rq, rs);
    // après
  }
}
```

</div>
<div class="col-50">

**Listener** &mdash; réagit aux événements (`ServletContextListener`, `HttpSessionListener`).

**Configuration**

| Moyen | Exemple |
| --- | --- |
| Annotation | `@WebServlet("/calc")` |
| `web.xml` | `<welcome-file-list>`, erreurs |
| Init param | `@WebInitParam` |

<div class="note">
Les annotations suffisent depuis Servlet 3.0 ; <code>web.xml</code> sert aux réglages globaux.
</div>

</div>
</div>

---

# Projet : Calculatrice (Servlets + Tailwind)

<div class="cols">
<div class="col-50">

```
calculatrice/
├── pom.xml
├── package.json          (Tailwind CLI)
└── src/main/
    ├── java/.../calculatrice/
    │   ├── CalculatriceServlet.java
    │   ├── HistoryServlet.java
    │   ├── Operation.java
    │   └── Layout.java
    ├── tailwind/input.css
    └── webapp/
        ├── css/styles.css   (généré)
        └── WEB-INF/web.xml
```

</div>
<div class="col-50">

- `GET /calc` &rarr; formulaire + historique.
- `POST /calc` &rarr; calcul, historique en **session**, **redirect**.
- `POST /history` &rarr; efface l'historique.
- **Tailwind** : `npm run css` scanne les classes écrites dans les servlets.

```bash
cd calculatrice
npm install && npm run css
mvn package      # target/calculatrice.war
```

</div>
</div>

---

# Bonnes pratiques &amp; erreurs fréquentes

<div class="cols">
<div class="col-50">

### &#9989; À faire
- Valider **toutes** les entrées (`null`, format, division par 0).
- Échapper la sortie HTML (**XSS**).
- `setContentType` + **UTF-8** partout.
- Post/Redirect/Get après un `POST`.
- Séparer logique (Java) et présentation.
- Garder les servlets **minces** : déléguer à des classes métier.

</div>
<div class="col-50">

### &#10060; À éviter
- État modifiable dans les champs de la servlet.
- Écrire tout le HTML dans la servlet (OK pour apprendre, pas en production &rarr; JSP).
- Oublier `return` après `sendRedirect` / `sendError`.
- Avaler les exceptions sans message utile.
- Faire confiance aux paramètres du client.

</div>
</div>

---

# À retenir

<div class="cols">
<div class="col-54">

1. Le **conteneur** appelle vos servlets ; vous ne les instanciez jamais.
2. **Une instance, plusieurs threads** &rarr; pas d'état partagé.
3. `doGet` / `doPost` lisent la **request**, écrivent la **response**.
4. **Session** = état par utilisateur ; **Application** = état global.
5. **Forward** (interne) vs **Redirect** (navigateur).
6. **Filter** pour le code transversal ; **MVC** pour l'organisation.

</div>
<div class="col-46">

<h3>Références</h3>

- Servlet 6.1 &mdash; <https://jakarta.ee/specifications/servlet/6.1/>
- Jakarta EE Tutorial &mdash; <https://eclipse-ee4j.github.io/jakartaee-tutorial/>
- Apache Tomcat 11 &mdash; <https://tomcat.apache.org/>
- Tailwind CSS &mdash; <https://tailwindcss.com/>

<h3>Exercices</h3>

- Ajouter l'opération **modulo** et **puissance** à `Operation`.
- Ajouter un `LogFilter` qui mesure le temps de chaque requête.
- Déplacer le HTML de la calculatrice dans une **JSP** (forward).

<p class="caption">Code : <code>hello-world/</code> et <code>calculatrice/</code> dans ce dépôt.</p>

</div>
</div>
