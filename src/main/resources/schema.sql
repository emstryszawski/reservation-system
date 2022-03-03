INSERT INTO ROLE(
    ID,
    NAME,
    HAS_OFFER_CREATE_PRIVILEGE,
    HAS_OFFER_READ_PRIVILEGE,
    HAS_OFFER_UPDATE_PRIVILEGE,
    HAS_OFFER_DELETE_PRIVILEGE,
    HAS_OFFER_UPDATE_OTHERS_OFFER_PRIVILEGE,
    HAS_OFFER_DELETE_OTHERS_OFFER_PRIVILEGE)
VALUES
    (1, 'USER',        false, true, false, false, false, false),
    (2, 'PLACE_OWNER', true,  true, true,  true,  false, false),
    (3, 'SYS_ADMIN',   true,  true, true,  true,  true,  true);
INSERT INTO FEATURE(
    ID,
    NAME,
    DESCRIPTION
)   VALUES
    (1,'Testowy 1','Opis 1'),
    (2,'Testowy 2','Opis 2'),
    (3,'Testowy 3','Opis 3');