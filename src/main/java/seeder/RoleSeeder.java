package seeder;

import entity.Role;
import org.springframework.data.repository.CrudRepository;

public class RoleSeeder extends Seeder {
    RoleSeeder(CrudRepository repository) {
        super(repository, "role");
    }

    @Override
    void seedJob() {
        String[] roles = {Role.ADMIN, Role.WAREHOUSE_MANAGER};
        for(String roleName: roles) {
            Role role = new Role();
            role.setName(roleName);
            repository.save(role);
        }
    }
}
