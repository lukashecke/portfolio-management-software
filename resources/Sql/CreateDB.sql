-- MySQL Script generated by MySQL Workbench
-- Sat Feb 19 20:50:01 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Database
-- -----------------------------------------------------
-- DROP DATABASE IF EXISTS Firestocks;
CREATE DATABASE Firestocks CHARACTER SET utf8;

USE Firestocks;

-- -----------------------------------------------------
-- Table `Portfolio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Portfolio` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Platform`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Platform` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Type` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(50) NOT NULL,
  `ShortName` VARCHAR(10) NOT NULL,
  `NeedsIncomeTax` TINYINT(1) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Info` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Type_Id` INT NOT NULL,
  `Info` VARCHAR(100) NOT NULL,
  `Description` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_Info_Type1_idx` (`Type_Id` ASC) VISIBLE,
  UNIQUE INDEX `Type_Id_UNIQUE` (`Type_Id` ASC) VISIBLE,
  CONSTRAINT `fk_Info_Type1`
    FOREIGN KEY (`Type_Id`)
    REFERENCES `Type` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Asset` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Type_Id` INT NOT NULL,
  `Name` VARCHAR(100) NOT NULL,
  `ShortName` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Assets_Type1_idx` (`Type_Id` ASC) VISIBLE,
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  CONSTRAINT `fk_Assets_Type1`
    FOREIGN KEY (`Type_Id`)
    REFERENCES `Type` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `History`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `History` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Asset_Id` INT NOT NULL,
  `TimeStamp` DATETIME NOT NULL,
  `PricePerUnit` DOUBLE NOT NULL,
  INDEX `fk_History_Asset1_idx` (`Asset_Id` ASC) VISIBLE,
  PRIMARY KEY (`Id`),
  CONSTRAINT `fk_History_Asset1`
    FOREIGN KEY (`Asset_Id`)
    REFERENCES `Asset` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Investment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Investment` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Portfolio_Id` INT NOT NULL,
  `Platform_Id` INT NOT NULL,
  `Asset_Id` INT NOT NULL,
  `History_Id` INT NOT NULL,
  `PurchasePrice` DOUBLE NOT NULL,
  `TransactionFee` DOUBLE NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `fk_Investment_Portfolio_idx` (`Portfolio_Id` ASC) VISIBLE,
  INDEX `fk_Investment_Platform1_idx` (`Platform_Id` ASC) VISIBLE,
  INDEX `fk_Investment_Assets1_idx` (`Asset_Id` ASC) VISIBLE,
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  INDEX `fk_Investment_History1_idx` (`History_Id` ASC) VISIBLE,
  CONSTRAINT `fk_Investment_Portfolio`
    FOREIGN KEY (`Portfolio_Id`)
    REFERENCES `Portfolio` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Investment_Platform1`
    FOREIGN KEY (`Platform_Id`)
    REFERENCES `Platform` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Investment_Assets1`
    FOREIGN KEY (`Asset_Id`)
    REFERENCES `Asset` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Investment_History1`
    FOREIGN KEY (`History_Id`)
    REFERENCES `History` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Config` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Label`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Label` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(15) NOT NULL,
  `Description` VARCHAR(200) NOT NULL,
  `Color` VARCHAR(7) NULL DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE INDEX `Id_UNIQUE` (`Id` ASC) VISIBLE,
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Asset_has_Label`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Asset_has_Label` (
  `Asset_Id` INT NOT NULL,
  `Label_Id` INT NOT NULL,
  PRIMARY KEY (`Asset_Id`, `Label_Id`),
  INDEX `fk_Asset_has_Label_Label1_idx` (`Label_Id` ASC) VISIBLE,
  INDEX `fk_Asset_has_Label_Asset1_idx` (`Asset_Id` ASC) VISIBLE,
  CONSTRAINT `fk_Asset_has_Label_Asset1`
    FOREIGN KEY (`Asset_Id`)
    REFERENCES `Asset` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Asset_has_Label_Label1`
    FOREIGN KEY (`Label_Id`)
    REFERENCES `Label` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Stored Procedures
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Investments
-- -----------------------------------------------------
DELIMITER //

CREATE PROCEDURE GetInvestments(
	IN portfolio_Id INT
)
BEGIN
	SELECT *
	FROM Investment
	WHERE Portfolio_Id = portfolio_Id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE CreateNewInvestment(
    IN portfolio_Id INT,
    IN asset_Id INT,
    IN platform_Id INT,
    IN purchaseDate VARCHAR(20),
    IN pricePerUnit DOUBLE,
    IN purchasePrice DOUBLE,
    IN transactionFee DOUBLE
)
BEGIN
-- Nächsten IDs holen
SELECT @historyId:=AUTO_INCREMENT
FROM information_schema.tables
WHERE table_name = 'history';

SELECT @investmentId:=AUTO_INCREMENT
FROM information_schema.tables
WHERE table_name = 'investment';

	INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
    VALUES (@historyId, asset_Id, DATE(purchaseDate), pricePerUnit);

    INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
    VALUES (@investmentId, portfolio_Id, platform_Id, asset_Id, @historyId, purchasePrice, transactionFee);
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetAssetInvestments(
	IN portfolio_Id INT,
	IN asset_Id INT
)
BEGIN
	SELECT *
	FROM investment
	WHERE investment.Portfolio_Id = portfolio_Id AND investment.Asset_Id = asset_Id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetAssetInvestmentsPresentation(
	IN portfolio_Id INT,
	IN asset_Id INT
)
BEGIN
	SET lc_time_names = 'de_DE';

    SELECT investment.PurchasePrice AS 'Investitionssumme in €', DATE_FORMAT(history.TimeStamp, '%d. %M %Y') AS 'Investitionsdatum'
    FROM investment
    LEFT JOIN history ON investment.History_Id = history.Id
    WHERE investment.Portfolio_Id = portfolio_Id AND investment.Asset_Id = asset_Id;
END //

DELIMITER ;

-- -----------------------------------------------------
-- Assets
-- -----------------------------------------------------
DELIMITER //

CREATE PROCEDURE GetAsset(
	IN asset_Id INT
)
BEGIN
	SELECT *
    FROM asset
    WHERE asset.id = asset_Id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetSummedAssets(
	IN portfolio_Id INT
)
BEGIN
	SELECT asset.Id, asset.Name AS 'Asset', SUM(investment.PurchasePrice) AS 'Investierter Betrag'
    FROM investment
    LEFT JOIN asset ON Asset_Id = asset.Id
    LEFT JOIN history ON History_id = history.Id
    WHERE Portfolio_Id = portfolio_Id
    GROUP BY asset.Id, asset.Name
    ORDER BY 2 desc;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetInvestedAssets(
	IN portfolio_Id INT
)
BEGIN
	SELECT asset.*
    FROM type
    LEFT JOIN asset ON asset.Type_Id = type.Id
    LEFT JOIN investment ON investment.Asset_Id = asset.Id
    WHERE investment.Portfolio_Id = portfolio_Id
    GROUP BY asset.Id, asset.Type_Id, asset.Name, asset.ShortName
    HAVING COUNT(investment.Id) > 0;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetInvestedSumForAsset(
	IN asset_Id INT
)
BEGIN
	SELECT SUM(investment.PurchasePrice)
    FROM investment
    LEFT JOIN asset ON investment.Asset_Id = asset.Id
    WHERE asset.id = asset_Id
    GROUP BY asset.Id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetAssets()
BEGIN
	SELECT *
    FROM asset;
END //

DELIMITER ;

-- -----------------------------------------------------
-- Type
-- -----------------------------------------------------
DELIMITER //

CREATE PROCEDURE GetType(
	IN type_Id INT
)
BEGIN
	SELECT *
    FROM type
    WHERE Id = type_Id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE GetInvestedAssetTypes(
	IN portfolio_Id INT
)
BEGIN
	SELECT type.*
    FROM type
    LEFT JOIN asset ON asset.Type_Id = type.Id
    LEFT JOIN investment ON investment.Asset_Id = asset.Id
    WHERE investment.Portfolio_Id = portfolio_Id
    GROUP BY type.Id
    HAVING COUNT(investment.Id) > 0;
END //

DELIMITER ;

-- -----------------------------------------------------
-- Info
-- -----------------------------------------------------
DELIMITER //

CREATE PROCEDURE GetInfo(
	IN info_Id INT
)
BEGIN
	SELECT *
    FROM info
    WHERE Type_Id = info_Id;
END //

DELIMITER ;
