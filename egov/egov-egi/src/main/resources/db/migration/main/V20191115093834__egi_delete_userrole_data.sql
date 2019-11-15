---- create temp userrole table 

CREATE TABLE IF NOT EXISTS state.TEMP_EG_USERROLE(
userid bigint,
roleid bigint
);

INSERT INTO state.TEMP_EG_USERROLE (userid, roleid)
SELECT userid,roleid FROM state.eg_userrole GROUP BY userid, roleid;

DELETE FROM state.eg_userrole;

ALTER TABLE state.eg_userrole DROP CONSTRAINT IF EXISTS fk_userrole_role_id ;