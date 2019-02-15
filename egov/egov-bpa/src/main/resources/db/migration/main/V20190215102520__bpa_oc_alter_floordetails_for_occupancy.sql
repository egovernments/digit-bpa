alter table EGBPA_APPLICATION_FLOORDETAIL drop constraint fk_egbpa_application_floordetail_occupancy;
alter table EGBPA_APPLICATION_FLOORDETAIL rename occupancy to suboccupancy;
update EGBPA_APPLICATION_FLOORDETAIL set suboccupancy = (select id from egbpa_sub_occupancy where occupancy = suboccupancy and name = (select name from egbpa_occupancy where id=suboccupancy));
alter table EGBPA_APPLICATION_FLOORDETAIL add constraint fk_egbpa_application_floordetail_suboccupancy FOREIGN KEY (suboccupancy) REFERENCES egbpa_sub_occupancy (id);


alter table EGBPA_OC_FLOOR drop constraint fk_egbpa_oc_floor_occupancy;
alter table EGBPA_OC_FLOOR rename occupancy to suboccupancy;
update EGBPA_OC_FLOOR set suboccupancy = (select id from egbpa_sub_occupancy where occupancy = suboccupancy and name = (select name from egbpa_occupancy where id=suboccupancy));
alter table EGBPA_OC_FLOOR add constraint fk_egbpa_oc_floor_suboccupancy FOREIGN KEY (suboccupancy) REFERENCES egbpa_sub_occupancy (id);

alter table EGBPA_EXISTING_BUILDING_FLOORDETAIL drop constraint fk_egbpa_application_floordetail_oocupancy;
alter table EGBPA_EXISTING_BUILDING_FLOORDETAIL rename occupancy to suboccupancy;
update EGBPA_EXISTING_BUILDING_FLOORDETAIL set suboccupancy = (select id from egbpa_sub_occupancy where occupancy = suboccupancy and name = (select name from egbpa_occupancy where id=suboccupancy));
alter table EGBPA_EXISTING_BUILDING_FLOORDETAIL add constraint fk_egbpa_existing_application_floor_suboccupancy FOREIGN KEY (suboccupancy) REFERENCES egbpa_sub_occupancy (id);

alter table EGBPA_OC_EXISTING_FLOOR drop constraint fk_egbpa_oc_exist_bldg_floor_oocupancy;
alter table EGBPA_OC_EXISTING_FLOOR rename occupancy to suboccupancy;
update EGBPA_OC_EXISTING_FLOOR set suboccupancy = (select id from egbpa_sub_occupancy where occupancy = suboccupancy and name = (select name from egbpa_occupancy where id=suboccupancy));
alter table EGBPA_OC_EXISTING_FLOOR add constraint fk_egbpa_oc_exist_bldg_floor_suboccupancy FOREIGN KEY (suboccupancy) REFERENCES egbpa_sub_occupancy (id);
