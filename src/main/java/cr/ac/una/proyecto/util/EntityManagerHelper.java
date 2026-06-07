package cr.ac.una.proyecto.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerHelper {

    private static final EntityManagerHelper SINGLENTON = new EntityManagerHelper();
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private static final String PU_NAME = "UNAPU"; 

 static {
    try {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
        } 
        emf = Persistence.createEntityManagerFactory(PU_NAME);
        em = emf.createEntityManager();

    } catch (Throwable t) {  
        t.printStackTrace();
    }
}
    
    private EntityManagerHelper() {
    }

    public static EntityManagerHelper getInstance() {
        return SINGLENTON;
    }

    public static EntityManager getManager() {
        if (em == null || !em.isOpen()) {
            try {
                if (emf == null || !emf.isOpen()) {
                    emf = Persistence.createEntityManagerFactory(PU_NAME);
                }
                em = emf.createEntityManager();
            } catch (Exception e) {
            }
        }
        return em;
    }
}