package interpreter;
import static interpreter.Token.*;
import static interpreter.TokenType.*;
%%
%class Lexer
%type String
L = [a-zA-Z_]
D = [0-9]
WHITE=[ \t\r\n]
%%
{WHITE} {/*Ignore*/}
"=" {return yytext();}
"+" {return yytext();}
"*" {return yytext();}
"-" {return yytext();}
"/" {return yytext();}
"%" {return yytext();}
"<" {return yytext();}
">" {return yytext();}
"<=" {return yytext();}
">=" {return yytext();}
"==" {return yytext();}
"!=" {return yytext();}
";" {return yytext();}
"(" {return yytext();}
")" {return yytext();}
"if" {return yytext();}
"then" {return yytext();}
"else" {return yytext();}
"endif" {return yytext();}
"while" {return yytext();}
"do" 	{return yytext();}
"wend"	{return yytext();}
"read"	{return yytext();}
"write" {return yytext();}

"$"{L}({L}|{D})* {return yytext();}
"-"?{D}+ {return yytext();}
. {return yytext();}