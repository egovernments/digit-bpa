

delete from eg_roleaction where actionid in (select id from eg_action where name in ('Upload dxf','Get dcr details'));
delete from eg_action where name in ('Upload dxf','Get dcr details');