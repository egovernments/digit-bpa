CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
update eg_user set uid = uuid_generate_v4() where uid is null;