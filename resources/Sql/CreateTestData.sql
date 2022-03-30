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
VALUES (3, 'Edelmetalle', 'MET', 1);

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
