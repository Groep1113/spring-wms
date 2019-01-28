package seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class Seeder {
    protected CrudRepository repository;
    private String tableName;
    private static JdbcTemplate jdbcTemplate;
    Logger logger = LoggerFactory.getLogger(Seeder.class);

    Seeder(CrudRepository repository, String tableName) {
        this.repository = repository;
        this.tableName = tableName;
    }

    static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        Seeder.jdbcTemplate = jdbcTemplate;
    }

    boolean shouldSeed() {
        if(jdbcTemplate == null)
            return false;
        String sql = "SELECT COUNT(*) FROM " + tableName + ";";
        try {
            int result = jdbcTemplate.queryForObject(sql, Integer.class);
            return 0 == result;
        } catch(NullPointerException e) {
            return false;
        }
    }

    public void seed() {
        if(!shouldSeed()) {
            logger.info("Table '" + tableName + "' has already been seeded. Stopping.");
            return;
        }
        seedJob();
        logger.info("Succesfully seeded " + tableName + " table.");
    }

    abstract void seedJob();
}
