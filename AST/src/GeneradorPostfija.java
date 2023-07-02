import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GeneradorPostfija {

    private final List<Token> infija;
    private final Stack<Token> pila;
    private final List<Token> postfija;

    public GeneradorPostfija(List<Token> infija) {
        this.infija = infija;
        this.pila = new Stack<>();
        this.postfija = new ArrayList<>();
    }

    public List<Token> convertir(){
        boolean estructuraDeControl = false;
        Stack<Token> pilaEstructurasDeControl = new Stack<>();

        for(int i=0; i<infija.size(); i++){
            Token t = infija.get(i);

            if(t.tipo == TipoToken.EOF){
                break;
            }

            if(t.esPalabraReservada()){
                /*
                 Si el token actual es una palabra reservada, se va directo a la
                 lista de salida.
                 */
                postfija.add(t);
                if (t.esEstructuraDeControl()){
                    estructuraDeControl = true;
                    pilaEstructurasDeControl.push(t);
                }
            }
            else if(t.esOperando()){
                postfija.add(t);
            }
            else if(t.tipo == TipoToken.LPAREN){
                pila.push(t);
            }
            else if(t.tipo == TipoToken.RPAREN){
                while(!pila.isEmpty() && pila.peek().tipo != TipoToken.LPAREN){
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                if(pila.peek().tipo == TipoToken.LPAREN){
                    pila.pop();
                }

                // Esta sección de aquí es para manejar el ")" que cierra la
                // condición de la estructura de control
                if(estructuraDeControl && infija.get(i + 1).tipo == TipoToken.LBRACE){
                    postfija.add(new Token(TipoToken.SEMICOLON, ";", null));
                }
            }
            else if(t.esOperador()){
                while(!pila.isEmpty() && pila.peek().precedenciaMayorIgual(t)){
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                pila.push(t);
            }
            else if(t.tipo == TipoToken.SEMICOLON){
                while(!pila.isEmpty() && pila.peek().tipo != TipoToken.LBRACE){
                    Token temp = pila.pop();
                    postfija.add(temp);
                }
                postfija.add(t);
            }
            else if(t.tipo == TipoToken.LBRACE){
                // Se mete a la pila, tal como el parentesis. Este paso
                // pudiera omitirse, sólo hay que tener cuidado en el manejo
                // del "}".
                pila.push(t);
            }
            else if(t.tipo == TipoToken.RBRACE && estructuraDeControl){

                // Primero verificar si hay un else:
                if(infija.get(i + 1).tipo == TipoToken.ELSE){
                    // Sacar el "{" de la pila
                    pila.pop();
                }
                else{
                    // En este punto, en la pila sólo hay un token: "{"
                    // El cual se extrae y se añade un ";" a cadena postfija,
                    // El cual servirá para indicar que se finaliza la estructura
                    // de control.
                    pila.pop();
                    postfija.add(new Token(TipoToken.SEMICOLON, ";", null));

                    // Se extrae de la pila de estrucuras de control, el elemento en el tope
                    Token aux = pilaEstructurasDeControl.pop();

                    /*
                        Si se da este caso, es necesario extraer el IF de la pila
                        pilaEstructurasDeControl, y agregar los ";" correspondientes
                     */
                    if(aux.tipo == TipoToken.ELSE){
                        pilaEstructurasDeControl.pop();
                        postfija.add(new Token(TipoToken.SEMICOLON, ";", null));
                    }
                    if(pilaEstructurasDeControl.isEmpty()){
                        estructuraDeControl = false;
                    }
                }


            }
        }
        while(!pila.isEmpty()){
            Token temp = pila.pop();
            postfija.add(temp);
        }

        while(!pilaEstructurasDeControl.isEmpty()){
            pilaEstructurasDeControl.pop();
            postfija.add(new Token(TipoToken.SEMICOLON, ";", null));
        }

        return postfija;
    }

}