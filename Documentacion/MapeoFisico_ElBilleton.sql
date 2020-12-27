CREATE DATABASE ElBilleton;

USE ElBilleton;

CREATE TABLE Gerente (codigo int PRIMARY KEY, nombre varchar(150) NOT NULL, DPI varchar(18) NOT NULL, direccion varchar(200) NOT NULL, sexo varchar(9) NOT NULL, password varchar(200), turno varchar(10), fechaIncorporacion varchar(14), correo varchar(75) DEFAULT '???');

CREATE TABLE Cliente (codigo int PRIMARY KEY, nombre varchar(150) NOT NULL, DPI varchar (18) NOT NULL, direccion varchar(200) NOT NULL, sexo varchar(9) NOT NULL, password varchar(200), birth varchar(14), pathDPI varchar(250), fechaIncorporacion varchar(14), correo varchar(75) DEFAULT '???');

CREATE TABLE Cajero (codigo int PRIMARY KEY, nombre varchar(150) NOT NULL, DPI varchar (18) NOT NULL, direccion varchar(200) NOT NULL, sexo varchar(9) NOT NULL, password varchar(200), turno varchar(10), fechaIncorporacion varchar(14), correo varchar(75) DEFAULT '???');

CREATE TABLE Cuenta (numeroCuenta int PRIMARY KEY, codigoDueno int NOT NULL, monto decimal(22,2) DEFAULT 0, fechaCreacion varchar(14), estado varchar(12) DEFAULT "activa");
ALTER TABLE Cuenta ADD FOREIGN KEY (codigoDueno) REFERENCES Cliente (codigo) ON DELETE NO ACTION ON UPDATE CASCADE; 

CREATE TABLE Asociacion (codigoSolicitado int NOT NULL, numeroCuentaSolicitado int NOT NULL, codigoSolicitante int NOT NULL, estado varchar(9) DEFAULT "enEspera", fechaCreacion varchar(14));
ALTER TABLE Asociacion ADD FOREIGN KEY (codigoSolicitado) REFERENCES Cliente (codigo) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE Asociacion ADD FOREIGN KEY (numeroCuentaSolicitado) REFERENCES Cuenta (numeroCuenta) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE Asociacion ADD FOREIGN KEY (codigoSolicitante) REFERENCES Cliente (codigo) ON DELETE CASCADE ON UPDATE CASCADE;

CREATE TABLE Transaccion (codigo int PRIMARY KEY, numeroCuentaAfectada int  NOT NULL, tipo varchar(8), monto decimal(22,2), fecha varchar(14), hora varchar(14), codigoCajero int NOT NULL);
ALTER TABLE Transaccion ADD FOREIGN KEY (numeroCuentaAfectada) REFERENCES Cuenta (numeroCuenta) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE Transaccion ADD FOREIGN KEY (codigoCajero) REFERENCES Cajero (codigo) ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE TABLE Cambios_Cliente (fecha varchar(14), hora varchar(14), gerenteACargo int NOT NULL, tipoDeCambio varchar(15), clienteCambiado int NOT NULL, datoAnterior varchar(350), datoNuevo varchar(350));
ALTER TABLE Cambios_Cliente ADD FOREIGN KEY (gerenteACargo) REFERENCES Gerente (codigo) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE Cambios_Cliente ADD FOREIGN KEY (clienteCambiado) REFERENCES Cliente (codigo) ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE TABLE Cambios_Cajero (fecha varchar(14), hora varchar(14), gerenteACargo int NOT NULL, tipoDeCambio varchar(15), cajeroCambiado int NOT NULL, datoAnterior varchar(350), datoNuevo varchar(350));
ALTER TABLE Cambios_Cajero ADD FOREIGN KEY (gerenteACargo) REFERENCES Gerente (codigo) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE Cambios_Cajero ADD FOREIGN KEY (cajeroCambiado) REFERENCES Cajero (codigo) ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE TABLE Cambios_Gerente (fecha varchar(14), hora varchar(14), gerenteCambiado int NOT NULL, tipoDeCambio varchar(15), datoAnterior varchar(350), datoNuevo varchar(350));
ALTER TABLE Cambios_Gerente ADD FOREIGN KEY (gerenteCambiado) REFERENCES Gerente (codigo) ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE TABLE Setting (limiteMayorMonto double(22,2) DEFAULT 100, limiteMayorSumaMonto double(22,2) DEFAULT 100);

INSERT INTO Cajero (codigo, nombre, DPI, direccion, sexo, password, turno, fechaIncorporacion) VALUES(101, "Banca Virtual","101", "---", "---", "8cX7%%tedj4!yJm4", "completo", "???");
INSERT INTO Setting (limiteMayorMonto, limiteMayorSumaMonto) VALUES (150, 250);

