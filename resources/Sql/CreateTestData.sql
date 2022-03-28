USE firestocks;

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

-- -----------------------------------------------------
-- Types
-- -----------------------------------------------------
INSERT INTO Type (Id, NAME, ShortName, NeedsIncomeTax) 
VALUES (1, 'Börsengehandelter Fonds', 'ETF', 0);

INSERT INTO Type (Id, NAME, ShortName, NeedsIncomeTax) 
VALUES (2, 'Kryptowährung', 'Krypto', 1);

-- -----------------------------------------------------
-- Assets
-- -----------------------------------------------------
INSERT INTO asset (Id, Type_Id, Name, ShortName) 
VALUES (1, 1, 'MSCI All Country World Index', 'MSCI ACWI');

INSERT INTO asset (Id, Type_Id, Name, ShortName) 
VALUES (2, 1, 'Bitcoin', 'BTC');

-- -----------------------------------------------------
-- Labels
-- -----------------------------------------------------
INSERT INTO label (Id, Name, Description, Color)
VALUES (1, 'Risiko', 'Es handelt sich hierbei um ein sehr risikoreiche Asset, weil zum Beispiel die volatilität besonders hochist.', '#d73a4a');

-- -----------------------------------------------------
-- Asset_has_Label
-- -----------------------------------------------------
INSERT INTO asset_has_label (Asset_Id, Label_Id)
VALUES (2, 1);

-- -----------------------------------------------------
-- History
-- -----------------------------------------------------
INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (1, 1, NOW(), 62.00);

INSERT INTO history (Id, Asset_Id, TimeStamp, PricePerUnit)
VALUES (2, 2, NOW(), 33000.00);

-- -----------------------------------------------------
-- Investments
-- -----------------------------------------------------
INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (1, 1, 1, 1, 1, 62.00, 1.00);

INSERT INTO investment (Id, Portfolio_Id, Platform_Id, Asset_Id, History_Id, PurchasePrice, TransactionFee)
VALUES (2, 1, 2, 2, 2, 50.00, 1.00);
