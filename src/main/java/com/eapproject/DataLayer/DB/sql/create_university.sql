CREATE TABLE UNIVERSITY (
    ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NAME VARCHAR(255) NOT NULL,
    COUNTRY VARCHAR(100) NOT NULL,
    ALPHATWOCODE VARCHAR(2),
    STATEPROVINCE VARCHAR(100),
    WEBPAGES VARCHAR(255),
    SCHOOL VARCHAR(255),
    DEPARTMENT VARCHAR(255),
    DESCRIPTION VARCHAR(1024),
    CONTACT VARCHAR(255),
    COMMENTS VARCHAR(2048),
    ISMODIFIED BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (ID)
);