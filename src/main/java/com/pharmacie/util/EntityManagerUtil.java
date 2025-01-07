package com.pharmacie.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pharmaciePU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}

