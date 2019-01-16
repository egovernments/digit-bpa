update eg_appconfig_values set value = '2' where key_id = (select id from eg_appconfig where key_name  = 'GAPFORSCHEDULING' and  module =(select id from eg_module where name = 'BPA') );
