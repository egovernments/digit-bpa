
INSERT INTO state.EG_USERROLE (userid, roleid)
SELECT userid,roleid FROM state.TEMP_EG_USERROLE;

DROP TABLE state.TEMP_EG_USERROLE;

ALTER TABLE state.eg_userrole ADD CONSTRAINT fk_userrole_role_id FOREIGN KEY (roleid) REFERENCES state.eg_role(id);