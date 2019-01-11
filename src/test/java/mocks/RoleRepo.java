package mocks;

import entity.Role;
import repository.RoleRepository;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoleRepo implements RoleRepository {
    private HashMap<Integer, Role> rolesWithId = new HashMap<>();

    public RoleRepo() {
        Role adminRole = new Role();
        adminRole.setName("admin");
        adminRole.setId(1);
        Role salesRole = new Role();
        salesRole.setName("inkoop");
        salesRole.setId(2);
        rolesWithId.put(1, adminRole);
        rolesWithId.put(2, salesRole);
    }

    @Override
    public <S extends Role> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Role> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Role> findById(Integer id) {
        if (!rolesWithId.containsKey(id)) return Optional.empty();
        return Optional.of(rolesWithId.get(id));
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Role> findAll() {
        return rolesWithId.values().parallelStream().collect(Collectors.toList());
    }

    @Override
    public Iterable<Role> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Role entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Role> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
