package seeder;

import entity.Role;
import entity.User;
import org.springframework.data.repository.CrudRepository;
import repository.RoleRepository;
import repository.UserRepository;

public class UserRoleSeeder extends Seeder {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    UserRoleSeeder(RoleRepository roleRepository,
                   UserRepository userRepository) {
        super(roleRepository, "user_roles");
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    void seedJob() {
        Role admin = null;
        Role warehouseManager = null;
        for(Role r: roleRepository.findAll()) {
            if(Role.ADMIN.equals(r.getName())) {
                admin = r;
            } else if(Role.WAREHOUSE_MANAGER.equals(r.getName())) {
                warehouseManager = r;
            }
            if (admin != null & warehouseManager != null) {
                break;
            }
        }
        int count = 0;
        for(User u: userRepository.findAll()){
            count++;
            if(count == 1) {
                u.getRoles().add(admin);
            } else {
                u.getRoles().add(warehouseManager);
            }
            userRepository.save(u);
        }
    }
}
