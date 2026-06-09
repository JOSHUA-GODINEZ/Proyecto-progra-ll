/*
Created: 10/5/2026
Modified: 9/6/2026
Model: ProyectoToadData
Database: Oracle 11g Release 1
*/


-- Create users section -------------------------------------------------

-- User User1 has empty SQL definition.

-- Create sequences section -------------------------------------------------

CREATE SEQUENCE user_userID_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
/

CREATE SEQUENCE SEC_USERS_CONFIG_LEVEL
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 CACHE 0
 ORDER
/

CREATE SEQUENCE SEC_USERS_CONFIG_GENERAL
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 NOMINVALUE
 NOCACHE
 ORDER
/

-- Create tables section -------------------------------------------------

-- Table users

CREATE TABLE users(
  user_ID Number NOT NULL,
  user_Name Varchar2(100 ) NOT NULL,
  user_Image Varchar2(255 ) NOT NULL,
  user_Version Number NOT NULL
)
/

-- Add keys for table users

ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY (user_ID)
/

ALTER TABLE users ADD CONSTRAINT user_Name UNIQUE (user_Name)
/

-- Table and Columns comments section

COMMENT ON COLUMN users.user_ID IS 'ID del usuario'
/
COMMENT ON COLUMN users.user_Name IS 'Nombre del usuario'
/
COMMENT ON COLUMN users.user_Image IS 'Imagen del usuario'
/
COMMENT ON COLUMN users.user_Version IS 'Version de datos del usuarios'
/

-- Table users_config

CREATE TABLE users_config(
  user_users Number NOT NULL,
  user_config Number NOT NULL,
  user_ConfigGeneral Number NOT NULL
)
/

-- Add keys for table users_config

ALTER TABLE users_config ADD CONSTRAINT users_config_game PRIMARY KEY (user_users,user_config,user_ConfigGeneral)
/

-- Table users_Config_Level

CREATE TABLE users_Config_Level(
  game_id Number NOT NULL,
  user_Level Number DEFAULT 1 NOT NULL,
  user_Level_GunDamage Number DEFAULT 1 NOT NULL,
  user_Level_GunSpeed Number DEFAULT 1 NOT NULL,
  user_Level_BaseHeath Number DEFAULT 1 NOT NULL,
  user_Level_CapacityElixir Number DEFAULT 1 NOT NULL,
  user_Level_SpeedElixir Number DEFAULT 1 NOT NULL,
  user_Level_PowerBombDamage Number DEFAULT 1 NOT NULL,
  user_Level_PowerBombRange Number DEFAULT 1 NOT NULL,
  user_Level_PowerFreezeDuration Number DEFAULT 1 NOT NULL,
  user_Level_PowerFreezeRange Number DEFAULT 1 NOT NULL,
  user_Points Number DEFAULT 0 NOT NULL,
  user_Level_Version Char(20 ) NOT NULL
)
/

-- Add keys for table users_Config_Level

ALTER TABLE users_Config_Level ADD CONSTRAINT PK_users_Config_Level PRIMARY KEY (game_id)
/

-- Create triggers for table users_Config_Level

CREATE OR REPLACE TRIGGER TRG_USERS_CONFIG_LEVEL
  BEFORE INSERT
  ON users_Config_Level
  CREATE OR REPLACE TRIGGER TRG_USERS_CONFIG_LEVEL
BEFORE INSERT ON USERS_CONFIG_LEVEL
FOR EACH ROW
BEGIN
    IF :NEW.GAME_ID IS NULL OR :NEW.GAME_ID = 0 THEN
        SELECT SEC_USERS_CONFIG_LEVEL.NEXTVAL INTO :NEW.GAME_ID FROM DUAL;
    END IF;
END;
/

/

-- Table and Columns comments section

COMMENT ON COLUMN users_Config_Level.user_Level IS 'Nivel mas alto de cada jugador'
/
COMMENT ON COLUMN users_Config_Level.user_Level_GunDamage IS 'Nivel de daño de la arma'
/
COMMENT ON COLUMN users_Config_Level.user_Level_GunSpeed IS 'NIvel de velocidad de ataque del arma'
/
COMMENT ON COLUMN users_Config_Level.user_Level_BaseHeath IS 'Nivel de vida de la base'
/
COMMENT ON COLUMN users_Config_Level.user_Level_CapacityElixir IS 'Nivel de capacidad de elixir'
/
COMMENT ON COLUMN users_Config_Level.user_Level_SpeedElixir IS 'Nivel de velocidad de recolecion elixir'
/
COMMENT ON COLUMN users_Config_Level.user_Level_PowerBombDamage IS 'Nivel de daño de el poder bomba'
/
COMMENT ON COLUMN users_Config_Level.user_Level_PowerBombRange IS 'Nivel de area del poder bomba'
/
COMMENT ON COLUMN users_Config_Level.user_Level_PowerFreezeDuration IS 'Nivel de duracion del poder hielo'
/
COMMENT ON COLUMN users_Config_Level.user_Level_PowerFreezeRange IS 'Nivel de rango del poder hielo'
/
COMMENT ON COLUMN users_Config_Level.user_Points IS 'Puntos del usuario'
/
COMMENT ON COLUMN users_Config_Level.user_Level_Version IS 'version de la tabla Niveles'
/

-- Table user_Config_General

