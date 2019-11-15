
INSERT INTO egeis_desig_rolemapping (designationid, roleid)
SELECT designationid, roleid FROM temp_egeis_desig_rolemapping;

DROP TABLE temp_egeis_desig_rolemapping;

ALTER TABLE  egeis_desig_rolemapping ADD CONSTRAINT role_fk FOREIGN KEY (roleid) REFERENCES state.eg_role(id);