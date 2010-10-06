SET FOREIGN_KEY_CHECKS=0;



DROP TABLE IF EXISTS consulta
;
DROP TABLE IF EXISTS cubo
;
DROP TABLE IF EXISTS cubo_dimensao
;
DROP TABLE IF EXISTS dimensao
;
DROP TABLE IF EXISTS filtro
;
DROP TABLE IF EXISTS hierarquia
;
DROP TABLE IF EXISTS hierarquia_juncao
;
DROP TABLE IF EXISTS metrica
;
DROP TABLE IF EXISTS nivel
;
DROP TABLE IF EXISTS no
;
DROP TABLE IF EXISTS propriedade
;

CREATE TABLE consulta
(
	idconsulta INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	eixox INTEGER NOT NULL,
	eixoy INTEGER NOT NULL,
	expressaofiltro VARCHAR(50),
	PRIMARY KEY (idconsulta),
	KEY (eixox),
	KEY (eixoy)
) 
;


CREATE TABLE cubo
(
	idcubo INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	tabela VARCHAR(50) NOT NULL,
	PRIMARY KEY (idcubo)
) 
;


CREATE TABLE cubo_dimensao
(
	idcubo INTEGER NOT NULL,
	iddimensao INTEGER NOT NULL,
	PRIMARY KEY (idcubo, iddimensao),
	KEY (idcubo),
	KEY (iddimensao)
) 
;


CREATE TABLE dimensao
(
	iddimensao INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	colunajuncaocubo VARCHAR(50) NOT NULL,
	PRIMARY KEY (iddimensao)
) 
;


CREATE TABLE filtro
(
	idcubo INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	expressao VARCHAR(50) NOT NULL,
	PRIMARY KEY (idcubo),
	KEY (idcubo)
) 
;


CREATE TABLE hierarquia
(
	iddimensao INTEGER NOT NULL,
	idhierarquia INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	tabela VARCHAR(50) NOT NULL,
	PRIMARY KEY (iddimensao, idhierarquia),
	KEY (iddimensao)
) 
;


CREATE TABLE hierarquia_juncao
(
	iddimensao INTEGER NOT NULL,
	idhierarquia INTEGER NOT NULL,
	tabeladireita VARCHAR(50) NOT NULL,
	colunadireita VARCHAR(50) NOT NULL,
	tabelaesquerda VARCHAR(50) NOT NULL,
	colunaesquerda VARCHAR(50) NOT NULL,
	PRIMARY KEY (iddimensao, idhierarquia),
	KEY (iddimensao, idhierarquia)
) 
;


CREATE TABLE metrica
(
	idcubo INTEGER NOT NULL,
	idmetrica INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	expressaometrica VARCHAR(50) NOT NULL,
	expressaofiltro VARCHAR(50) NOT NULL,
	padrao BOOL,
	PRIMARY KEY (idcubo, idmetrica),
	KEY (idcubo)
) 
;


CREATE TABLE nivel
(
	iddimensao INTEGER NOT NULL,
	idhierarquia INTEGER NOT NULL,
	idnivel INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	tabela VARCHAR(50) NOT NULL,
	colunaid VARCHAR(50) NOT NULL,
	colunanome VARCHAR(50) NOT NULL,
	PRIMARY KEY (iddimensao, idhierarquia, idnivel),
	KEY (iddimensao, idhierarquia)
) 
;


CREATE TABLE no
(
	idno INTEGER NOT NULL,
	idobjeto INTEGER NOT NULL,
	tipoobjeto INTEGER NOT NULL,
	idnosuperior INTEGER,
	indice INTEGER NOT NULL,
	PRIMARY KEY (idno),
	KEY (idnosuperior)
) 
;


CREATE TABLE propriedade
(
	iddimensao INTEGER NOT NULL,
	idhierarquia INTEGER NOT NULL,
	idnivel INTEGER NOT NULL,
	idpropriedade INTEGER NOT NULL,
	nome VARCHAR(50),
	expressao VARCHAR(50),
	PRIMARY KEY (iddimensao, idhierarquia, idnivel, idpropriedade),
	KEY (idnivel, iddimensao, idhierarquia)
) 
;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE consulta ADD CONSTRAINT FK_consulta_no_x 
	FOREIGN KEY (eixox) REFERENCES no (idno)
;

ALTER TABLE consulta ADD CONSTRAINT FK_consulta_no_y 
	FOREIGN KEY (eixoy) REFERENCES no (idno)
;

ALTER TABLE cubo_dimensao ADD CONSTRAINT FK_cubo_dimensao_cubo 
	FOREIGN KEY (idcubo) REFERENCES cubo (idcubo)
;

ALTER TABLE cubo_dimensao ADD CONSTRAINT FK_cubo_dimensao_dimensao 
	FOREIGN KEY (iddimensao) REFERENCES dimensao (iddimensao)
;

ALTER TABLE filtro ADD CONSTRAINT FK_filtro_cubo 
	FOREIGN KEY (idcubo) REFERENCES cubo (idcubo)
;

ALTER TABLE hierarquia ADD CONSTRAINT FK_hierarquia_dimensao 
	FOREIGN KEY (iddimensao) REFERENCES dimensao (iddimensao)
;

ALTER TABLE hierarquia_juncao ADD CONSTRAINT FK_hierarquia_juncao_hierarquia 
	FOREIGN KEY (iddimensao, idhierarquia) REFERENCES hierarquia (idhierarquia, iddimensao)
;

ALTER TABLE metrica ADD CONSTRAINT FK_metrica_cubo 
	FOREIGN KEY (idcubo) REFERENCES cubo (idcubo)
;

ALTER TABLE nivel ADD CONSTRAINT FK_nivel_hierarquia 
	FOREIGN KEY (iddimensao, idhierarquia) REFERENCES hierarquia (idhierarquia, iddimensao)
;

ALTER TABLE no ADD CONSTRAINT FK_no_no 
	FOREIGN KEY (idnosuperior) REFERENCES no (idno)
;

ALTER TABLE propriedade ADD CONSTRAINT FK_propriedade_nivel 
	FOREIGN KEY (idnivel, iddimensao, idhierarquia) REFERENCES nivel (idnivel, iddimensao, idhierarquia)
;
