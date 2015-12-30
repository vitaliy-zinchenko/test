package zinjvi;

import javax.persistence.EntityManager;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        EntityManager manager = HibernateUtil.getEm();

        Reader reader = new Reader();
        reader.setName("n");
        manager.getTransaction().begin();
        manager.persist(reader);
        manager.getTransaction().commit();

        manager = HibernateUtil.getEm();
        Reader reader1 = manager.find(Reader.class, 1L);
        System.out.println(reader1);

        manager.close();
    }
}
