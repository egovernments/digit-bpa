update egbpa_master_nocconfiguration set applicationtype='Permit' where applicationtype='Permit ';

insert into egbpa_master_nocconfiguration values(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'AAI_NOC','OC','MANUAL','MANUAL',15);
insert into egbpa_master_nocconfiguration values(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'NMA_NOC','OC','MANUAL','MANUAL',15);
insert into egbpa_master_nocconfiguration values(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'IDA_NOC','OC','MANUAL','MANUAL',15);
insert into egbpa_master_nocconfiguration values(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'FIRE_NOC','OC','MANUAL','MANUAL',15);
insert into egbpa_master_nocconfiguration values(nextval('SEQ_EGBPA_MSTR_NOCCONFIG'),'MOEF_NOC','OC','MANUAL','MANUAL',15);
