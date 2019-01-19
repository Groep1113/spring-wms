package seeder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public abstract class TableSeeder {
    protected CrudRepository repository;
    private String tableName;
    private static JdbcTemplate jdbcTemplate;

    TableSeeder(CrudRepository repository, String tableName) {
        this.repository = repository;
        this.tableName = tableName;
    }

    static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        TableSeeder.jdbcTemplate = jdbcTemplate;
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
            System.out.println("Table '" + tableName + "' has already been seeded. Stopping.");
            return;
        }
        seedJob();
        System.out.println("Succesfully seeded " + tableName + " table.");
    }

    abstract void seedJob();
}
