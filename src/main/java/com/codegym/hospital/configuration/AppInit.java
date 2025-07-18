package com.codegym.hospital.configuration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;


    public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

        @Override
        protected Class<?>[] getRootConfigClasses() {
            return new Class[] {};
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class[] { AppConfig.class };
        }

        @Override
        protected String[] getServletMappings() {
            return new String[] { "/" };
        }

        // Thêm filter encoding UTF-8
        @Override
        protected Filter[] getServletFilters() {
            CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
            encodingFilter.setEncoding("UTF-8");
            encodingFilter.setForceEncoding(true);
            return new Filter[]{encodingFilter};
        }
    }


