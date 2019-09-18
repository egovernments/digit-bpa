UPDATE EGP_PORTALSERVICE set isactive=true where code='Apply For Occupancy Certificate';
UPDATE EGP_PORTALSERVICE set isactive=true where code='New Occupancy Certificate Plan Scrutiny';
UPDATE EGP_PORTALSERVICE set isactive=true where code='Resubmit Occupancy Certificate Plan Scrutiny';

update EGP_PORTALSERVICE set code='New Building Plan Scrutiny', name='New Building Plan Scrutiny' where url='/edcr/edcrapplication/new';

update EGP_PORTALSERVICE set code='Resubmit Building Plan Scrutiny', name='Resubmit Building Plan Scrutiny' where url='/edcr/edcrapplication/resubmit';