	ALTER TABLE egbpa_application_floordetail DROP CONSTRAINT fk_egbpa_application_floordetail_oocupancy;

	ALTER TABLE egbpa_application_floordetail
	    ADD CONSTRAINT fk_egbpa_application_floordetail_occupancy FOREIGN KEY (occupancy) REFERENCES eg_occupancy (id);
    
    
    ALTER TABLE egbpa_existing_building_floordetail DROP CONSTRAINT fk_egbpa_application_floordetail_oocupancy;
    
    ALTER TABLE  egbpa_existing_building_floordetail 
    ADD CONSTRAINT fk_egbpa_application_floordetail_oocupancy FOREIGN KEY (occupancy) REFERENCES eg_occupancy (id);


    drop table if exists egbpa_mstr_occupancy;
    drop sequence if exists seq_egbpa_mstr_occupancy;
