package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Anton on 2016-11-24.
 */
public class EntityManagerHelper {
    private static EntityManagerFactory emf;

    public static EntityManager createEntityManager(){
        if(emf==null){
            emf = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        }
        return emf.createEntityManager();
    }
}
