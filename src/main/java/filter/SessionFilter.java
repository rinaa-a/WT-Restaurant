package filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/restaurant/*")
public class SessionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            super.doFilter(request, response, chain);
        } else {
            String url = request.getRequestURL().toString();
            String resource = url.substring(url.lastIndexOf("/"));
            if (resource.equals("/menu") || resource.equals("/orders") || resource.equals("/addDish") ||
                    resource.equals("/editDish")) {
                response.sendRedirect(request.getContextPath());
            } else {
                super.doFilter(request, response, chain);
            }
        }
    }
}
