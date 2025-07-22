package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@WebFilter("/*")
public class RedirectToGuestFilter implements Filter {

    private FilterConfig filterConfig = null;

    public RedirectToGuestFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String context = req.getContextPath();

        try {

            if (uri.equals(context) || uri.equals(context + "/")) {
                res.sendRedirect(context + "/home");
                return;
            }

            chain.doFilter(request, response);
        } catch (Throwable t) {
            sendProcessingError(t, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            log("RedirectToGuestFilter initialized");
        }
    }

    @Override
    public void destroy() {
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        try {
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.print("<html><head><title>Error</title></head><body>");
            pw.print("<h1>The resource did not process correctly</h1><pre>");
            pw.print(stackTrace);
            pw.print("</pre></body></html>");
            pw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getStackTrace(Throwable t) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            return sw.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public void log(String msg) {
        if (filterConfig != null) {
            filterConfig.getServletContext().log(msg);
        }
    }
}
