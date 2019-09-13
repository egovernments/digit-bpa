update EGP_PORTALSERVICE set isactive = true where name = 'New Occupancy Certificate Plan Scrutiny' and module = (select id from eg_module where name='Digit DCR');

update EGP_PORTALSERVICE set isactive = true where name = 'Resubmit Occupancy Certificate Plan Scrutiny' and module = (select id from eg_module where name='Digit DCR');

update EGP_PORTALSERVICE set isactive = true where name = 'Apply For Occupancy Certificate' and module = (select id from eg_module where name='BPA');