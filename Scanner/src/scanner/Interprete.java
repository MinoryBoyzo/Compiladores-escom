package scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Interprete {
    static boolean existenErrores = false;
    public static TablaSimbolos tablaSimbolos;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Uso correcto: interprete [script]");
            // Convención definida en el archivo "system.h" de UNIX
            System.exit(64);
        } else if (args.length == 1) {
            ejecutarArchivo(args[0]);
        } else {
            ejecutarPrompt();
        }
    }

    private static void ejecutarArchivo(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()));

        // Se indica que existe un error
        if (existenErrores)
            System.exit(65);
    }

    private static void ejecutarPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print(">>> ");
            String linea = reader.readLine();
            if (linea == null)
                break; // Presionar Ctrl + D
            ejecutar(linea);
            existenErrores = false;
        }
    }

    private static void ejecutar(String source) {
        // LEXICO
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // SINTACTICO
        Desendente Program = new Desendente(tokens);
        Program.Program();

        // SEMANTICO
        tablaSimbolos = new TablaSimbolos();

        GeneradorPosfija pfija = new GeneradorPosfija(tokens);
        List<Token> postfija = pfija.convertir();

        GeneradorAST gast = new GeneradorAST(postfija);
        Arbol programa = gast.generarAST();

        if (programa == null) {
            System.out.println("No hay árbol");
        } else {
            programa.recorrer(tablaSimbolos); // ejecuta el método recorrer
        }
    }

    static void error(int linea, String mensaje) {
        reportar(linea, "", mensaje);
    }

    private static void reportar(int linea, String donde, String mensaje) {
        System.err.println("[linea " + linea + "] Error " + donde + ": " + mensaje);
        existenErrores = true;
    }
}