CREATE TABLE user_Config_General(
  user_General_id Number NOT NULL,
  user_StyleGun Char(2 CHAR) DEFAULT A NOT NULL
        CONSTRAINT StyleGun CHECK (user_StyleGun IN ('A', 'M')),
  user_Controller Char(2 CHAR) DEFAULT M NOT NULL
        CONSTRAINT user_Controller CHECK (user_Controller IN ('M', 'K')),
  user_GameMode Char(2 CHAR) DEFAULT N NOT NULL
        CONSTRAINT user_GameModeConstrain CHECK (user_GameMode IN ('E', 'N')),
  user_AudioMenu Float DEFAULT 0.5 NOT NULL,
  user_AudioEnemys Float DEFAULT 0.5 NOT NULL,
  user_AudioEfect Float DEFAULT 0.5 NOT NULL,
  user_AudioGun Float DEFAULT 0.5 NOT NULL,
  user_Screen Char(2 CHAR) DEFAULT C NOT NULL
        CONSTRAINT user_ScreenSize CHECK (user_Screen IN ('C', 'B')),
  user_Language Char(2 CHAR) DEFAULT S NOT NULL
        CONSTRAINT user_LanguageConstrain CHECK (user_Language IN ('S', 'I')),
  user_FirstTime Char(2 CHAR) DEFAULT S NOT NULL
        CONSTRAINT user_FirstTimeConstrain CHECK (user_FirstTime IN ('S', 'N')),
  user_Version Number NOT NULL
)
/

-- Add keys for table user_Config_General

ALTER TABLE user_Config_General ADD CONSTRAINT PK_user_Config_General PRIMARY KEY (user_General_id)
/

-- Table and Columns comments section

COMMENT ON COLUMN user_Config_General.user_General_id IS 'LLave para acceder al atributo'
/
COMMENT ON COLUMN user_Config_General.user_StyleGun IS 'estilo arma A:aka-47 , M: m16'
/
COMMENT ON COLUMN user_Config_General.user_Controller IS 'Controles de juego M:raton, K: teclado'
/
COMMENT ON COLUMN user_Config_General.user_GameMode IS 'Mode de juego N: normal ,E: facil'
/
COMMENT ON COLUMN user_Config_General.user_AudioMenu IS 'Volumen de los menus'
/
COMMENT ON COLUMN user_Config_General.user_AudioEnemys IS 'Volumen de los enemigos'
/
COMMENT ON COLUMN user_Config_General.user_AudioEfect IS 'Volumen de los efectos'
/
COMMENT ON COLUMN user_Config_General.user_AudioGun IS 'Volumen del arma'
/
COMMENT ON COLUMN user_Config_General.user_Screen IS 'Tamaño de la pantalla C:completa, B: con Bordes'
/
COMMENT ON COLUMN user_Config_General.user_Language IS 'Modo de idioma S:español, I: ingles'
/
COMMENT ON COLUMN user_Config_General.user_FirstTime IS 'Cuando entra por primera vez al menu  S: Primera vez,  N: no es la primera vez '
/
COMMENT ON COLUMN user_Config_General.user_Version IS 'version de la taba'
/

-- Trigger for sequence user_userID_seq01 for column user_ID in table users ---------
CREATE OR REPLACE TRIGGER ts_users_user_userID_seq01 BEFORE INSERT
ON users FOR EACH ROW
BEGIN
  :new.user_ID := user_userID_seq01.nextval;
END;
/
CREATE OR REPLACE TRIGGER tsu_users_user_userID_seq01 AFTER UPDATE OF user_ID
ON users FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column user_ID in table users as it uses sequence.');
END;
/

-- Trigger for sequence SEC_USERS_CONFIG_GENERAL for column game_id in table users_Config_Level ---------
CREATE OR REPLACE TRIGGER ts_users_Config_Level_SEC_US_0 BEFORE INSERT
ON users_Config_Level FOR EACH ROW
BEGIN
  :new.game_id := SEC_USERS_CONFIG_GENERAL.nextval;
END;
/
CREATE OR REPLACE TRIGGER tsu_users_Config_Level_SEC_U_0 AFTER UPDATE OF game_id
ON users_Config_Level FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column game_id in table users_Config_Level as it uses sequence.');
END;
/

-- Trigger for sequence SEC_USERS_CONFIG_GENERAL for column user_General_id in table user_Config_General ---------
CREATE OR REPLACE TRIGGER ts_user_Config_General_SEC_U_0 BEFORE INSERT
ON user_Config_General FOR EACH ROW
BEGIN
  :new.user_General_id := SEC_USERS_CONFIG_GENERAL.nextval;
END;
/
CREATE OR REPLACE TRIGGER tsu_user_Config_General_SEC__0 AFTER UPDATE OF user_General_id
ON user_Config_General FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column user_General_id in table user_Config_General as it uses sequence.');
END;
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE users_config ADD CONSTRAINT User_to_usersConfig FOREIGN KEY (user_users) REFERENCES users (user_ID)
/



ALTER TABLE users_config ADD CONSTRAINT usersConfig_to_ConfigLevel FOREIGN KEY (user_config) REFERENCES users_Config_Level (game_id)
/



ALTER TABLE users_config ADD CONSTRAINT usersConfig_to_ConfigGeneral FOREIGN KEY (user_ConfigGeneral) REFERENCES user_Config_General (user_General_id)
/




-- Grant permissions section -------------------------------------------------


