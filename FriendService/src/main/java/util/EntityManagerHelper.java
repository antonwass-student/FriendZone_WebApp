package main.java.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Anton on 2016-11-24.
 */
public class EntityManagerHelper {
    private static EntityManagerFactory emf;

    static{
        emf = Persistence.createEntityManagerFactory("persistenceUnit");
    }

    public static EntityManager createEntityManager(){
        return emf.createEntityManager();
    }
}
