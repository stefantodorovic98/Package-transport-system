CREATE TABLE [ADMINISTRATOR]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL 
)
go

CREATE TABLE [GRAD]
( 
	[IdGrad]             integer  IDENTITY ( 1,1 )  NOT NULL ,
	[Naziv]              varchar(100)  NOT NULL ,
	[Broj]               varchar(100)  NOT NULL 
)
go

CREATE TABLE [KORISNIK]
( 
	[Ime]                varchar(100)  NOT NULL ,
	[Prezime]            varchar(100)  NOT NULL ,
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[Sifra]              varchar(100)  NOT NULL ,
	[BrPoslatihPaketa]   integer  NOT NULL 
	CONSTRAINT [Nula_1]
		 DEFAULT  0
	CONSTRAINT [VeceJednako0_729827647]
		CHECK  ( BrPoslatihPaketa >= 0 )
)
go

CREATE TABLE [KURIR]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[BrIsporucenihPaketa] integer  NOT NULL 
	CONSTRAINT [Nula_1448162665]
		 DEFAULT  0
	CONSTRAINT [VeceJednako0_1567593786]
		CHECK  ( BrIsporucenihPaketa >= 0 ),
	[Profit]             decimal(10,3)  NOT NULL 
	CONSTRAINT [Nula_125402010]
		 DEFAULT  0,
	[Status]             integer  NOT NULL 
	CONSTRAINT [Nula_108557670]
		 DEFAULT  0
	CONSTRAINT [NulaJedan_319882063]
		CHECK  ( Status BETWEEN 0 AND 1 ),
	[RegBr]              varchar(100)  NOT NULL 
)
go

CREATE TABLE [OPSTINA]
( 
	[IdOpstina]          integer  IDENTITY ( 1,1 )  NOT NULL ,
	[Naziv]              varchar(100)  NOT NULL ,
	[x]                  integer  NOT NULL ,
	[y]                  integer  NOT NULL ,
	[IdGrad]             integer  NOT NULL 
)
go

CREATE TABLE [PAKET]
( 
	[IdPaket]            integer  IDENTITY ( 1,1 )  NOT NULL ,
	[Tip]                integer  NOT NULL 
	CONSTRAINT [NulaJedanDva_1414202407]
		CHECK  ( Tip BETWEEN 0 AND 2 ),
	[Tezina]             decimal(10,3)  NOT NULL 
	CONSTRAINT [VeceJednako0_1035715819]
		CHECK  ( Tezina >= 0 ),
	[OpstinaPreuzima]    integer  NOT NULL ,
	[OpstinaDostavlja]   integer  NOT NULL ,
	[Korisnik]           varchar(100)  NOT NULL ,
	[Kurir]              varchar(100)  NULL ,
	[Status]             integer  NOT NULL 
	CONSTRAINT [Nula_175212909]
		 DEFAULT  0
	CONSTRAINT [NulaJedanDvaTri_1459792570]
		CHECK  ( Status BETWEEN 0 AND 3 ),
	[Cena]               decimal(10,3)  NULL 
	CONSTRAINT [VeceJednako0_2031686903]
		CHECK  ( Cena >= 0 ),
	[VremePrihvatanja]   datetime  NULL 
)
go

CREATE TABLE [PONUDA]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[IdPaket]            integer  NOT NULL ,
	[Procenat]           decimal(10,3)  NOT NULL 
	CONSTRAINT [VeceJednako0_107781228]
		CHECK  ( Procenat >= 0 ),
	[IdPonuda]           integer  IDENTITY ( 1,1 )  NOT NULL 
)
go

CREATE TABLE [VOZILO]
( 
	[RegBr]              varchar(100)  NOT NULL ,
	[Tip]                integer  NOT NULL 
	CONSTRAINT [NulaJedanDva_1513227722]
		CHECK  ( Tip BETWEEN 0 AND 2 ),
	[Potrosnja]          decimal(10,3)  NOT NULL 
	CONSTRAINT [VeceJednako0_784052863]
		CHECK  ( Potrosnja >= 0 )
)
go

CREATE TABLE [ZAHTEVATI]
( 
	[KorisnickoIme]      varchar(100)  NOT NULL ,
	[RegBr]              varchar(100)  NOT NULL 
)
go

ALTER TABLE [ADMINISTRATOR]
	ADD CONSTRAINT [XPKADMINISTRATOR] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [GRAD]
	ADD CONSTRAINT [XPKGRAD] PRIMARY KEY  CLUSTERED ([IdGrad] ASC)
go

ALTER TABLE [KORISNIK]
	ADD CONSTRAINT [XPKKORISNIK] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [KURIR]
	ADD CONSTRAINT [XPKKURIR] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go

ALTER TABLE [OPSTINA]
	ADD CONSTRAINT [XPKOPSTINA] PRIMARY KEY  CLUSTERED ([IdOpstina] ASC)
go

