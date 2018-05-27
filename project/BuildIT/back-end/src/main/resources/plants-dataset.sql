insert into construction_site (id, address)
    values (10, 'Buildit address');
insert into supplier (id, name)
    values (10, 'RentIt');
insert into plant_inventory_entry (href, external_id, name, supplier_id)
    values ('http://localhost:8090/api/plants/3', 3, 'Mini excavator', 10);
insert into employee (id, role, first_name, last_name)
    values (10, 'SITE_ENGINEER', 'Lolo', 'Momo')

