package repeticion;
import java.util.*;

class CRC {
    public static void main(String args[]) {
	Scanner scan = new Scanner(System.in);
	int n;
	//introducir la entrada
	System.out.println("Introducir el tamaño del dato:");
	n = scan.nextInt();
	int data[] = new int[n];
	System.out.println("Introducir el dato, bit por bit:");
	for(int i=0 ; i < n ; i++) {
            System.out.println("Introducir el bit numero " + (n-i) + ":");
            data[i] = scan.nextInt();
	}
	//introducir el divisor
	System.out.println("Introducir el tamaño del divisor:");
	n = scan.nextInt();
	int divisor[] = new int[n];
	System.out.println("Introducir el dato, bit por bit:");
	for(int i=0 ; i < n ; i++) {
            System.out.println("Introducir el bit numero " + (n-i) + ":");
            divisor[i] = scan.nextInt();
	}
	// Divide el dato con el divisor,se almacena en este pedazo de codigo para retornarlo al metodo	
	int remainder[] = divide(data, divisor);
	for(int i=0 ; i < remainder.length-1 ; i++) {
            System.out.print(remainder[i]);
	}
	System.out.println("\nEl codigo CRC generado es:");
	for(int i=0 ; i < data.length ; i++) {
            System.out.print(data[i]);
	}
	for(int i=0 ; i < remainder.length-1 ; i++) {
            System.out.print(remainder[i]);
	}
	System.out.println();
	// Se crea un nuevo array donde el residuo generado se corrobora con el introducido
	int sent_data[] = new int[data.length + remainder.length - 1];
	System.out.println("Introducir el dato a enviar:");
	for(int i=0 ; i < sent_data.length ; i++) {
            System.out.println("Introducir el bit numero" + (sent_data.length-i)+ ":");
		sent_data[i] = scan.nextInt();
	}
	receive(sent_data, divisor);
    }
	
    static int[] divide(int old_data[], int divisor[]) {
	int remainder[] , i;
	int data[] = new int[old_data.length + divisor.length];
	System.arraycopy(old_data, 0, data, 0, old_data.length);
	remainder = new int[divisor.length];
	System.arraycopy(data, 0, remainder, 0, divisor.length);
	// En este ciclo es uan exor para el residuo producto de la division
	for(i=0 ; i < old_data.length ; i++) {
            System.out.println((i+1) + ".) Primer bit del dato es : "+ remainder[0]);
            System.out.print("Residuo : ");
            if(remainder[0] == 1) {
            // El exor de los bit de residuo con los bits del divisor
		for(int j=1 ; j < divisor.length ; j++) {
                    remainder[j-1] = exor(remainder[j], divisor[j]);
                    System.out.print(remainder[j-1]);
		}
            }
            else {
		//El exor de los bits de residuo 0
		for(int j=1 ; j < divisor.length ; j++) {
                    remainder[j-1] = exor(remainder[j], 0);
			System.out.print(remainder[j-1]);
		}
            }
            // Se obtiene el acarreamiento de la operacion
            remainder[divisor.length-1] = data[i+divisor.length];
            System.out.println(remainder[divisor.length-1]);
	}
    return remainder;
    }
    
    static int exor(int a, int b) {
	// retorna el valor de dos bits por la exor
	if(a == b) {
            return 0;
	}
    return 1;
    }

    static void receive(int data[], int divisor[]) {
	// El metodo de recepcion
	int remainder[] = divide(data, divisor);
	// la division se completa
	for(int i=0 ; i < remainder.length ; i++) {
            if(remainder[i] != 0) {
		// Si el residuo no es cero hubo un error 
		System.out.println("Los datos fueron enviados con un error.");
		return;
            }
	}
	//Si no lo hubo se captados de manera adecuada
	System.out.println("Los datos fueron enviados sin ningun error.");
    }
}