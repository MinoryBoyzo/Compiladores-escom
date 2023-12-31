package scanner;
public class Arbol {
    private final Nodo raiz;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public void recorrer(TablaSimbolos tablaSimbolos) {
        for (Nodo n : raiz.getHijos()) {
            System.out.println("PRIMER NODO " + n.getValue().lexema);
            Token t = n.getValue();
            switch (t.tipo) {
                // OPERADORES ARITMETICOS
                case SUMA:
                case RESTA:
                case MULTIPLICACION:
                case DIVISION:
                    System.out.println("CASO ARITMETICOS");
                    // METODO DE ARITMETICOS
                    SolverAritmetico solver = new SolverAritmetico(n, tablaSimbolos);
                    Object res = solver.resolver();
                    System.out.println(res);
                    break;

                // RELACIONALES
                case MAYORQUE:
                case MAYORIGUALQUE:
                case MENORQUE:
                case MENORIGUALQUE:
                case IGUALA:
                case DIFERENTEDE:
                    System.out.println("CASO RELACIONALES");
                    // METODO DE RELACIONALES
                    SolverRelacional solverR = new SolverRelacional(n, tablaSimbolos);
                    Object res2 = solverR.resolverR();
                    System.out.println(res2);
                    break;

                // BOOLEANOS
                case Y:
                case O:
                    System.out.println("CASO AND O OR");
                    // METODO DE BOOLEANOS
                    SolverRelacional solverB = new SolverRelacional(n, tablaSimbolos);
                    Object res3 = solverB.resolverB();
                    System.out.println(res3);
                    break;

                case VAR:
                    System.out.println("CASO VAR");
                    // Aquí va la tabla de símbolos
                    Nodo variable = n;
                    if (variable.getValue().tipo == TipoToken.VAR) { //"="
                        Nodo id = variable.getHijos().get(0);
                        if (tablaSimbolos.existeIdentificador(id.getValue().lexema)) {
                            System.out.println("Variable ya declarada: " + id.getValue().lexema);
                        } else if (variable.getHijos().size() == 1) {
                            if (tablaSimbolos.existeIdentificador(id.getValue().lexema)) {
                                System.out.println("Variable ya declarada: " + id.getValue().lexema);
                            } else {
                                tablaSimbolos.asignar(id.getValue().lexema, null);
                            }
                        } else {
                            Nodo expresion = variable.getHijos().get(1);
                            SolverAritmetico solverAritmetico = new SolverAritmetico(expresion, tablaSimbolos);
                            Object valor = solverAritmetico.resolver();
                            tablaSimbolos.asignar(id.getValue().lexema, valor);
                        }
                    } else {
                        System.out.println("No hay var");
                    }
                    break;

                case IF:
                    // resolver if
                    Nodo si = n; //if
                    Nodo condicion = si.getHijos().get(0); //operandos relacionales < <= > >= == != ó || &&
                    if (condicion.getValue().tipo == TipoToken.Y || condicion.getValue().tipo == TipoToken.O) {
                        SolverRelacional solverCond = new SolverRelacional(condicion, tablaSimbolos);
                        Boolean resultado = (Boolean) solverCond.resolverB();
                        if (resultado) { //VERDADERO
                            System.out.println("RECORRER RAMA");
                            Nodo ifs = n; //LO QUE SIGUE DEL VERDADERO
                            Rama ramaIf = new Rama(ifs, tablaSimbolos);
                            ramaIf.recorrerR();
                        } else { //ELSE
                            if (n.getHijos().size() > 2) {
                                Nodo bloqueElse = n.getHijos().get(2);
                                //Se crea un arbol para el bloque de codigo else
                                Rama ramaElse = new Rama(bloqueElse, tablaSimbolos);
                                ramaElse.recorrerR();
                            }
                        }
                    } else {
                        SolverRelacional solverCondicion = new SolverRelacional(condicion, tablaSimbolos);

                        Boolean resultado = (Boolean) solverCondicion.resolverR();
                        System.out.println(resultado);

                        if (resultado) { //VERDADERO
                            System.out.println("RECORRER RAMA");
                            Nodo ifs = n; //LO QUE SIGUE DEL VERDADERO
                            Rama ramaIf = new Rama(ifs, tablaSimbolos);
                            ramaIf.recorrerR();
                        } else { //ELSE
                            if (n.getHijos().size() > 2) {
                                Nodo bloqueElse = n.getHijos().get(2);
                                //Se crea un arbol para el bloque de codigo else
                                Rama ramaElse = new Rama(bloqueElse, tablaSimbolos);
                                ramaElse.recorrerR();
                            }
                        }
                    }
                    break;

                case WHILE:
                    System.out.println("CASO WHILE");
                    // resolver while
                    Nodo mientras = n;
                    Nodo condicionW = mientras.getHijos().get(0);
                    Nodo izq = condicionW.getHijos().get(0);
                    Nodo der = condicionW.getHijos().get(1);

                    SolverRelacional solverCondicionW = new SolverRelacional(condicionW, tablaSimbolos);
                    Boolean resultadoW = (Boolean) solverCondicionW.resolverR();

                    while (resultadoW) {
                        System.out.println("RECORRE RAMA");
                        Nodo bloqueWhile = mientras;
                        Rama ramaWhile = new Rama(bloqueWhile, tablaSimbolos);
                        ramaWhile.recorrerR();
                    }
                    break;

                case FOR:
                    System.out.println("CASO FOR");
                    // resolver for
                    Nodo para = n;
                    System.out.println("for :" + para.getValue().lexema);
                    Nodo decl = para.getHijos().get(0);
                    Nodo cond = para.getHijos().get(1);
                    Nodo inc = para.getHijos().get(2);
                    System.out.println(decl.getValue().lexema);
                    System.out.println(decl.getValue().lexema);
                    System.out.println(decl.getValue().lexema);
                    // NO GENERA POSTFIJA
                    break;

                case IMPRIMIR:
                    // resolver print
                    Nodo expresionImprimir = n.getHijos().get(0);

                    // Resolver la expresión utilizando el SolverAritmetico
                    SolverAritmetico solverAritmetico = new SolverAritmetico(expresionImprimir, tablaSimbolos);
                    Object resultado = solverAritmetico.resolver();
                    if (resultado != null) {
                        String resultadoImpresion = resultado.toString();
                        System.out.println(resultadoImpresion);
                    } else {
                        throw new RuntimeException("Error al resolver la expresión de impresión.");
                    }
                    break;
            }
        }
    }
}
