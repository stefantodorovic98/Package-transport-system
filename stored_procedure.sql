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


