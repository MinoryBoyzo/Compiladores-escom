package scanner;

public class SolverRelacional {

    private final Nodo nodo;
    private final TablaSimbolos tablaSimbolos;
    public SolverRelacional(Nodo nodo, TablaSimbolos tablaSimbolos) {
        this.nodo = nodo;
        this.tablaSimbolos = tablaSimbolos;
    }

    public Object resolverR(){
        return resolverR(nodo);
    }

    public Object resolverB(){
        return resolverB(nodo);
    }

    private Object resolverR(Nodo n){
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
        Nodo izq = n.getHijos().get(0);
        Nodo der = n.getHijos().get(1);

        Object resultadoIzquierdo = resolverR(izq);
        Object resultadoDerecho = resolverR(der);

        if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
            switch (n.getValue().tipo){
                case MAYORQUE:
                    return ((Double)resultadoIzquierdo > (Double)resultadoDerecho);
                case MAYORIGUALQUE:
                    return ((Double)resultadoIzquierdo >= (Double)resultadoDerecho);
                case MENORQUE:
                    return ((Double)resultadoIzquierdo < (Double)resultadoDerecho);
                case MENORIGUALQUE:
                    return ((Double)resultadoIzquierdo <= (Double)resultadoDerecho);
                case IGUALA:
                    return ((Double)resultadoIzquierdo == (Double)resultadoDerecho);
                case DIFERENTEDE:
                    return ((Double)resultadoIzquierdo != (Double)resultadoDerecho);
            }
        }
        else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
            switch (n.getValue().tipo){
                case IGUALA:
                    return resultadoIzquierdo.equals(resultadoDerecho);
                case DIFERENTEDE:
                    return !resultadoIzquierdo.equals(resultadoDerecho);
            }
        }
        else{
            // Error por diferencia de tipos
            throw new RuntimeException("Error de tipos en operadores relacionales.");
        }

        return null;
    }

    private Object resolverB(Nodo n){
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
        //System.out.println(primCon.getValue().lexema);
        //System.out.println(segCon.getValue().lexema);
        //OPERANDOS DE LA PRIMERA CONDICION
        Nodo izq1 = primCon.getHijos().get(0);
        Nodo der1 = primCon.getHijos().get(1);
        //OPERANDOS DE LA SEGUNDA CONDICION
        Nodo izq2 = segCon.getHijos().get(0);
        Nodo der2 = segCon.getHijos().get(1);

        Object resultadoIzquierdo = resolverR(primCon);
        Object resultadoDerecho = resolverR(segCon);

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
    }

}