ALTER TABLE [PAKET]
	ADD CONSTRAINT [XPKPAKET] PRIMARY KEY  CLUSTERED ([IdPaket] ASC)
go

ALTER TABLE [PONUDA]
	ADD CONSTRAINT [XPKPONUDA] PRIMARY KEY  CLUSTERED ([IdPonuda] ASC)
go

ALTER TABLE [VOZILO]
	ADD CONSTRAINT [XPKVOZILO] PRIMARY KEY  CLUSTERED ([RegBr] ASC)
go

ALTER TABLE [ZAHTEVATI]
	ADD CONSTRAINT [XPKZAHTEVATI] PRIMARY KEY  CLUSTERED ([KorisnickoIme] ASC)
go


ALTER TABLE [ADMINISTRATOR]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KORISNIK]([KorisnickoIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go


ALTER TABLE [KURIR]
	ADD CONSTRAINT [R_4] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KORISNIK]([KorisnickoIme])
		ON DELETE CASCADE
		ON UPDATE CASCADE
go

ALTER TABLE [KURIR]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([RegBr]) REFERENCES [VOZILO]([RegBr])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [OPSTINA]
	ADD CONSTRAINT [R_1] FOREIGN KEY ([IdGrad]) REFERENCES [GRAD]([IdGrad])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [PAKET]
	ADD CONSTRAINT [R_6] FOREIGN KEY ([OpstinaPreuzima]) REFERENCES [OPSTINA]([IdOpstina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PAKET]
	ADD CONSTRAINT [R_7] FOREIGN KEY ([OpstinaDostavlja]) REFERENCES [OPSTINA]([IdOpstina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PAKET]
	ADD CONSTRAINT [R_8] FOREIGN KEY ([Korisnik]) REFERENCES [KORISNIK]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PAKET]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([Kurir]) REFERENCES [KURIR]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [PONUDA]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KURIR]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [PONUDA]
	ADD CONSTRAINT [R_15] FOREIGN KEY ([IdPaket]) REFERENCES [PAKET]([IdPaket])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [ZAHTEVATI]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KORISNIK]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [ZAHTEVATI]
	ADD CONSTRAINT [R_10] FOREIGN KEY ([KorisnickoIme]) REFERENCES [KORISNIK]([KorisnickoIme])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [ZAHTEVATI]
	ADD CONSTRAINT [R_11] FOREIGN KEY ([RegBr]) REFERENCES [VOZILO]([RegBr])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go






CREATE proc [dbo].[spOdobriZahtevZaKurira]
@KorisnickoIme varchar(100),
@Broj int output
as
begin
	declare @RegBr varchar(100)

	if(exists(select * from ZAHTEVATI where KorisnickoIme = @KorisnickoIme))
	begin
		select @RegBr = RegBr from ZAHTEVATI where KorisnickoIme = @KorisnickoIme
		if(exists(select * from KURIR where RegBr = @RegBr))
		begin
			set @Broj = 0
		end
		else
		begin
			insert into KURIR(KorisnickoIme, RegBr) values (@KorisnickoIme, @RegBr)
			if(@@ROWCOUNT = 1)
			begin
				delete from ZAHTEVATI where RegBr = @RegBr
				set @Broj = 1
			end
			else
			begin
				set @Broj = 0
			end
		end
	end
	else
	begin
		set @Broj = 0;
	end
end
GO




create trigger TR_TransportOffer_PrihvatanjePonude
on PAKET
for update
as
begin
	declare @KursorI Cursor
	declare @KursorD Cursor
	declare @stariIdPaket int
	declare @stariKurir varchar(100)
	declare @stariKorisnik varchar(100)
	declare @noviIdPaket int
	declare @noviKurir varchar(100)
	declare @noviKorisnik varchar(100)
	
	set @KursorD = cursor for
	select IdPaket, Kurir, Korisnik 
	from deleted
	open @KursorD

	set @KursorI = cursor for
	select IdPaket, Kurir, Korisnik
	from inserted
	open @KursorI

	fetch next from @KursorD
	into @stariIdPaket, @stariKurir, @stariKorisnik
	fetch next from @KursorI
	into @noviIdPaket, @noviKurir, @noviKorisnik

	while (@@FETCH_STATUS = 0)
	begin
		if ((@stariKurir is null) and (@noviKurir is not null) and (@stariIdPaket = @noviIdPaket))
		begin
			delete from PONUDA where IdPaket = @noviIdPaket
			update Korisnik set BrPoslatihPaketa = BrPoslatihPaketa + 1 where KorisnickoIme = @noviKorisnik
		end
		fetch next from @KursorD
		into @stariIdPaket, @stariKurir, @stariKorisnik
		fetch next from @KursorI
		into @noviIdPaket, @noviKurir, @noviKorisnik
	end

	close @KursorD
	deallocate @KursorD
	close @KursorI
	deallocate @KursorI

end


