
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-1' ) where boundarytype in (select id from eg_boundary where name = 'Revenue Ward No 1' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-2' ) where boundarytype in (select id from eg_boundary where name = 'Revenue Ward No 2' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-3' ) where boundarytype in (select id from eg_boundary where name = 'Revenue Ward No 3' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-4' ) where boundarytype in (select id from eg_boundary where name = 'Revenue Ward No 4' );
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-1' ) where boundarytype in (select id from eg_boundary where name = 'Revenue');
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-2' ) where boundarytype in (select id from eg_boundary where name = 'Revenue Ward: 6');
update eg_boundary set parent = (select id from eg_boundary where name = 'Zone-3' ) where boundarytype in (select id from eg_boundary where name = 'Gandhi Road');