CREATE DATABASE ElBilleton;

USE ElBilleton;

CREATE TABLE Gerente (codigo int PRIMARY KEY, nombre varchar(150) NOT NULL, DPI varchar(18) NOT NULL, direccion varchar(200) NOT NULL, sexo varchar(9) NOT NULL, password varchar(200), turno varchar(10));

CREATE TABLE Cliente (codigo int PRIMARY KEY, nombre varchar(150) NOT NULL, DPI varchar (18) NOT NULL, direccion varchar(200) NOT NULL, sexo varchar(9) NOT NULL, password varchar(200), birth varchar(14), pathDPI varchar(250));

CREATE TABLE Cajero (codigo int PRIMARY KEY, nombre varchar(150) NOT NULL, DPI varchar (18) NOT NULL, direccion varchar(200) NOT NULL, sexo varchar(9) NOT NULL, password varchar(200), turno varchar(10));

CREATE TABLE Cuenta (numeroCuenta int PRIMARY KEY, codigoDueno int NOT NULL, monto int, fechaCreacion varchar(14), estado varchar(12) DEFAULT "activa");
ALTER TABLE Cuenta ADD FOREIGN KEY (codigoDueno) REFERENCES Cliente (codigo) ON DELETE NO ACTION; 

CREATE TABLE Asociacion (numeroCuentaSolicitante int NOT NULL, numeroCuentaSolicitado int NOT NULL, numeroIntentos int(1), estado varchar(9) DEFAULT "enEspera");
ALTER TABLE Asociacion ADD FOREIGN KEY (numeroCuentaSolicitante) REFERENCES Cuenta (numeroCuenta) ON DELETE CASCADE;
ALTER TABLE Asociacion ADD FOREIGN KEY (numeroCuentaSolicitado) REFERENCES Cuenta (numeroCuenta) ON DELETE CASCADE;

CREATE TABLE Transaccion (codigo int PRIMARY KEY, numeroCuentaAfectada int  NOT NULL, tipo varchar(8), monto int, fecha varchar(14), hora varchar(14), codigoCajero int NOT NULL);
ALTER TABLE Transaccion ADD FOREIGN KEY (numeroCuentaAfectada) REFERENCES Cuenta (numeroCuenta) ON DELETE NO ACTION;
ALTER TABLE Transaccion ADD FOREIGN KEY (codigoCajero) REFERENCES Cajero (codigo) ON DELETE NO ACTION;

CREATE TABLE Cambios_Cliente (fecha varchar(14), hora varchar(14), gerenteACargo int NOT NULL, tipoDeCambio varchar(15), clienteCambiado int NOT NULL, datoAnterior varchar(350), datoNuevo varchar(350));
ALTER TABLE Cambios_Cliente ADD FOREIGN KEY (gerenteACargo) REFERENCES Gerente (codigo) ON DELETE NO ACTION;
ALTER TABLE Cambios_Cliente ADD FOREIGN KEY (clienteCambiado) REFERENCES Cliente (codigo) ON DELETE NO ACTION;

CREATE TABLE Cambios_Cajero (fecha varchar(14), hora varchar(14), gerenteACargo int NOT NULL, tipoDeCambio varchar(15), cajeroCambiado int NOT NULL, datoAnterior varchar(350), datoNuevo varchar(350));
ALTER TABLE Cambios_Cajero ADD FOREIGN KEY (gerenteACargo) REFERENCES Gerente (codigo) ON DELETE NO ACTION;
ALTER TABLE Cambios_Cajero ADD FOREIGN KEY (cajeroCambiado) REFERENCES Cajero (codigo) ON DELETE NO ACTION;

CREATE TABLE Cambios_Gerente (fecha varchar(14), hora varchar(14), codigoGerente int NOT NULL, tipoDeCambio varchar(15), datoAnterior varchar(350), datoNuevo varchar(350));
ALTER TABLE Cambios_Gerente ADD FOREIGN KEY (codigoGerente) REFERENCES Gerente (codigo) ON DELETE NO ACTION;

CREATE TABLE Setting (limiteMayorMonto int, limiteMayorSumaMonto int);

INSERT INTO Cajero (codigo, nombre, DPI, direccion, sexo, password, turno) VALUES(101, "Banca Virtual","101", "---", "---", "8cX7%%tedj4!yJm4", "completo");

