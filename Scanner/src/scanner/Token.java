package scanner;

public class Token {

    final TipoToken tipo;
    final String lexema;
    final Object literal;

    public Token(TipoToken tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = null;
    }

    public Token(TipoToken tipo, String lexema, Object literal) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.literal = literal;
    }

    @Override
    public String toString() {
        return tipo + " " + lexema + " " + (literal == null ? "" : literal.toString());
    }




    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Token)) {
            return false;
        }

        Token otherToken = (Token) o;
        return this.tipo == otherToken.tipo;
    }

    //METODOS AUXILIARES
    public boolean esOperando(){
        switch (this.tipo){
            case IDENTIFICADOR:
            case NUMERO:
            case CADENA:
            case TRUE:
            case FALSE:
                return true;
            default:
                return false;
        }
    }

    public boolean esOperador(){
        switch (this.tipo){
            case SUMA: //ARITMETICOS
            case RESTA:
            case MULTIPLICACION:
            case DIVISION:
            case IGUAL: //RELACIONALES
            case IGUALA:
            case MAYORQUE:
            case MAYORIGUALQUE:
            case MENORQUE:
            case MENORIGUALQUE:
            case DIFERENTEDE:
            //BOOLEANOS
            case Y:
            case O:
                return true;
            default:
                return false;
        }
    }

    public boolean esPalabraReservada(){
        switch (this.tipo){
            case VAR:
            case IF:
            case IMPRIMIR:
            case ELSE:
            case WHILE:
            case FOR:
                return true;
            default:
                return false;
        }
    }

    public boolean esEstructuraDeControl(){
        switch (this.tipo){
            case IF:
            case ELSE:
            case FOR:
            case WHILE:
                return true;
            default:
                return false;
        }
    }

    public boolean precedenciaMayorIgual(Token t) {
        return this.obtenerPrecedencia() >= t.obtenerPrecedencia();
    }

    private int obtenerPrecedencia(){
        switch (this.tipo){
            //ARTIRMETICAS
            case MULTIPLICACION:
            case DIVISION:
                return 7;
            case SUMA:
            case RESTA:
                return 6;
            case IGUAL:
                return 1;
            //RELACIONALES
            case MAYORQUE:
            case MAYORIGUALQUE:
            case MENORIGUALQUE:
            case MENORQUE:
                return 5;
            case IGUALA:
            case DIFERENTEDE:
                return 4;
            //BOOLEANAS
            case Y:
                return 3;
            case O:
                return 2;
        }
        return 0;
    }

    public int aridad(){
        switch (this.tipo) {
            case MULTIPLICACION:
            case DIVISION:
            case SUMA:
            case RESTA:
            case IGUAL:
            case MAYORQUE:
            case MAYORIGUALQUE:
            case MENORIGUALQUE:
            case MENORQUE:
            case IGUALA:
            case Y:
            case O:
                return 2;
        }
        return 0;
    }

}
