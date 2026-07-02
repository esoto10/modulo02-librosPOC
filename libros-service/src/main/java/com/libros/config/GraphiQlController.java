package com.libros.config;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GraphiQlController {

    @GetMapping(value = "/graphiql", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String graphiql() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8" />
                  <title>GraphiQL — Libros POC</title>
                  <style>
                    * { box-sizing: border-box; }
                    body { height: 100%; margin: 0; width: 100%; overflow: hidden; }
                    #graphiql { height: 100vh; }
                  </style>
                  <link rel="stylesheet" href="/vendor/graphiql.min.css" />
                  <link rel="stylesheet" href="/vendor/explorer.css" />
                </head>
                <body>
                  <div id="graphiql">Cargando GraphiQL...</div>
                  <script src="/vendor/react.min.js"></script>
                  <script src="/vendor/react-dom.min.js"></script>
                  <script src="/vendor/graphiql.min.js"></script>
                  <script src="/vendor/explorer.umd.js"></script>
                  <script>
                    const fetcher = GraphiQL.createFetcher({ url: '/graphql' });
                    const explorerPlugin = GraphiQLPluginExplorer.explorerPlugin();
                    const root = ReactDOM.createRoot(document.getElementById('graphiql'));
                    root.render(
                      React.createElement(GraphiQL, {
                        fetcher: fetcher,
                        defaultVariableEditorOpen: true,
                        plugins: [explorerPlugin],
                      })
                    );
                  </script>
                </body>
                </html>
                """;
    }
}
