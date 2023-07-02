package scanner;

public enum TipoToken {
    // Crear un tipoToken por palabra reservada
    // Palabras clave:
    IF,ELSE,SWITCH,CASE,DEFAULT,WHILE,DO,FOR,BREAK,CONTINUE,RETURN,            //CONTROL DE FLUJO
    CLASE,INTERFAZ,ENUM,EXTENDS,IMPLEMENTS,NEW,THIS,SUPER,STATIC,FINAL,ABSTRACT,//DECLARACION Y MODIFICACION DE VARIABLES
    PUBLIC,PRIVATE,PROTECTED,                                                   //DE ACCESO Y PROTECCION
    TRY,CATCH,FINALLY,THROW,THROWS,                                             //EXCEPCIONES
    NULL,TRUE,FALSE,                                                            //MANEJO DE MEMORIA
    PACKAGE,IMPORT,INSTANCEOF,                                                  //OBJETOS
    INT,DOUBLE,FLOAT,LONG,SHORT,BYTE,BOOLEAN,CHAR,VOID,STRING,                  //VARIABLES

    // Crear un tipoToken: identificador, una cadena y numero
    IDENTIFICADOR, //PARA NOMBRES DE FUNCIONES
    CADENA,     //CADENA DE CARACTERES ""
    NUMERO,    //NUMEROS 0,1..9


    NUMERONEG, //temporal


    //DEL LENGUAJE
    VAR,IMPRIMIR,FUN,

    // Crear un tipoToken por cada "Signo del lenguaje" (ver clase Scanner)
    PARENTESISABRE, PARENTESISCIERRA, //PARENTESIS ()
    LLAVEABRE, LLAVECIERRA,     //LLAVES {}
    CORCHETEABRE,CORCHETECIERRA,    //CORCHETES []
    COMA,   //COMA ,
    PUNTO,  //PUNTOS .
    PUNTOYCOMA, //PUNTO Y COMA ;
    DOBLEPUNTO, // :
    MULTIPLICACION, //*
    DIVISION, // "/"
    NEGACIONLOGICA, // !
    DIFERENTEDE, // !=
    MENORQUE, // <
    MENORIGUALQUE, // <=
    MAYORQUE, // >
    MAYORIGUALQUE, // >=
    SUMA, // +
    RESTA, // -
    MODULO, // %
    IGUAL, // =
    INCREMENTO, DECREMENTO, // ++ ó --

    IGUALA, // ==
    O,Y, // "||" , "&&"

    // Final de cadena
    EOF //TOKEN FINAL
}