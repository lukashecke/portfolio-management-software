USE gruppeZ;

-- -----------------------------------------------------
-- Portfolios
-- -----------------------------------------------------
INSERT INTO portfolio (Id) 
VALUES (1);

-- -----------------------------------------------------
-- Platforms
-- -----------------------------------------------------
INSERT INTO platform (Id, Name) 
VALUES (1, 'Trade Repubic');

INSERT INTO platform (Id, Name) 
VALUES (2, 'Coinbase');

INSERT INTO platform (Id, Name)
VALUES (3, 'Ebay');

-- -----------------------------------------------------
-- Types
-- -----------------------------------------------------
INSERT INTO Type (Id, NAME, ShortName, NeedsIncomeTax) 
VALUES (1, 'Börsengehandelter Fonds', 'ETF', 0);

INSERT INTO Type (Id, NAME, ShortName, NeedsIncomeTax) 
VALUES (2, 'Kryptowährung', 'Krypto', 1);

INSERT INTO Type (Id, NAME, ShortName, NeedsIncomeTax)
VALUES (3, 'Edelmetalle', 'Met.', 1);

INSERT INTO Type (Id, NAME, ShortName, NeedsIncomeTax)
VALUES (4, 'Einzelaktien', 'Aktien', 1);

-- -----------------------------------------------------
-- Assets
-- -----------------------------------------------------
INSERT INTO asset (Id, Type_Id, Name, ShortName) 
VALUES (1, 1, 'MSCI All Country World Index', 'MSCI ACWI');

INSERT INTO asset (Id, Type_Id, Name, ShortName) 
VALUES (2, 2, 'Bitcoin', 'BTC');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (3, 2, 'Ethereum', 'ETH');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (4, 3, 'Gold', 'GLD');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (5, 4, 'Tesla', 'TSLA');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (6, 4, 'Microsoft', 'MSFT');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (7, 4, 'Apple', 'AAPL');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (8, 2, 'Polkadot', 'DOT');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (9, 2, 'Solana', 'SOL');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (10, 2, 'Cardano', 'ADA');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (11, 4, 'Nike', 'NKE');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (12, 1, 'Digital Security EUR', 'DIGS');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (13, 1, 'Global Clean Energy EUR', 'GCE');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (14, 1, 'S&P500 EUR', 'S&P');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (15, 3, 'Silber', 'SLB');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (16, 3, 'Titan', 'TITN');

INSERT INTO asset (Id, Type_Id, Name, ShortName)
VALUES (17, 3, 'Diamanten', 'DMNDS');

-- -----------------------------------------------------
-- Labels
-- -----------------------------------------------------
INSERT INTO label (Id, Name, Description, Color)
VALUES (1, 'Risiko', 'Es handelt sich hierbei um ein sehr risikoreiche Asset, weil zum Beispiel die volatilität besonders hoch ist.', '#d73a4a');

-- -----------------------------------------------------
-- Asset_has_Label
-- -----------------------------------------------------
INSERT INTO asset_has_label (Asset_Id, Label_Id)
VALUES (2, 1);

-- -----------------------------------------------------
-- History
-- -----------------------------------------------------
INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (1, 1, DATE('2022-02-28 00:00:00'), 62.42);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (2, 2, DATE('2022-01-22 00:00:00'), 30678.00);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (3, 2, DATE('2022-03-29 00:00:00'), 42513.00);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (4, 1, DATE('2022-03-28 00:00:00'), 65.34);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (5, 3, DATE('2022-03-15 00:00:00'), 2405.73);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (6, 4, DATE('2022-03-30 00:00:00'), 50.00);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (7, 5, DATE('2022-03-30 00:00:00'), 1020.60);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (8, 5, DATE('2022-03-20 00:00:00'), 1010.60);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (9, 4, DATE('2022-03-20 00:00:00'), 177.72);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (10, 14, DATE('2022-03-20 00:00:00'), 4565.69);

-- -----------------------------------------------------
-- Investments
-- -----------------------------------------------------
INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (1, 1, 1, 1, 1, 62.42, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (2, 1, 2, 2, 2, 50.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (3, 1, 2, 2, 3, 50.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (4, 1, 1, 1, 4, 65.34, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (5, 1, 2, 3, 5, 50.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (6, 1, 3, 4, 6, 50.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (7, 1, 1, 5, 7, 50.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (8, 1, 1, 5, 8, 50.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (9, 1, 1, 4, 9, 50.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (10, 1, 1, 14, 10, 75.00, 1.00);

-- -----------------------------------------------------
-- Benutzer und Berechtigungen
-- -----------------------------------------------------
DROP USER IF EXISTS 'gruppeZadmin'@'localhost';
CREATE USER  IF NOT EXISTS 'gruppeZadmin'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON gruppeZ.* TO 'gruppeZadmin'@'localhost';
FLUSH PRIVILEGES;

DROP USER IF EXISTS 'gruppeZuser'@'localhost';
CREATE USER  IF NOT EXISTS 'gruppeZuser'@'localhost' IDENTIFIED BY '0000';
GRANT SELECT ON gruppeZ.* TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.GetInvestedAssets TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.GetType TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.GetInfo TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.GetInvestedSumForAsset TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.GetAssetInvestmentsPresentation TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.GetAssets TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.CreateNewInvestment TO 'gruppeZuser'@'localhost';
GRANT EXECUTE ON PROCEDURE gruppeZ.GetPlatforms TO 'gruppeZuser'@'localhost';
FLUSH PRIVILEGES;
