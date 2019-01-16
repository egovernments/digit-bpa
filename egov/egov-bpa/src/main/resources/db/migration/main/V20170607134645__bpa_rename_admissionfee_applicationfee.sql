update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees For New Construction' where code='001';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Demolition' where code='002';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Reconstruction' where code='003';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Alteration' where code='004';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Land development' where code='005';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for adding extension' where code='006';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for change in occupancy' where code='007';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Amenities' where code='008';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Hut Or Shed' where code='009';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Well consturction' where code='010';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for compound wall' where code='011';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Shutter or Door conversion' where code='012';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Roof conversion' where code='013';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Tower Construction' where code='014';
update egbpa_mstr_bpafee set feetype='ApplicationFee',description='Application Fees for Pole Structure' where code='015';

-- update demand reason master
update eg_demand_reason_master set reasonmaster='Application Fees for Pole Structure'  where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='015';
update eg_demand_reason_master set reasonmaster='Application Fees for Tower Construction' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='014';
update eg_demand_reason_master set reasonmaster='Application Fees for Roof conversion' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='013';
update eg_demand_reason_master set reasonmaster='Application Fees for Shutter or Door conversion' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='012';
update eg_demand_reason_master set reasonmaster='Application Fees for compound wall' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='011';
update eg_demand_reason_master set reasonmaster='Application Fees for Well consturction' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='010';
update eg_demand_reason_master set reasonmaster='Application Fees for Hut Or Shed' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='009';
update eg_demand_reason_master set reasonmaster='Application Fees for Amenities' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='008';
update eg_demand_reason_master set reasonmaster='Application Fees for change in occupancy' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='007';
update eg_demand_reason_master set reasonmaster='Application Fees for adding extension' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='006';
update eg_demand_reason_master set reasonmaster='Application Fees for Land development' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='005';
update eg_demand_reason_master set reasonmaster='Application Fees for Alteration' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='004';
update eg_demand_reason_master set reasonmaster='Application Fees for Reconstruction' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='003';
update eg_demand_reason_master set reasonmaster='Application Fees for Demolition' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='002';
update eg_demand_reason_master set reasonmaster='Application Fees For New Construction' where  module=(select id from eg_module where name='BPA'  and parentmodule is null) and code='001';

