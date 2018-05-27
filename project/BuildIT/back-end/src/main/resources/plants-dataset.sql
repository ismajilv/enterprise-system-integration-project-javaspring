insert into supplier (id, name)
    values (12, 'LocalRentIt');

insert into construction_site (id, address) values (10, 'Buildit address');

insert into construction_site (id, address)
    values (11, 'Buildit address 2');

insert into plant_inventory_entry (href, external_id, name, supplier_id)
    values ('http://localhost:8090/api/plants/3', 3, 'Mini excavator', 1);

insert into employee (id, role, first_name, last_name) values (10, 'SITE_ENGINEER', 'Lolo', 'Momo');

--insert into plant_hire_request (id, rental_period, rental_cost, status, plant, supplier,
-- comments, purchase_order_id, requesting_site_engineer_id, approving_works_engineer_id, approving_works_engineer, extension_request)
--    values ()