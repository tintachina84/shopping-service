insert
into
    member
(reg_time, update_time, created_by, modified_by, address, email, name, password, role, id)
values
    (CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'anonymousUser', 'anonymousUser', '서울시 강남구 대치동', 'admin@admin.com', '관리자', '$2a$10$wF4gGpuUpJBIETUSFy8Xw.XOATJV.5gWzZ2Bv9Kn/SNRxiUz5IWdi', 'ADMIN', 1);
