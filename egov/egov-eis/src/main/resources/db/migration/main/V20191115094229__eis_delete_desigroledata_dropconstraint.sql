
CREATE TABLE IF NOT EXISTS temp_egeis_desig_rolemapping(
designationid bigint,
roleid bigint
);

INSERT INTO temp_egeis_desig_rolemapping (designationid, roleid)
SELECT designationid, roleid FROM egeis_desig_rolemapping GROUP BY designationid, roleid;

DELETE FROM egeis_desig_rolemapping;

ALTER TABLE egeis_desig_rolemapping DROP CONSTRAINT role_fk ;