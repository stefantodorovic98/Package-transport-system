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


