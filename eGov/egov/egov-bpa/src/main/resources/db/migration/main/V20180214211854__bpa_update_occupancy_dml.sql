update egbpa_mstr_occupancy set permissibleareainpercentage =60, numoftimesareapermissible=2.5, numoftimesareapermwitaddnlfee=3.5 where code='04';
update egbpa_mstr_occupancy set permissibleareainpercentage =70, numoftimesareapermissible=3, numoftimesareapermwitaddnlfee=4 where code='06';
update egbpa_mstr_occupancy set permissibleareainpercentage =70, numoftimesareapermissible=3, numoftimesareapermwitaddnlfee=4 where code='07';
update egbpa_mstr_occupancy set permissibleareainpercentage =65, numoftimesareapermissible=2.5 where code='08';
update egbpa_mstr_occupancy set permissibleareainpercentage =75, numoftimesareapermissible=3.5, numoftimesareapermwitaddnlfee=4 where code='09';
update egbpa_mstr_occupancy set permissibleareainpercentage =80, numoftimesareapermissible=3, numoftimesareapermwitaddnlfee=4 where code='10';
update egbpa_mstr_occupancy set permissibleareainpercentage =45, numoftimesareapermissible=2 where code='11';
update egbpa_mstr_occupancy set permissibleareainpercentage =40, numoftimesareapermissible=1.5 where code='12';

ALTER TABLE egbpa_noc_document ALTER COLUMN remarks TYPE character varying(1000);
ALTER TABLE egbpa_noc_document ALTER COLUMN natureofrequest TYPE character varying(1000);
ALTER TABLE egbpa_inspection ALTER COLUMN inspectionremarks TYPE character varying(1000);
alter table egbpa_application add column istownsurveyorinspectionrequire boolean default false;