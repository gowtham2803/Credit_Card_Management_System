package com.ccms.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@WebFilter("/*")
public class LoggingFilter implements Filter {

    private static final Logger logger =
            Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String url = req.getRequestURI();
        String method = req.getMethod();
        String ip = request.getRemoteAddr();
        LocalDateTime time = LocalDateTime.now();

        long startTime = System.currentTimeMillis();

        logger.info("Incoming Request → "
                + "Method: " + method
                + " | URL: " + url
                + " | IP: " + ip
                + " | Time: " + time);

        chain.doFilter(request, response);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("Completed Request → "
                + "URL: " + url
                + " | Execution Time: " + executionTime + " ms");
    }
}