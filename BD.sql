                    --------AUTENTICACIÓN

--CREACIÓN DE TABLA USUARIOS

CREATE TABLE USUARIOS(
	idusuario uniqueidentifier,
	Nombre varchar(120),
	apellidop varchar(120),
	apellidom varchar(120),
	telefono varchar(120),
	correo varchar(70),
	password varchar(max)
)
 
--CREACIÓN DE USUARIO
CREATE PROCEDURE SP_AddUserAuthentication
@Email VARCHAR(70),
@Password VARCHAR(max),
@Nombre VARCHAR(120),
@ApellidoP VARCHAR(120),
@ApellidoM VARCHAR(120),
@Telefono VARCHAR(10)
AS
BEGIN 
DECLARE @count INT

SELECT @count = COUNT(*) FROM USUARIOS  WHERE correo = @Email;

IF @count > 0
	BEGIN
		SELECT Isok = 'false', Mensaje = 'User existente'
	END
ELSE
	BEGIN 
	
	INSERT INTO USUARIOS(
		idusuario,
		Nombre,
		apellidop,
		apellidom,
		telefono,
		correo,
		password
	)
	VALUES (
		NEWID(),
		@Nombre,
		@ApellidoP,
		@ApellidoM,
		@Telefono,
		@Email,
		@Password
	)
	SELECT Isok = 'true', Mensaje = 'Insert ok'
	END
END

--AUTHENTICACION LOGIN

CREATE PROCEDURE SP_AuthenticationUserLogin
@Email VARCHAR(70),
@Password VARCHAR(max)

AS
BEGIN 
DECLARE @count INT

SELECT @count = COUNT(*) FROM USUARIOS  WHERE correo = @Email;

IF @count > 0
	BEGIN
        SELECT correo, password,Isok = 'true' FROM USUARIOS WHERE correo=@Email
	END
ELSE
	BEGIN 
   		SELECT Isok = 'false', Mensaje = 'Usuario no existe'
	END
END


-- TABLA PRODUCTOS

CREATE TABLE PRODUCTOSCATEGORIA(
   	Idcategoria uniqueidentifier PRIMARY Key,
    Nombre VARCHAR(70)
)

INSERT INTO PRODUCTOSCATEGORIA(
    Idcategoria,
    Nombre
)VALUES(
    NEWID(),
    'Ferreteria'
)
 --SP GETCATALOGPRODUCTOS

CREATE PROCEDURE SP_Getcategoriasproductos
AS
BEGIN 
 SELECT Idcategoria, nombre from PRODUCTOSCATEGORIA
END


CREATE TABLE PRODUCTOS (
	idproducto uniqueidentifier,
    nombre VARCHAR(100),
    cantidad int,
    precioinicial FLOAT,
    preciofinal FLOAT,
    
)