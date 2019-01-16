update eg_demand_reason_master set reasonmaster='Admission Fees for Well consturction' where 
 module=(select id from eg_module where name='BPA' 
 and parentmodule is null) and code='010' and reasonmaster='Admission Fees for Hut Or Shed';