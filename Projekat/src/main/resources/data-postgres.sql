
insert into klinicki_centar (naziv, adresa, opis) values ('Klinicki centar Srbije', 'Pasterova 2','Klinicki centar uvek na usluzi svojim pacijentima');
insert into administratorkc (ime, prezime, lozinka, email, klinicki_centar_id) values ('MVC', 'MVC', 'mvc', 'mvc@gmail.com', 1);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Beograd', 'Pasterova 2','Klinika uvek na usluzi svojim pacijentima',1, 0);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Nis', 'Bulevar dr Zorana Djindjica 48','Klinika uvek na usluzi svojim pacijentima',1, 0);
insert into klinika (naziv, adresa, opis, klinicki_centar_id, ocena) values ('Klinika Novi Sad', 'Hajduk Veljkova 1','Klinika uvek na usluzi svojim pacijentima',1, 0);
insert into administrator_klinike (ime, prezime, korisnicko_ime, lozinka, email, klinika_id) values ('Maga', 'Lakic', 'maga', 'maga', 'maga@gmail.com', 1);
insert into administrator_klinike (ime, prezime, korisnicko_ime, lozinka, email, klinika_id) values ('Ceca', 'Antesevic', 'ceca', 'ceca', 'ceca@gmail.com', 2);
insert into administrator_klinike (ime, prezime, korisnicko_ime, lozinka, email, klinika_id) values ('Viki', 'Maric', 'viki', 'viki', 'viki@gmail.com', 3);

insert into lek (naziv, sifra) values ('Andol','001');
insert into lek (naziv, sifra) values ('Cafetin','002');
insert into lek (naziv, sifra) values ('Panadol','003');
insert into lek (naziv, sifra) values ('Nitroglicerin','004');
insert into lek (naziv, sifra) values ('Valsacor','005');
insert into lek (naziv, sifra) values ('Tiastat','006');
insert into lek (naziv, sifra) values ('Febricet','007');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Milica','Markovic', 'micacica@gmail.com', 'mica', 1, 0, '066/243-665');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena,  telefon) values ('Jovan','Jovanovic', 'jole@gmail.com', 'jole', 2, 0, '063/200-765');
insert into lekar (ime, prezime, email, lozinka,klinika_id, ocena, telefon) values ('Dusan','Dusanovic', 'duskodule@gmail.com', 'dule', 3, 0, '066/993-785');

insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Jelena','Jelenovic', 'jelena@gmail.com', '066/222-665', 'jeca', 1);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Olgica','Olganovic', 'olgaolgaa@gmail.com','063/222-765', 'olga', 2);
insert into medicinska_sestra (ime, prezime, email, br_telefona, lozinka,klinika_id) values ('Gordana','Gordanovic', 'gocagordana@gmail.com', '066/963-785', 'goca', 3);
insert into sala (broj, klinika_id) values ('001C', 1);
insert into sala (broj, klinika_id) values ('002B', 2);
insert into sala (broj, klinika_id) values ('003A', 3);

insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon) values ('Pera', 'Peric', '001', 'pera', 'pera@gmail.com', 'Temerinska 55','Novi Sad', 'Srbija','065241359');
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon) values ('Mita', 'Mitic', '002', 'mita', 'mita@gmail.com', 'Petra Drapsina 89', 'Novi Sad', 'Srbija','0642255696');
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon) values ('Jovan', 'Jovicic', '003', 'jovan', 'jovan@gmail.com', 'Petra Kocica 66', 'Novi Sad', 'Srbija','061221478');
insert into pacijent (ime, prezime, lbo,  lozinka, email, adresa, grad, drzava, telefon) values ('Lara', 'Nikolic', '004', 'lara', 'lara@gmail.com', 'Kralja Petra 11', 'Novi Sad', 'Srbija','0642255696');


insert into lekar_pacijent (pacijent_id, lekar_id) values (1,2);
insert into lekar_pacijent (pacijent_id, lekar_id) values (2,2);



