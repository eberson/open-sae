create table tbcurso(
	codigo bigint generated by default as identity (start with 1),
	codigoEscolaCurso integer,
	nome varchar(250) not null,
	vagas int,
	primary key (codigo)
);