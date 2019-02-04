update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-1' ) where id in (select id from eg_boundary where name = 'Revenue Ward No 1' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-2' ) where id in (select id from eg_boundary where name = 'Revenue Ward No 2' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-3' ) where id in (select id from eg_boundary where name = 'Revenue Ward No 3' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-4' ) where id in (select id from eg_boundary where name = 'Revenue Ward No 4' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-1' ) where id in (select id from eg_boundary where name = 'Revenue');
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-2' ) where id in (select id from eg_boundary where name = 'Revenue Ward: 6');
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-3' ) where id in (select id from eg_boundary where name = 'Gandhi Road');


 update eg_boundary set parent = (select id from eg_boundary where boundarytype  in ( select id from eg_boundary_type where hierarchytype  =(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION') and name='City'))  where boundarytype  in ( select id from eg_boundary_type where hierarchytype  =(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION') and name='Ward');  
 update eg_boundary set parent = (select id from eg_boundary where boundarytype  in ( select id from eg_boundary_type where hierarchytype =(select id from eg_hierarchy_type where upper(name)='LOCATION') and name='City'))   where boundarytype  in ( select id from eg_boundary_type where hierarchytype  =(select id from eg_hierarchy_type where upper(name)='LOCATION') and name='Locality');  
 update eg_boundary set parent =null  where boundarytype  in ( select id from eg_boundary_type where hierarchytype =(select id from eg_hierarchy_type where upper(name)='LOCATION') and name='City');
 update eg_boundary set parent =null where boundarytype  in ( select id from eg_boundary_type where hierarchytype =(select id from eg_hierarchy_type where upper(name)='ADMINISTRATION') and name='City');

