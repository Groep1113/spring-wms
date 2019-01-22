package seeder;

import com.google.common.collect.Lists;
import entity.Supplier;
import entity.Item;
import repository.SupplierRepository;
import repository.ItemRepository;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class SupplierItemSeeder extends Seeder {
    private final ItemRepository itemRepository;
    private final SupplierRepository supplierRepository;

    SupplierItemSeeder(SupplierRepository supplierRepository, ItemRepository itemRepository) {
        super(supplierRepository, "supplier_items");
        this.supplierRepository = supplierRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    void seedJob() {
        Iterable<Supplier> supplierIterable = supplierRepository.findAll();
        Iterable<Item> items = itemRepository.findAll();
        ArrayList<Supplier> suppliers = Lists.newArrayList(supplierIterable);
        Random rand = new Random();
        for(Item i: items) {
            System.out.println("++++");
            Supplier supplier = suppliers.get(rand.nextInt(suppliers.size()));
            i.setSupplier(supplier);
            itemRepository.save(i);
        }
    }
}
