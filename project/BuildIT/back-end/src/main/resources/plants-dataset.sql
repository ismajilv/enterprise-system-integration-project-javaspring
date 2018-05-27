--insert into supplier (id, name)
--    values (1, 'LocalRentIt');

insert into construction_site (id, address)
    values (10, 'Buildit address');

insert into plant_inventory_entry (href, external_id, name, supplier_id)
    values ('http://localhost:8090/api/plants/3', 3, 'Mini excavator', 1);

insert into employee (id, role, first_name, last_name)
    values (10, 'SITE_ENGINEER', 'Lolo', 'Momo');

    --INSERT INTO plant_inventory_entry (href, external_id, name, supplier_id)
--SELECT 'http://localhost:8090/api/plants/3', 3, 'Mini excavator', id FROM supplier
--WHERE name='LocalRentIt';


