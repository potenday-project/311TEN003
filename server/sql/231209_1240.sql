# 운영 반영 완료.
ALTER TABLE bside.alcohol_type
    ADD CONSTRAINT alcohol_type_UN UNIQUE KEY (name);
