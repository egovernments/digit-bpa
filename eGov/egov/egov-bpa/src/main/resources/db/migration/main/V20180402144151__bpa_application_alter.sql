alter table egbpa_application alter column occupancy type  bigint USING occupancy::bigint;
ALTER TABLE egbpa_application
	    ADD CONSTRAINT fk_egbpa_application_occupancy FOREIGN KEY (occupancy) REFERENCES eg_occupancy (id);
    
