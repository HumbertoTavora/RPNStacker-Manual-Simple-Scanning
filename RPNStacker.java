import stack.rpn.lexer.Token;
import stack.rpn.lexer.TokenType;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;



public class RPNStacker{

       
    private static Scanner scanner = new Scanner(System.in); // Função de leitura

    private static Stack<Integer> stack = new Stack<>();   // função de pilha

    public static void main(String[] args) {

        String file = "file.txt";       // Nome do arquivo de entrada.
        String input;
        int saida =0;                   // Inicializando a variavel de saida para evitar erro de compilação.
        try(BufferedReader br = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = br.readLine()) != null) {    // Pego cada linha do arquivo passado como parametro.
                input = line;
                
                if (isInteger(input)) {                 // Caso o input seja um inteiro, coloco na pilha como inteiro.
                    Token t = new Token(TokenType.NUM , input);
                    System.out.println(t.toString());
                    stack.push(Integer.parseInt(t.getLexeme()));
                } else if (isOperation(input)) {        // Caso o input seja um operador aritmetico, realizo a operação nos dois ultimos elementos da pilha e coloco o resultado nela.
                    int current = prepOperation(input, stack);
                    stack.push(current);
                    saida = current;                    // Atualizo o valor da saida.
                }
                else{
                    System.out.println("Error: Unexpected character:" + input);
                    break;
                    
                }
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");       // Erro ao abrir o arquivo.
            e.printStackTrace();
        }

        System.out.println(saida);
    }


    public static int prepOperation(String operation, Stack<Integer> stack) {
        int result;
        if(stack.empty()){      // Caso em que a pilha está vazia é retornado 0
            result = 0;
        }
        else{
            int aux = stack.pop();      // uso uma variavel aux, para passar os parametros para operate na ordem correta.
            result = operate(operation, stack.pop(), aux);
        }

        return result;
    }

    public static int operate(String operation, int a, int b) {     // Realiza a operação que é passada como parametro.
        switch (operation) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": {
                if (b == 0) {               // trata o caso da divisão por zero
                    System.out.println("Divisão por 0");
                    return a;
                }
                return a / b;
            }
            default: return a;
        }
    }

    public static boolean isInteger(String input) {
        if (input == null) return false;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isOperation(String input){
        
        if(input.equals("+")){
            Token t = new Token(TokenType.PLUS , input);
            System.out.println(t.toString());
            return true;
        }
        if(input.equals("-")){
            Token t = new Token(TokenType.MINUS , input);
            System.out.println(t.toString());
            return true;
        }
        if( input.equals("*") ){
            Token t = new Token(TokenType.STAR , input);
            System.out.println(t.toString());
            return true;
        }
        if(input.equals("/") ){
            Token t = new Token(TokenType.SLASH , input);
            System.out.println(t.toString());
            return true;
        }
        else{
            return false;
        }
    }
}

