# Résumé — Jakarta EE key concepts (Servlets)

A 13-slide revision deck for students (in French): the web container, HTTP and
the Servlet API, lifecycle and thread-safety, request/response and scopes,
sessions, Post/Redirect/Get, forward vs redirect and MVC, filters and
listeners, the [`calculatrice`](../../calculatrice) project, good practices and
a summary with exercises.

Built as a [Marp](https://marp.app/) presentation from `slides.md`
(same theme as [`../introduction`](../introduction)).

## Regenerate

```bash
npm i -g @marp-team/marp-cli

marp slides.md --allow-local-files --html -o slides.html
marp slides.md --allow-local-files --pdf  -o slides.pdf
marp slides.md --allow-local-files --pptx -o slides.pptx
```
