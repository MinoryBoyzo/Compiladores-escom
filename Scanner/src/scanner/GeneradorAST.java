package scanner;


import java.util.List;
import java.util.Stack;

public class GeneradorAST {

    private final List<Token> postfija;
    private final Stack<Nodo> pila;

    public GeneradorAST(List<Token> postfija){
        this.postfija = postfija;
        this.pila = new Stack<>(); //pila auxiliar
    }

    public Arbol generarAST() {
        Stack<Nodo> pilaPadres = new Stack<>(); //pila de padres
        Nodo raiz = new Nodo(null); //se crea la raiz
        pilaPadres.push(raiz); //la raiz se va a la pila padres

        Nodo padre = raiz; //raiz es el primer padre

        for(Token t : postfija){ // se recorren los tokens por postfija
            //FINAL DE CADENA "EOF" no se toma en cuenta
            if(t.tipo == TipoToken.EOF){
                break;
            }
            //PALABRAS RESERVADAS "VAR, IF, IMPRIMIR, WHILE, FOR"
            if(t.esPalabraReservada()){
                Nodo n = new Nodo(t); //crea nodo

                padre = pilaPadres.peek(); //raiz
                padre.insertarSiguienteHijo(n); //alguna estructura de control

                pilaPadres.push(n); //se incluye en la pila la estructura de control
                padre = n; //ahora esa estructura es un padre

            }
            // OPERANDOS "IDENTIFICADOR, NUMERO, CADENA, TRUE, FALSE"
            else if(t.esOperando()){
                Nodo n = new Nodo(t); //crea nodo
                pila.push(n); //lo incluye en la pila auxiliar
            }
            // OPERADORES Aritmeticos: SUMA, RESTA, MULTIPLICACION, DIVISION.
            // OPERADORES Relacionales: IGUAL, IGUALA, MAYORQUE, MAYORIGUALQUE,MENORQUE,MENORIGUALQUE,DIFERENTEDE
            // OPERADORES Booleanos: Y, O
            else if(t.esOperador()){
                int aridad = t.aridad(); //obtiene la aridad que es 2
                Nodo n = new Nodo(t); //crea nodo
                for(int i=1; i<=aridad; i++){ //2 ciclos, dos hijos generados
                    Nodo nodoAux = pila.pop(); //quita un nodo de la pila
                    n.insertarHijo(nodoAux); //lo pone como hijo de "n" en la posicion 0 siempre
                }
                pila.push(n); // ahora pone esa ramificacion de nuevo en la pila, esta tendra 2 hijos
            }
            else if(t.tipo == TipoToken.PUNTOYCOMA){
                if (pila.isEmpty()){
                    /*
                    Si la pila esta vacía es porque t es un punto y coma
                    que cierra una estructura de control
                     */
                    pilaPadres.pop(); //elimina el ultimo nodo insertado en la pila en la var pilapadres
                    padre = pilaPadres.peek(); // esta estructura de control se añade a la pila padres desde el primer nodo con peek
                }
                else{ //De lo contrario es una asignacion como ejemplo "var a=0 o print a;"
                    Nodo n = pila.pop(); //el nodo obtiene el ultimo valor en la pila auxiliar

                    if(padre.getValue().tipo == TipoToken.VAR){ //si en la pila padres es una reservada var
                        /*
                        En el caso del VAR, es necesario eliminar el igual que
                        pudiera aparecer en la raíz del nodo n.
                         */
                        if(n.getValue().tipo == TipoToken.IGUAL){ //si en la pila aux existe un "="
                            padre.insertarHijos(n.getHijos());// en la pila padres se insertan las listas de hijos que tenga n
                        }
                        else{ //de lo contrario en la pila padres solo se insetan más hijos +1 en la lista o se crea una
                            padre.insertarSiguienteHijo(n);
                        }
                        pilaPadres.pop(); // se envia la pila padres a la raiz
                        padre = pilaPadres.peek(); //conexion a la raiz
                    }
                    else if(padre.getValue().tipo == TipoToken.IMPRIMIR){ //si en la pila padres es una reservada IMPRIMIR
                        padre.insertarSiguienteHijo(n); //se inserta un nuevo hijo n en la raiz
                        pilaPadres.pop(); //se retoma el utlimo elemento de la pila insertado
                        padre = pilaPadres.peek(); //y se devuelve a la raiz
                    }
                    else { //de lo contrario se inserta en la raiz el nodo n como estructura reservada
                        padre.insertarSiguienteHijo(n);
                    }
                }
            }
        }

        // Suponiendo que en la pila sólamente queda un nodo
        // Nodo nodoAux = pila.pop();
        Arbol programa = new Arbol(raiz); //se envia toda la raiz
        return programa; //regresa un objeto tipo arbol
    }
}
