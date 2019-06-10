update eg_checklist set code='AAI_OCNOC' where code='OCNOC-01';
update eg_checklist set code='NMA_OCNOC' where code='OCNOC-02';
update eg_checklist set code='IDA_OCNOC' where code='OCNOC-03';
update eg_checklist set code='FIRE_OCNOC' where code='OCNOC-04';
update eg_checklist set code='MOEF_OCNOC' where code='OCNOC-05';

-------------------------------------------------------------------------------------------

update egbpa_master_nocconfiguration SET department='AAI_OCNOC' where applicationtype = 'OC' and department='AAI_NOC';
update egbpa_master_nocconfiguration SET department='NMA_OCNOC' where applicationtype = 'OC' and department='NMA_NOC';
update egbpa_master_nocconfiguration SET department='IDA_OCNOC' where applicationtype = 'OC' and department='IDA_NOC';
update egbpa_master_nocconfiguration SET department='FIRE_OCNOC' where applicationtype = 'OC' and department='FIRE_NOC';
update egbpa_master_nocconfiguration SET department='MOEF_OCNOC' where applicationtype = 'OC' and department='MOEF_NOC';
