insert into GRAD(Naziv, Broj) values ('Beograd', '11000')
insert into OPSTINA(Naziv, x, y, IdGrad) values ('Novi Beograd', 10, 40, 694);
insert into OPSTINA(Naziv, x, y, IdGrad) values ('Vozdovac', 10, 15, 694)

insert into GRAD(Naziv, Broj) values ('Banja Luka', 78000)
insert into OPSTINA(Naziv, x, y, IdGrad) values ('Starcevica', 1, 12, 696)
insert into OPSTINA(Naziv, x, y, IdGrad) values ('Laus', 3, 14, 696)

insert into KORISNIK(Ime, Prezime, KorisnickoIme, Sifra) values ('Stefan', 'Todorovic', 'stefan', '12345678A')
insert into KORISNIK(Ime, Prezime, KorisnickoIme, Sifra) values ('Goran', 'Goranovic', 'gogi', '12345678A')
insert into KORISNIK(Ime, Prezime, KorisnickoIme, Sifra) values ('Zoran', 'Zoranovic', 'zoki', '12345678A')
insert into KORISNIK(Ime, Prezime, KorisnickoIme, Sifra) values ('Pera', 'Peric', 'pera', '12345678A')
insert into KORISNIK(Ime, Prezime, KorisnickoIme, Sifra) values ('Zika', 'Zikic', 'zika', '12345678A')

insert into VOZILO(RegBr, Tip, Potrosnja) values ('123', 0, 6.2)
insert into VOZILO(RegBr, Tip, Potrosnja) values ('456', 2, 4.9)
insert into VOZILO(RegBr, Tip, Potrosnja) values ('789', 1, 7.7)
insert into VOZILO(RegBr, Tip, Potrosnja) values ('003', 2, 5.5)
insert into VOZILO(RegBr, Tip, Potrosnja) values ('135', 0, 7.4)

insert into ADMINISTRATOR(KorisnickoIme) values ('pera')

insert into KURIR(KorisnickoIme, RegBr) values ('stefan', '123')
insert into KURIR(KorisnickoIme, RegBr) values ('zoki', '789')

insert into PAKET(Tip, Tezina, OpstinaPreUzima, OpstinaDostavlja, Korisnik) values (2, 4.1, 407, 408, 'zoki')
insert into PAKET(Tip, Tezina, OpstinaPreUzima, OpstinaDostavlja, Korisnik) values (0, 1.9, 407, 409, 'zika')
insert into PAKET(Tip, Tezina, OpstinaPreUzima, OpstinaDostavlja, Korisnik) values (1, 7.4, 409, 410, 'pera')
insert into PAKET(Tip, Tezina, OpstinaPreUzima, OpstinaDostavlja, Korisnik) values (2, 5.0, 410, 407, 'gogi')

insert into PONUDA(KorisnickoIme, IdPaket, Procenat) values ('stefan', 137, 12.5)
insert into PONUDA(KorisnickoIme, IdPaket, Procenat) values ('zoki', 139, 17)
insert into PONUDA(KorisnickoIme, IdPaket, Procenat) values ('zoki', 140, 14)

insert into ZAHTEVATI(KorisnickoIme, RegBr) values ('zika', '456')





