alter table egp_portalservice rename ordernumber to moduleorder;

alter table egp_portalservice add column serviceorder bigint;

update egp_portalservice set moduleorder = 1 where module in (Select id from eg_module where name='Digit DCR');
update egp_portalservice set moduleorder = 2 where module in (Select id from eg_module where name='BPA');

update egp_portalservice set serviceorder=1 where name='New Building Plan Scrutiny';
update egp_portalservice set serviceorder=2 where name='Resubmit Building Plan Scrutiny';
update egp_portalservice set serviceorder=3 where name='New Occupancy Certificate Plan Scrutiny';
update egp_portalservice set serviceorder=4 where name='Resubmit Occupancy Certificate Plan Scrutiny';
update egp_portalservice set serviceorder=1 where name='New Construction';
update egp_portalservice set serviceorder=2 where name='Demolition';
update egp_portalservice set serviceorder=3 where name='Reconstruction';
update egp_portalservice set serviceorder=4 where name='Alteration';
update egp_portalservice set serviceorder=5 where name='Addition or Extension';
update egp_portalservice set serviceorder=6 where name='Change in Occupancy';
update egp_portalservice set serviceorder=7 where name='Sub-Division of plot/Land Development';
update egp_portalservice set serviceorder=8 where name='Amenities (Well,Compound Wall,Roof cov..etc)';
update egp_portalservice set serviceorder=9 where name='Permission for Temporary hut or shed';
update egp_portalservice set serviceorder=10 where name='Tower Construction';
update egp_portalservice set serviceorder=11 where name='Pole Structures';
