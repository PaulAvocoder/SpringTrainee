package com.galomzik.spring.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * Данный метод отвечает за нашу конфигурацию контекста
     * @see SpringConfig
     * @return массив классов, в которых лежат наши конфигурации
     */

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * Данный метод отвечает за точки "вхождения" эндпоинтов
     * Тут просто указываем /, чтобы любой запрос проходил через это приложение
     * @return массив String, который указывает, какие запросы обрабатывать
     */

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}