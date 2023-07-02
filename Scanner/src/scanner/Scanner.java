package scanner;

//LIBRERIAS
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//CLASE SCANNER MAIN
public class Scanner {

    //VARIABLES DE ENTRADA
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("if",  TipoToken.IF);
        palabrasReservadas.put("else",  TipoToken.ELSE);
        palabrasReservadas.put("switch",  TipoToken.SWITCH);
        palabrasReservadas.put("case",  TipoToken.CASE);
        palabrasReservadas.put("default",  TipoToken.DEFAULT);
        palabrasReservadas.put("while",  TipoToken.WHILE);
        palabrasReservadas.put("do",  TipoToken.DO);
        palabrasReservadas.put("for",  TipoToken.FOR);
        palabrasReservadas.put("break",  TipoToken.BREAK);
        palabrasReservadas.put("continue",  TipoToken.CONTINUE);
        palabrasReservadas.put("return",  TipoToken.RETURN);
        palabrasReservadas.put("class",  TipoToken.CLASE);
        palabrasReservadas.put("interface",  TipoToken.INTERFAZ);
        palabrasReservadas.put("enum",  TipoToken.ENUM);
        palabrasReservadas.put("extends",  TipoToken.EXTENDS);
        palabrasReservadas.put("implements",  TipoToken.IMPLEMENTS);
        palabrasReservadas.put("new",  TipoToken.NEW);
        palabrasReservadas.put("this",  TipoToken.THIS);
        palabrasReservadas.put("super",  TipoToken.SUPER);
        palabrasReservadas.put("static",  TipoToken.STATIC);
        palabrasReservadas.put("final",  TipoToken.FINAL);
        palabrasReservadas.put("abstract",  TipoToken.ABSTRACT);
        palabrasReservadas.put("public",  TipoToken.PUBLIC);
        palabrasReservadas.put("private",  TipoToken.PRIVATE);
        palabrasReservadas.put("protected",  TipoToken.PROTECTED);
        palabrasReservadas.put("try",  TipoToken.TRY);
        palabrasReservadas.put("catch",  TipoToken.CATCH);
        palabrasReservadas.put("finally",  TipoToken.FINALLY);
        palabrasReservadas.put("throws",  TipoToken.THROWS);
        palabrasReservadas.put("null",  TipoToken.NULL);
        palabrasReservadas.put("true",  TipoToken.TRUE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("package",  TipoToken.PACKAGE);
        palabrasReservadas.put("import",  TipoToken.IMPORT);
        palabrasReservadas.put("instanceof",  TipoToken.INSTANCEOF);
        palabrasReservadas.put("int",  TipoToken.INT);
        palabrasReservadas.put("double",  TipoToken.DOUBLE);
        palabrasReservadas.put("float",  TipoToken.FLOAT);
        palabrasReservadas.put("long",  TipoToken.LONG);
        palabrasReservadas.put("short",  TipoToken.SHORT);
        palabrasReservadas.put("byte",  TipoToken.BYTE);
        palabrasReservadas.put("boolean",  TipoToken.BOOLEAN);
        palabrasReservadas.put("char",  TipoToken.CHAR);
        palabrasReservadas.put("void",  TipoToken.VOID);
        palabrasReservadas.put("String",  TipoToken.STRING);
        palabrasReservadas.put("and",  TipoToken.Y);
        palabrasReservadas.put("or",  TipoToken.O);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("print", TipoToken.IMPRIMIR);
        palabrasReservadas.put("fun", TipoToken.FUN);
    }

    Scanner(String source){
        this.source = source+" ";
    }

