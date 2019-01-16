ALTER TABLE egbpa_application_floordetail ADD COLUMN occupancy bigint; 
 
ALTER TABLE egbpa_application_floordetail ADD CONSTRAINT fk_egbpa_application_floordetail_oocupancy FOREIGN KEY (occupancy) REFERENCES egbpa_mstr_occupancy (id);