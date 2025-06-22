package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import entity.Book;

import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration cfg = new Configuration();
                Properties settings = new Properties();

                // 1) driver + URL
                settings.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                settings.put(Environment.URL, 
                    "jdbc:sqlserver://localhost:1433;databaseName=demo;encrypt=true;trustServerCertificate=true");
                settings.put(Environment.USER, "sa");
                settings.put(Environment.PASS, "yourPassword");

                // 2) dialect
                settings.put(Environment.DIALECT, "org.hibernate.dialect.SQLServerDialect");

                // 3) hiển thị SQL / schema auto
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                
                // 4) (tùy chọn) tắt metadata lookup nếu bạn gặp lỗi liên quan đến sql_variant
                settings.put("hibernate.temp.use_jdbc_metadata_defaults", "false");

                cfg.setProperties(settings);
                cfg.addAnnotatedClass(Book.class);

                ServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties()).build();

                sessionFactory = cfg.buildSessionFactory(registry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) sessionFactory.close();
    }
}
