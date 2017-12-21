package interpreter;
import static interpreter.Token.*;
import static interpreter.TokenType.*;
%%
%class Lexer
%type Token
%line
%column
LET = [a-z]
DIG = [0-9]
WHITE=[ \t\r\n]
%%
{WHITE} {/*Ignore*/}
"=" {return new Token(TokenType.ASSIGN, yytext(), yyline + 1, yycolumn + 1);}
"+" {return new Token(TokenType.ARISMETIC, yytext(), yyline + 1, yycolumn + 1);}
"*" {return new Token(TokenType.ARISMETIC, yytext(), yyline + 1, yycolumn + 1);}
"-" {return new Token(TokenType.ARISMETIC, yytext(), yyline + 1, yycolumn + 1);}
"/" {return new Token(TokenType.ARISMETIC, yytext(), yyline + 1, yycolumn + 1);}
"%" {return new Token(TokenType.ARISMETIC, yytext(), yyline + 1, yycolumn + 1);}
"<" {return new Token(TokenType.LOGIC, yytext(), yyline + 1, yycolumn + 1);}
">" {return new Token(TokenType.LOGIC, yytext(), yyline + 1, yycolumn + 1);}
"<=" {return new Token(TokenType.LOGIC, yytext(), yyline + 1, yycolumn + 1);}
">=" {return new Token(TokenType.LOGIC, yytext(), yyline + 1, yycolumn + 1);}
"==" {return new Token(TokenType.LOGIC, yytext(), yyline + 1, yycolumn + 1);}
"!=" {return new Token(TokenType.LOGIC, yytext(), yyline + 1, yycolumn + 1);}
";" {return new Token(TokenType.SEMICOLON, yytext(), yyline + 1, yycolumn + 1);}
"(" {return new Token(TokenType.L_PARENTH, yytext(), yyline + 1, yycolumn + 1);}
")" {return new Token(TokenType.R_PARENTH, yytext(), yyline + 1, yycolumn + 1);}
"if" {return new Token(TokenType.IF, yytext(), yyline + 1, yycolumn + 1);}
"then" {return new Token(TokenType.THEN, yytext(), yyline + 1, yycolumn + 1);}
"else" {return new Token(TokenType.ELSE, yytext(), yyline + 1, yycolumn + 1);}
"endif" {return new Token(TokenType.ENDIF, yytext(), yyline + 1, yycolumn + 1);}
"while" {return new Token(TokenType.WHILE, yytext(), yyline + 1, yycolumn + 1);}
"do" 	{return new Token(TokenType.DO, yytext(), yyline + 1, yycolumn + 1);}
"wend"	{return new Token(TokenType.WEND, yytext(), yyline + 1, yycolumn + 1);}
"read"	{return new Token(TokenType.READ, yytext(), yyline + 1, yycolumn + 1);}
"write" {return new Token(TokenType.WRITE, yytext(), yyline + 1, yycolumn + 1);}

"$"{LET}({LET}|{DIG})* {return new Token(TokenType.VAR, yytext(), yyline + 1, yycolumn + 1);}
{DIG}+ {return new Token(TokenType.INT, yytext(), yyline + 1, yycolumn + 1);}
. {return new Token(TokenType.ERROR, yytext(), yyline + 1, yycolumn + 1);}
<<EOF>> {return new Token(TokenType.EOF, yytext(), yyline + 1, yycolumn + 1);}