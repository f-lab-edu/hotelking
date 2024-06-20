# -- 약관동의
# INSERT INTO TERM (agreement_name, agreement_version, agreement_content, agreement_mandatory, created_at, updated_at) VALUES ('TERMS_OF_SERVICE', 1, '이용 약관 동의 내용', 1, NOW(), NOW());
# INSERT INTO TERM (agreement_name, agreement_version, agreement_content, agreement_mandatory, created_at, updated_at) VALUES ('PRIVACY_POLICY', 1, '개인정보 처리 방침 동의', 1, NOW(), NOW());
# INSERT INTO TERM (agreement_name, agreement_version, agreement_content, agreement_mandatory, created_at, updated_at) VALUES ('MARKETING', 1, '마케팅 정보 수신 동의', 0, NOW(), NOW());
# INSERT INTO TERM (agreement_name, agreement_version, agreement_content, agreement_mandatory, created_at, updated_at) VALUES ('THIRD_PARTY_SHARING',1,  '제3자 정보 제공 동의', 0, NOW(), NOW());
# INSERT INTO TERM (agreement_name, agreement_version, agreement_content, agreement_mandatory, created_at, updated_at) VALUES ('LOCATION_SERVICES',1,  '위치 정보 이용 동의', 0, NOW(), NOW());
# INSERT INTO TERM (agreement_name, agreement_version, agreement_content, agreement_mandatory, created_at, updated_at) VALUES ('NEWSLETTER',1,  '뉴스레터 수신 동의', 0, NOW(), NOW());
#
# -- 서비스 별 필수 약관
# INSERT INTO SERVICE_REQUIRED_TERM(ID, SERVICE_NAME, AGREEMENT_NAME, AGREEMENT_VERSION, CREATED_AT, UPDATED_AT) VALUES (1, 'SIGNUP', 'TERMS_OF_SERVICE', '1', NOW(), NOW());
# INSERT INTO SERVICE_REQUIRED_TERM(ID, SERVICE_NAME, AGREEMENT_NAME, AGREEMENT_VERSION, CREATED_AT, UPDATED_AT) VALUES (2, 'SIGNUP', 'PRIVACY_POLICY', '1', NOW(), NOW());