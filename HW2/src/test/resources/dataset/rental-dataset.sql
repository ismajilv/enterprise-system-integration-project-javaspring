insert into plant_inventory_entry (id, name, description, total) values (1, 'Mini excavator', '1.5 Tonne Mini excavator', 150);
insert into plant_inventory_entry (id, name, description, total) values (2, 'Mini excavator', '3 Tonne Mini excavator', 200);
insert into plant_inventory_entry (id, name, description, total) values (3, 'Midi excavator', '5 Tonne Midi excavator', 250);
insert into plant_inventory_entry (id, name, description, total) values (4, 'Midi excavator', '8 Tonne Midi excavator', 300);
insert into plant_inventory_entry (id, name, description, total) values (5, 'Maxi excavator', '15 Tonne Large excavator', 400);
insert into plant_inventory_entry (id, name, description, total) values (6, 'Maxi excavator', '20 Tonne Large excavator', 450);
insert into plant_inventory_entry (id, name, description, total) values (7, 'HS dumper', '1.5 Tonne Hi-Swivel Dumper', 150);
insert into plant_inventory_entry (id, name, description, total) values (8, 'FT dumper', '2 Tonne Front Tip Dumper', 180);
insert into plant_inventory_entry (id, name, description, total) values (9, 'FT dumper', '2 Tonne Front Tip Dumper', 200);
insert into plant_inventory_entry (id, name, description, total) values (10, 'FT dumper', '2 Tonne Front Tip Dumper', 300);
insert into plant_inventory_entry (id, name, description, total) values (11, 'FT dumper', '3 Tonne Front Tip Dumper', 400);
insert into plant_inventory_entry (id, name, description, total) values (12, 'Loader', 'Hewden Backhoe Loader', 200);
insert into plant_inventory_entry (id, name, description, total) values (13, 'D-Truck', '15 Tonne Articulating Dump Truck', 250);
insert into plant_inventory_entry (id, name, description, total) values (14, 'D-Truck', '20 Tonne Articulating Dump Truck', 280);
insert into plant_inventory_entry (id, name, description, total) values (15, 'D-Truck', '30 Tonne Articulating Dump Truck', 300);

insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (1, 10, 'A01', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (2, 12, 'A02', 'SERVICEABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (3, 15, 'A03', 'UNSERVICEABLE_REPAIRABLE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (4, 18, 'A04', 'UNSERVICEABLE_INCOMPLETE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (5, 19, 'A05', 'UNSERVICEABLE_INCOMPLETE');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (6, 6, 'A07', 'UNSERVICEABLE_CONDEMNED');
insert into plant_inventory_item (id, plant_info_id, serial_number, equipment_condition) values (7, 9, 'A08', 'UNSERVICEABLE_CONDEMNED');

insert into plant_reservation (id, plant_id, start_date, end_date) values (1, 1, '2017-03-22', '2017-03-24');
insert into plant_reservation (id, plant_id, start_date, end_date) values (2, 3, '2016-12-20', '2016-03-24');
insert into plant_reservation (id, plant_id, start_date, end_date) values (3, 1, '2015-03-09', '2015-03-24');