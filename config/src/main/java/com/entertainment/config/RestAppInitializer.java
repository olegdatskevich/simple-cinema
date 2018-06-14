package com.entertainment.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class RestAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) throws ServletException {
//        AnnotationConfigWebApplicationContext restContext = new AnnotationConfigWebApplicationContext();
        XmlWebApplicationContext restContext = new XmlWebApplicationContext();
        restContext.setConfigLocation("WEB-INF/rest-spring.xml");
//        restContext.register(RestAppConfiguration.class);

        container.addListener(new ContextLoaderListener(restContext));

        ServletRegistration.Dynamic restDispatcherServlet
                = container.addServlet("restDispatcherServlet", new DispatcherServlet(restContext));

        restDispatcherServlet.setLoadOnStartup(1);
        restDispatcherServlet.addMapping("/");
    }
}
