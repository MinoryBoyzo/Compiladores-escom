package scanner;

public class SolverAritmetico {

    private final Nodo nodo;
    private final TablaSimbolos tablaSimbolos;

    public SolverAritmetico(Nodo nodo, TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos; //obtiene la tabla de simbolos existente
        this.nodo = nodo; //obtiene de parametro un nodo
    }

    public Object resolver(){
        return resolver(nodo); //va a retornar un objeto
    }
    private Object resolver(Nodo n){
        //boolean existe = false;
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

            Object resultadoIzquierdo = resolver(izq);
            Object resultadoDerecho = resolver(der);

            if(resultadoIzquierdo instanceof Double && resultadoDerecho instanceof Double){
                switch (n.getValue().tipo){
                    case SUMA:
                        return ((Double)resultadoIzquierdo + (Double) resultadoDerecho);
                    case RESTA:
                        return ((Double)resultadoIzquierdo - (Double) resultadoDerecho);
                    case MULTIPLICACION:
                        return ((Double)resultadoIzquierdo * (Double) resultadoDerecho);
                    case DIVISION:
                        return ((Double)resultadoIzquierdo / (Double) resultadoDerecho);
                }
            } else if(resultadoIzquierdo instanceof String && resultadoDerecho instanceof String){
                if (n.getValue().tipo == TipoToken.SUMA){
                    // Ejecutar la concatenación
                    String cadIzq = resultadoIzquierdo.toString();
                    String cadDer = resultadoDerecho.toString();

                    // Verificar y eliminar las comillas al principio y al final si existen
                    if (cadIzq.startsWith("\"")) {
                        cadIzq = cadIzq.substring(1);
                    }
                    if (cadIzq.endsWith("\"")) {
                        cadIzq = cadIzq.substring(0, cadIzq.length() - 1);
                    }
                    if (cadDer.startsWith("\"")) {
                        cadDer = cadDer.substring(1);
                    }
                    if (cadDer.endsWith("\"")) {
                        cadDer = cadDer.substring(0, cadDer.length() - 1);
                    }

                    String concatenación = cadIzq + cadDer;
                    return concatenación;
                }
            } else {
                // Error por diferencia de tipos
                throw new RuntimeException("Error de tipos en operadores artimeticos.");
            }
            return null;
        }
}

