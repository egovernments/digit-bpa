update EG_DEMAND_REASON set GLCODEID =(select ID from CHARTOFACCOUNTS where GLCODE = '1404011')  where id_demand_reason_master in ( select id from eg_demand_reason_master 
where module=(select id from eg_module where name = 'BPA' and parentmodule is null));

update egbpa_mstr_bpafee set glcode=(select ID from CHARTOFACCOUNTS where GLCODE = '1404011');

	



