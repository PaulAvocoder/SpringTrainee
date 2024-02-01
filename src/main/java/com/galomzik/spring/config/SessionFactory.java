package com.galomzik.spring.config;

import com.galomzik.spring.entity.UserEntity;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;


@Service
public class SessionFactory {
    private org.hibernate.SessionFactory sessionFactory;

    @PostConstruct
    void init() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(UserEntity.class)
                .buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
