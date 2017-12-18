package interpreter;
import static interpreter.CustomToken.*;
import static interpreter.Token.*;
%%
%class Lexer
%type CustomToken
L = [a-zA-Z_]
D = [0-9]
WHITE=[ \t\r\n]
%%
{WHITE} {/*Ignore*/}
"=" {return new CustomToken(Token.ASSIGN, yytext());}
"+" {return new CustomToken(Token.PLUS, yytext());}
"*" {return new CustomToken(Token.MULT, yytext());}
"-" {return new CustomToken(Token.MINUS, yytext());}
"/" {return new CustomToken(Token.DIV, yytext());}
"%" {return new CustomToken(Token.MOD, yytext());}
"<" {return new CustomToken(Token.LEAST, yytext());}
">" {return new CustomToken(Token.GREATER, yytext());}
"<=" {return new CustomToken(Token.LEAST_EQUAL, yytext());}
">=" {return new CustomToken(Token.GREATER_EQUAL, yytext());}
"==" {return new CustomToken(Token.EQUAL, yytext());}
"!=" {return new CustomToken(Token.DIF, yytext());}
";" {return new CustomToken(Token.SEMICOLON, yytext());}
"(" {return new CustomToken(Token.L_BRACKET, yytext());}
")" {return new CustomToken(Token.R_BRACKET, yytext());}
"if" {return new CustomToken(Token.IF, yytext());}
"then" {return new CustomToken(Token.THEN, yytext());}
"else" {return new CustomToken(Token.ELSE, yytext());}
"endif" {return new CustomToken(Token.ENDIF, yytext());}
"while" {return new CustomToken(Token.WHILE, yytext());}
"do" 	{return new CustomToken(Token.DO, yytext());}
"wend"	{return new CustomToken(Token.WEND, yytext());}
"read"	{return new CustomToken(Token.READ, yytext());}
"write" {return new CustomToken(Token.WRITE, yytext());}

"$"{L}({L}|{D})* {return new CustomToken(Token.VAR, yytext());}
("(-"{D}+")")|{D}+ {return new CustomToken(Token.INT, yytext());}
. {return new CustomToken(Token.ERROR, yytext());}