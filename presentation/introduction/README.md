# Introduction to Jakarta EE (JEE)

A 10-slide introductory deck: what Jakarta EE is, how it works, an
introduction to **Servlets** and **JSP** (Jakarta Pages), and how to run the
app on **Apache Tomcat** (including a Windows install guide).

Built as a [Marp](https://marp.app/) presentation from `slides.md`.

## Files

| File | Description |
| ---- | ----------- |
| `slides.md` | Source (Marp Markdown) |
| `slides.html` | Web slideshow |
| `slides.pdf` | PDF handout |
| `slides.pptx` | PowerPoint export |
| `images/` | Diagrams (SVG) and real screenshots (PNG) |

## Slides

1. Title
2. What is Jakarta EE?
3. How Jakarta EE Works (architecture diagram)
4. How a Request Is Handled
5. Servlets
6. Jakarta Pages (JSP) with the translation flow
7. Putting It Together — the Hello World project (real screenshots)
8. The Web Container: Apache Tomcat
9. Installing Tomcat 11 on Windows
10. Summary & References

## Regenerate

Install the Marp CLI once, then:

```bash
npm i -g @marp-team/marp-cli

marp slides.md --allow-local-files --html  -o slides.html
marp slides.md --allow-local-files --pdf   -o slides.pdf
marp slides.md --allow-local-files --pptx  -o slides.pptx
```

The two PNG screenshots in `images/` are real captures of the
[`hello-world`](../../hello-world) project running on Apache Tomcat 11
(Servlet 6.1). The SVGs were authored for this deck.