    List<Token> scanTokens(){
        int estado = 0;
        String lexema = "";
        int inicioLexema = 0;
        char car;

        for(int i = 0; i<source.length();i++){
            car = source.charAt(i);
            switch(estado)
            {
                case 0: //OPERADORES
                    switch(car){
                        case '-':
                            if (car == '-') {
                                // Verificar si hay otro '/' consecutivo
                                if (i + 1 < source.length() && source.charAt(i + 1) == '-') {
                                    tokens.add(new Token(TipoToken.DECREMENTO, "--"));
                                    i++;
                                } else if (i + 1 < source.length() && source.charAt(i + 1) != '-') {
                                    tokens.add(new Token(TipoToken.RESTA, "-"));
                                }
                            }
                            //i++;
                            break;
                        case '+':
                            if (car == '+') {
                                // Verificar si hay otro '/' consecutivo
                                if (i + 1 < source.length() && source.charAt(i + 1) == '+') {
                                    tokens.add(new Token(TipoToken.INCREMENTO, "++"));
                                    i++;
                                } else if (i + 1 < source.length() && source.charAt(i + 1) != '+') {
                                    tokens.add(new Token(TipoToken.SUMA, "+"));
                                }
                            }
                            //i++;
                            break;
                        case '*':
                            tokens.add(new Token(TipoToken.MULTIPLICACION, "*"));
                            break;
                        case '/':
                            if (car == '/') {
                                // Verificar si hay otro '/' consecutivo
                                if (i + 1 < source.length() && source.charAt(i + 1) == '/') {
                                    //tokens.add(new Token(TipoToken.DIVISION, "/", inicioLexema + 1));
                                    estado = 4; // Mover al caso 5 para reconocer comentarios
                                    i++; // Avanzar al siguiente carácter
                                } else if(i + 1 < source.length() && source.charAt(i + 1) == '*'){
                                    estado = 4; // Mover al caso 5 para reconocer comentarios
                                    i++; // Avanzar al siguiente carácter
                                }else{
                                    tokens.add(new Token(TipoToken.DIVISION,"/"));
                                }
                            }
                            break;
                        case '%':
                            tokens.add(new Token(TipoToken.MODULO,"%"));
                            break;
                        case '=':
                            if (car == '=') {
                                // Verificar si hay otro '/' consecutivo
                                if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                                    tokens.add(new Token(TipoToken.IGUALA, "==",null));
                                    i++;
                                } else if (i + 1 < source.length() && source.charAt(i + 1) != '=') {
                                    tokens.add(new Token(TipoToken.IGUAL, "=",null));
                                }
                            }

                            //tokens.add(new Token(TipoToken.IGUAL,"=",i+1));
                            break;
                        case '<':
                            if (car == '<') {
                                // Verificar si hay otro '/' consecutivo
                                if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                                    tokens.add(new Token(TipoToken.MENORIGUALQUE, "<=",null));
                                    i++;
                                } else if (i + 1 < source.length() && source.charAt(i + 1) != '=') {
                                    tokens.add(new Token(TipoToken.MENORQUE, "<",null));
                                }
                            }

                            //tokens.add(new Token(TipoToken.MENORQUE,"<",i+1));
                            break;
                        case '>':
                            if (car == '>') {
                                // Verificar si hay otro '/' consecutivo
                                if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                                    tokens.add(new Token(TipoToken.MAYORIGUALQUE, ">=",null));
                                    i++;
                                } else if (i + 1 < source.length() && source.charAt(i + 1) != '=') {
                                    tokens.add(new Token(TipoToken.MAYORQUE, ">",null));
                                }
                            }
                            //i++;
                            //tokens.add(new Token(TipoToken.MAYORQUE,">",i+1));
                            break;
                        case '!':
                            if (car == '!') {
                                // Verificar si hay otro '/' consecutivo
                                if (i + 1 < source.length() && source.charAt(i + 1) == '=') {
                                    tokens.add(new Token(TipoToken.DIFERENTEDE, "!=",null));
                                    i++;
                                } else if (i + 1 < source.length() && source.charAt(i + 1) != '=') {
                                    tokens.add(new Token(TipoToken.NEGACIONLOGICA, "-"));
                                }
                            }
                            //i++;
                            //tokens.add(new Token(TipoToken.NEGACIONLOGICA,"!",i+1));
                            break;

                        //VARIANTES A OTROS ESTADOS
                        //Cadena
                        case '"':
                            estado = 1;
                            lexema = lexema+car;
                            inicioLexema=i;
                            break;
                        //SIMBOLOS DEL LENGUAJE
                        case '(':
                            tokens.add(new Token(TipoToken.PARENTESISABRE,"("));
                            break;
                        case '[':
                            tokens.add(new Token(TipoToken.CORCHETEABRE,"["));
                            break;
                        case '{':
                            tokens.add(new Token(TipoToken.LLAVEABRE,"{"));
                            break;
                        case ')':
                            tokens.add(new Token(TipoToken.PARENTESISCIERRA,")"));
                            break;
                        case ']':
                            tokens.add(new Token(TipoToken.CORCHETECIERRA,"]"));
                            break;
                        case '}':
                            tokens.add(new Token(TipoToken.LLAVECIERRA,"}"));
                            break;
                        case ',':
                            tokens.add(new Token(TipoToken.COMA,","));
                            break;
                        case '.':
                            tokens.add(new Token(TipoToken.PUNTO,"."));
                            break;
                        case ';':
                            tokens.add(new Token(TipoToken.PUNTOYCOMA,";"));
                            break;
                        case ':':
                            tokens.add(new Token(TipoToken.DOBLEPUNTO,":"));
                            break;

                    }
                    if(Character.isAlphabetic(car)){
                        estado = 2;
                        lexema=lexema+car;
                        inicioLexema = i;
                    } else if(Character.isDigit(car)) {
                        lexema=lexema+car;
                        estado = 3;

                        inicioLexema = i;
                    }
                    break;
                case 1://Cadenas
                    if (car != '"') {
                        lexema = lexema + car;
                    } else {
                        lexema = lexema + car;
                        tokens.add(new Token(TipoToken.CADENA, lexema,String.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        inicioLexema = 0;
                    }
                    break;
                case 2 : //Reservadas
                    if(Character.isAlphabetic(car) || Character.isDigit(car)){
                        lexema=lexema+car;
                    } else {
                        TipoToken tt = palabrasReservadas.get(lexema);
                        if(tt ==  null){
                            tokens.add(new Token(TipoToken.IDENTIFICADOR,lexema));
                        } else {
                            tokens.add(new Token(tt,lexema));
                        }
                        estado = 0;
                        lexema = "";
                        inicioLexema=0;
                        i--;
                    }

                    break;
                case 3: //Numeros
                    if(Character.isDigit(car)){
                        estado=3;
                        lexema=lexema+car;
                    } else if( car == '.'){
                        estado=6;
                        lexema=lexema+car;
                    } else if (car == 'E'){
                        estado=7;
                        lexema=lexema+car;
                    } else {
                        tokens.add(new Token(TipoToken.NUMERO,lexema,Double.valueOf(lexema)));
                        estado=0;
                        lexema="";
                        i--;
                    }
                    break;
                case 4: // Comentarios
                    if (estado == 4) { // Estado para comentarios
                        if (car == '*' && source.charAt(i + 1) == '/') {
                            estado = 0; // Se sale del comentario
                            i++; // Avanza el índice para omitir el siguiente caracter '/'
                        }
                    } else {
                        if (car != '\n') {
                            lexema = lexema + car;
                        } else if (car == '/' && source.charAt(i + 1) == '*') {
                            estado = 4; // Se inicia un comentario tipo '/*'
                            i++; // Avanza el índice para omitir el siguiente caracter '*'
                        } else {
                            lexema = lexema + car;
                        }
                    }
                    break;
                    //continuacion numeros
                case 6:
                    if(Character.isDigit(car)){
                        estado = 8;
                        lexema = lexema + car;
                    }
                    else{
                        throw new RuntimeException("Error de tipos en operadores artimeticos.");
                    }
                    break;
                case 8:
                    if(Character.isDigit(car)){
                        estado = 8;
                        lexema = lexema + car;
                    }
                    else if(car == 'E'){
                        estado = 7;
                        lexema = lexema + car;
                    }
                    else{
                        tokens.add(new Token(TipoToken.NUMERO, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
                case 7:
                    if(car == '+' || car == '-'){
                        estado = 9;
                        lexema = lexema + car;
                    }
                    else if(Character.isDigit(car)){
                        estado = 10;
                        lexema = lexema + car;
                    }
                    else{
                        throw new RuntimeException("Error de tipos en operadores artimeticos.");
                    }
                    break;
                case 9:
                    if(Character.isDigit(car)){
                        estado = 10;
                        lexema = lexema + car;
                    }
                    else{
                        throw new RuntimeException("Error de tipos en operadores artimeticos.");
                    }
                    break;
                case 10:
                    if(Character.isDigit(car)){
                        estado = 10;
                        lexema = lexema + car;
                    }
                    else{
                        tokens.add(new Token(TipoToken.NUMERO, lexema, Double.valueOf(lexema)));
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    break;
            }
        }
        tokens.add(new Token(TipoToken.EOF, "",  source.length())); //Fin del archivo
        return tokens;
    }
}

/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */