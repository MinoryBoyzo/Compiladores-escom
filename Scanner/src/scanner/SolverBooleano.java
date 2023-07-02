package scanner;

public class SolverBooleano {
/*
    private final Nodo nodo;
    private final TablaSimbolos tablaSimbolos;
    public SolverBooleano(Nodo nodo, TablaSimbolos tablaSimbolos) {
        this.nodo = nodo;
        this.tablaSimbolos = tablaSimbolos;
    }

    public Object resolverB(){
        return resolverB(nodo);
    }
    public Object resolverB(Nodo n){
        // No tiene hijos, es un operando
        if(n.getHijos() == null){
            if(n.getValue().tipo == TipoToken.NUMERO || n.getValue().tipo == TipoToken.CADENA || n.getValue().tipo == TipoToken.TRUE || n.getValue().tipo == TipoToken.FALSE ){
                return n.getValue().literal;
            }
            else if(n.getValue().tipo == TipoToken.IDENTIFICADOR){
                // Ver la tabla de símbolos
                String identificador = n.getValue().lexema;
                // Obtener el valor asociado al identificador desde la tabla de símbolos
                Object valor = tablaSimbolos.obtener(identificador);
                // Retornar el valor obtenido
                return valor;
            }
        }

        // Por simplicidad se asume que la lista de hijos del nodo tiene dos elementos
        // < <= > >= == != usualmente son los que estaran aqui
        Nodo primCon = n.getHijos().get(0);
        Nodo segCon = n.getHijos().get(1);
        //OPERANDOS DE LA PRIMERA CONDICION
        Nodo izq1 = primCon.getHijos().get(0);
        Nodo der1 = primCon.getHijos().get(1);
        //OPERANDOS DE LA SEGUNDA CONDICION
        Nodo izq2 = segCon.getHijos().get(0);
        Nodo der2 = segCon.getHijos().get(1);

        //Object resultadoIzquierdo = resolverR(izq);
        //Object resultadoDerecho = resolverR(der);

        if (resultadoIzquierdo instanceof Boolean && resultadoDerecho instanceof Boolean){
            switch (n.getValue().tipo) {
                case Y:
                    return ((Boolean)resultadoIzquierdo && (Boolean) resultadoDerecho);
                case O:
                    return ((Boolean)resultadoIzquierdo || (Boolean) resultadoDerecho);
            }
        } else {
            //ERROR EN LA DIFERENCIA DE TIPOS
            throw new RuntimeException("Error de tipos en operadores booleanos.");
        }

        return null;
    }*/
}
