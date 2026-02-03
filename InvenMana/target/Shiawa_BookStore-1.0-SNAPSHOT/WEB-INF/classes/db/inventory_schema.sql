/*
  Inventory Management extension for DB_DESIGN (SQL Server)
  - Books are items
  - 1 warehouse (logical)
  - Supports: Supplier, Stock In (GRN), Stock Out (Issue), Stock Adjust

  Notes:
  - This script is designed to be SAFE to run multiple times (IF NOT EXISTS checks).
  - It assumes you already have table [Book] with PK book_id and column stock (int).
*/

/* =============== SUPPLIER =============== */
IF OBJECT_ID(N'dbo.Supplier', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Supplier (
        supplier_id INT IDENTITY(1,1) PRIMARY KEY,
        name NVARCHAR(200) NOT NULL,
        phone NVARCHAR(50) NULL,
        email NVARCHAR(200) NULL,
        address NVARCHAR(500) NULL,
        is_active BIT NOT NULL CONSTRAINT DF_Supplier_is_active DEFAULT (1),
        created_at DATETIME2 NOT NULL CONSTRAINT DF_Supplier_created_at DEFAULT (SYSDATETIME())
    );
END

/* =============== STOCK TRANSACTION =============== */
IF OBJECT_ID(N'dbo.StockTxn', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.StockTxn (
        txn_id INT IDENTITY(1,1) PRIMARY KEY,
        txn_type VARCHAR(20) NOT NULL, /* IN | OUT | ADJUST */
        txn_code VARCHAR(30) NOT NULL,
        txn_date DATETIME2 NOT NULL CONSTRAINT DF_StockTxn_txn_date DEFAULT (SYSDATETIME()),
        supplier_id INT NULL,
        note NVARCHAR(500) NULL,
        created_by_staff_id INT NULL,
        created_at DATETIME2 NOT NULL CONSTRAINT DF_StockTxn_created_at DEFAULT (SYSDATETIME()),
        CONSTRAINT UQ_StockTxn_txn_code UNIQUE (txn_code),
        CONSTRAINT FK_StockTxn_Supplier FOREIGN KEY (supplier_id) REFERENCES dbo.Supplier(supplier_id)
        /* Optional FK to Staff if you have dbo.Staff(staff_id) */
    );
END

IF OBJECT_ID(N'dbo.StockTxnItem', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.StockTxnItem (
        item_id INT IDENTITY(1,1) PRIMARY KEY,
        txn_id INT NOT NULL,
        book_id INT NOT NULL,
        qty INT NOT NULL,
        unit_cost DECIMAL(18,2) NULL, /* only for IN (optional) */
        CONSTRAINT FK_StockTxnItem_Txn FOREIGN KEY (txn_id) REFERENCES dbo.StockTxn(txn_id) ON DELETE CASCADE,
        CONSTRAINT FK_StockTxnItem_Book FOREIGN KEY (book_id) REFERENCES dbo.Book(book_id),
        CONSTRAINT CK_StockTxnItem_qty_positive CHECK (qty > 0)
    );
END

/* Helpful indexes */
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_StockTxn_txn_date' AND object_id = OBJECT_ID('dbo.StockTxn'))
BEGIN
    CREATE INDEX IX_StockTxn_txn_date ON dbo.StockTxn(txn_date);
END

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_StockTxnItem_book_id' AND object_id = OBJECT_ID('dbo.StockTxnItem'))
BEGIN
    CREATE INDEX IX_StockTxnItem_book_id ON dbo.StockTxnItem(book_id);
END




