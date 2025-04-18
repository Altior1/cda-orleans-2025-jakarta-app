package fr.formation.jakarta.filter;

import io.github.cdimascio.dotenv.Dotenv;
import io.pebbletemplates.pebble.PebbleEngine;
import io.pebbletemplates.pebble.loader.ClasspathLoader;
import io.pebbletemplates.pebble.template.PebbleTemplate;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
public class GlobalExceptionFilter implements Filter {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionFilter.class);

    private boolean isDev;
    private PebbleEngine pebble;

    @Override
    public void init(FilterConfig filterConfig) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String env = dotenv.get("APP_ENV", "prod").toLowerCase();
        isDev = env.equals("dev");

        ClasspathLoader loader = new ClasspathLoader();
        loader.setPrefix("templates");

        pebble = new PebbleEngine.Builder()
                .loader(loader)
                .build();

        LOGGER.info("GlobalExceptionFilter initialisé (mode : {})",
                isDev ? "dev" : "prod");
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Throwable ex) {
            LOGGER.error("Erreur serveur interceptée", ex);

            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setContentType("text/html");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            Map<String, Object> context = new HashMap<>();

            if (isDev) {
                context.put("exception", ex.getClass().getName());
                context.put("message", ex.getMessage());

                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                context.put("stacktrace", sw.toString());

                renderTemplate(resp, "error-dev.peb", context);
            } else {
                renderTemplate(resp, "error.peb", context);
            }
        }
    }

    private void renderTemplate(
            HttpServletResponse response,
            String templateName,
            Map<String, Object> context
    ) throws IOException {
        try (Writer writer = response.getWriter()) {
            PebbleTemplate template = pebble.getTemplate(templateName);
            template.evaluate(writer, context);
        } catch (Exception e) {
            LOGGER.error("Erreur lors du rendu du template : {}", templateName, e);
            throw new IOException("Erreur de rendu du template", e);
        }
    }
}
