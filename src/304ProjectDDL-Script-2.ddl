DROP TABLE Appointments CASCADE CONSTRAINTS ;

DROP TABLE Contacts CASCADE CONSTRAINTS ;

DROP TABLE InsuranceProviders CASCADE CONSTRAINTS ;

DROP TABLE Medications CASCADE CONSTRAINTS ;

DROP TABLE Patients CASCADE CONSTRAINTS ;

DROP TABLE Patients_Prescriptions CASCADE CONSTRAINTS ;

DROP TABLE Prescriptions CASCADE CONSTRAINTS ;

DROP TABLE Roles CASCADE CONSTRAINTS ;

DROP TABLE Users CASCADE CONSTRAINTS ;

CREATE TABLE Appointments
  (
    "Date"        DATE NOT NULL ,
    TIME          DATE NOT NULL ,
    AppointmentID INTEGER NOT NULL ,
    RoomNumber    INTEGER ,
    Reason        VARCHAR2 (4000) ,
    FK_PatientID  INTEGER NOT NULL ,
    FK_UserID     INTEGER NOT NULL ,
    FK_DoctorID   INTEGER NOT NULL
  ) ;
ALTER TABLE Appointments ADD CONSTRAINT AppointmentID PRIMARY KEY ( AppointmentID ) ;


CREATE TABLE Contacts
  (
    ContactID              INTEGER NOT NULL ,
    FirstName              VARCHAR2 (50) NOT NULL ,
    LastName               VARCHAR2 (50) NOT NULL ,
    ALIAS                  VARCHAR2 (50) ,
    MiddleName             VARCHAR2 (50) ,
    AptHouseNumber         NUMBER NOT NULL ,
    Street                 VARCHAR2 (50) NOT NULL ,
    City                   VARCHAR2 (50) NOT NULL ,
    PostaleCode            VARCHAR2 (10) ,
    Province               VARCHAR2 (3) ,
    PhoneNumber            NUMBER ,
    Notes                  VARCHAR2 (200) ,
    FK_PatientID           INTEGER NOT NULL ,
    FK_InsuranceProviderID INTEGER NOT NULL
  ) ;
ALTER TABLE Contacts ADD CONSTRAINT ContactID PRIMARY KEY ( ContactID ) ;


CREATE TABLE InsuranceProviders
  (
    InsuranceProviderID   INTEGER NOT NULL ,
    InsuranceProviderName VARCHAR2 (50 CHAR) NOT NULL ,
    PrivateOrPublic       CHAR (1) NOT NULL ,
    FK_PatientID          INTEGER NOT NULL
  ) ;
ALTER TABLE InsuranceProviders ADD CONSTRAINT InsuranceProviderID PRIMARY KEY ( InsuranceProviderID ) ;


CREATE TABLE Medications
  (
    MedicationName VARCHAR2 (50) NOT NULL ,
    MedicationID   INTEGER NOT NULL
  ) ;
ALTER TABLE Medications ADD CONSTRAINT MedicationID PRIMARY KEY ( MedicationID ) ;


CREATE TABLE Patients
  (
    PatientID    INTEGER NOT NULL ,
    FirstName    VARCHAR2 (50 CHAR) NOT NULL ,
    LastName     VARCHAR2 (50 CHAR) NOT NULL ,
    Gender       VARCHAR2 (50) NOT NULL ,
    FK_ContactID INTEGER NOT NULL
  ) ;
ALTER TABLE Patients ADD CONSTRAINT PatientID PRIMARY KEY ( PatientID ) ;


CREATE TABLE Patients_Prescriptions
  (
    FK_PatientID        INTEGER NOT NULL ,
    FK_PrescriptionID   INTEGER NOT NULL ,
    PatientMedicationID INTEGER NOT NULL
  ) ;
ALTER TABLE Patients_Prescriptions ADD CONSTRAINT PatientPrescriptionD PRIMARY KEY ( PatientMedicationID ) ;


CREATE TABLE Prescriptions
  (
    PrescriptionID  INTEGER NOT NULL ,
    Doseage         INTEGER NOT NULL ,
    MedStartDate    DATE NOT NULL ,
    MedEndDate      DATE NOT NULL ,
    DailyDoses      INTEGER NOT NULL ,
    GenericOK       CHAR (1) NOT NULL ,
    FK_MedicationID INTEGER NOT NULL
  ) ;
ALTER TABLE Prescriptions ADD CONSTRAINT PrescriptionID PRIMARY KEY ( PrescriptionID ) ;


CREATE TABLE Roles
  (
    RoleID   INTEGER NOT NULL ,
    RoleName VARCHAR2 (50) NOT NULL
  ) ;
ALTER TABLE Roles ADD CONSTRAINT RoleID PRIMARY KEY ( RoleID ) ;


CREATE TABLE Users
  (
    UserID    INTEGER NOT NULL ,
    UserName  VARCHAR2 (50) NOT NULL ,
    Password  VARCHAR2 (12) NOT NULL ,
    FK_RoleID INTEGER NOT NULL
  ) ;
ALTER TABLE Users ADD CONSTRAINT UserID PRIMARY KEY ( UserID ) ;


ALTER TABLE Appointments ADD CONSTRAINT Appointment_Patient_FK FOREIGN KEY ( FK_PatientID ) REFERENCES Patients ( PatientID ) ;

