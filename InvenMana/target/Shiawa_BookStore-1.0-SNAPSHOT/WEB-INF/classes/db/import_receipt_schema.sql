/*
  Import Receipt tables for inventory module.
  Uses SQL Server and DB_DESIGN database.
*/

IF OBJECT_ID(N'dbo.ImportReceipt', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.ImportReceipt (
        receipt_id INT IDENTITY(1,1) PRIMARY KEY,
        staff_id INT NULL,
        supplier_id INT NULL,
        import_date DATETIME2 NOT NULL CONSTRAINT DF_ImportReceipt_import_date DEFAULT (SYSDATETIME()),
        total_amount DECIMAL(18,2) NULL,
        note NVARCHAR(500) NULL
    );
END

IF OBJECT_ID(N'dbo.ImportReceiptDetail', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.ImportReceiptDetail (
        detail_id INT IDENTITY(1,1) PRIMARY KEY,
        receipt_id INT NOT NULL,
        book_id INT NOT NULL,
        qty INT NOT NULL,
        import_price DECIMAL(18,2) NOT NULL,
        CONSTRAINT FK_ImportReceiptDetail_Receipt FOREIGN KEY (receipt_id) REFERENCES dbo.ImportReceipt(receipt_id) ON DELETE CASCADE,
        CONSTRAINT FK_ImportReceiptDetail_Book FOREIGN KEY (book_id) REFERENCES dbo.Book(book_id),
        CONSTRAINT CK_ImportReceiptDetail_qty CHECK (qty > 0)
    );
END


