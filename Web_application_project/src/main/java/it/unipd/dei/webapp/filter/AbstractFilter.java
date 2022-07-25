package it.unipd.dei.webapp.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;

public class AbstractFilter extends HttpFilter {

    // The configuration for the filter

    private FilterConfig config = null;

    public void init(final FilterConfig config) throws ServletException {

        if (config == null) {
            throw new ServletException("Filter configuration cannot be null.");
        }
        this.config = config;

    }

    public void destroy() {

    }

}