ALTER TABLE Appointments ADD CONSTRAINT Appointment_User_FK FOREIGN KEY ( FK_UserID ) REFERENCES Users ( UserID ) ON
DELETE CASCADE ;

ALTER TABLE Contacts ADD CONSTRAINT Contact_InsuranceProvider_FK FOREIGN KEY ( FK_InsuranceProviderID ) REFERENCES InsuranceProviders ( InsuranceProviderID ) ;

ALTER TABLE Contacts ADD CONSTRAINT Contact_Patient_FK FOREIGN KEY ( FK_PatientID ) REFERENCES Patients ( PatientID ) ON
DELETE CASCADE ;

ALTER TABLE Prescriptions ADD CONSTRAINT FK_MedicationID FOREIGN KEY ( FK_MedicationID ) REFERENCES Medications ( MedicationID ) ON
DELETE CASCADE ;

ALTER TABLE Patients_Prescriptions ADD CONSTRAINT FK_PatientID FOREIGN KEY ( FK_PatientID ) REFERENCES Patients ( PatientID ) ON
DELETE CASCADE ;

ALTER TABLE Patients_Prescriptions ADD CONSTRAINT FK_PrescriptionID FOREIGN KEY ( FK_PrescriptionID ) REFERENCES Prescriptions ( PrescriptionID ) ;

ALTER TABLE InsuranceProviders ADD CONSTRAINT InsuranceProvider_Patient_FK FOREIGN KEY ( FK_PatientID ) REFERENCES Patients ( PatientID ) ;

ALTER TABLE Patients ADD CONSTRAINT Patient_Contact_FK FOREIGN KEY ( FK_ContactID ) REFERENCES Contacts ( ContactID ) ON
DELETE CASCADE ;

ALTER TABLE Users ADD CONSTRAINT User_Roles_FK FOREIGN KEY ( FK_RoleID ) REFERENCES Roles ( RoleID ) ON
DELETE CASCADE ;

CREATE SEQUENCE Appointments_AppointmentID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Appointments_AppointmentID_TRG BEFORE
  INSERT ON Appointments FOR EACH ROW WHEN (NEW.AppointmentID IS NULL) BEGIN
  SELECT Appointments_AppointmentID_SEQ.NEXTVAL
  INTO :NEW.AppointmentID
  FROM DUAL;
END;
/

CREATE SEQUENCE Contacts_ContactID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Contacts_ContactID_TRG BEFORE
  INSERT ON Contacts FOR EACH ROW WHEN (NEW.ContactID IS NULL) BEGIN
  SELECT Contacts_ContactID_SEQ.NEXTVAL INTO :NEW.ContactID FROM DUAL;
END;
/

CREATE SEQUENCE InsuranceProviders_InsurancePr START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER InsuranceProviders_InsurancePr BEFORE
  INSERT ON InsuranceProviders FOR EACH ROW WHEN (NEW.InsuranceProviderID IS NULL) BEGIN
  SELECT InsuranceProviders_InsurancePr.NEXTVAL
  INTO :NEW.InsuranceProviderID
  FROM DUAL;
END;
/

CREATE SEQUENCE Medications_MedicationID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Medications_MedicationID_TRG BEFORE
  INSERT ON Medications FOR EACH ROW WHEN (NEW.MedicationID IS NULL) BEGIN
  SELECT Medications_MedicationID_SEQ.NEXTVAL INTO :NEW.MedicationID FROM DUAL;
END;
/

CREATE SEQUENCE Patients_PatientID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Patients_PatientID_TRG BEFORE
  INSERT ON Patients FOR EACH ROW WHEN (NEW.PatientID IS NULL) BEGIN
  SELECT Patients_PatientID_SEQ.NEXTVAL INTO :NEW.PatientID FROM DUAL;
END;
/

CREATE SEQUENCE Patients_Prescriptions_Patient START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Patients_Prescriptions_Patient BEFORE
  INSERT ON Patients_Prescriptions FOR EACH ROW WHEN (NEW.PatientMedicationID IS NULL) BEGIN
  SELECT Patients_Prescriptions_Patient.NEXTVAL
  INTO :NEW.PatientMedicationID
  FROM DUAL;
END;
/

CREATE SEQUENCE Prescriptions_PrescriptionID START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Prescriptions_PrescriptionID BEFORE
  INSERT ON Prescriptions FOR EACH ROW WHEN (NEW.PrescriptionID IS NULL) BEGIN
  SELECT Prescriptions_PrescriptionID.NEXTVAL
  INTO :NEW.PrescriptionID
  FROM DUAL;
END;
/

CREATE SEQUENCE Roles_RoleID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Roles_RoleID_TRG BEFORE
  INSERT ON Roles FOR EACH ROW WHEN (NEW.RoleID IS NULL) BEGIN
  SELECT Roles_RoleID_SEQ.NEXTVAL INTO :NEW.RoleID FROM DUAL;
END;
/

CREATE SEQUENCE Users_UserID_SEQ START WITH 1 NOCACHE ORDER ;
CREATE OR REPLACE TRIGGER Users_UserID_TRG BEFORE
  INSERT ON Users FOR EACH ROW WHEN (NEW.UserID IS NULL) BEGIN
  SELECT Users_UserID_SEQ.NEXTVAL INTO :NEW.UserID FROM DUAL;
END;
/

