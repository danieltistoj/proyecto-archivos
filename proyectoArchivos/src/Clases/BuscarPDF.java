import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int fila = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad de filas y columnas xd")) ;

        int [][] tablero = new int[fila][fila];

        for (int i =0; i<fila; i++){
            for (int j=0; j<fila; j++){

                if (i==0 || (i == (fila-1)) || (j==0 || (j == (fila-1)))){
                    tablero[i][j] = 1;
                }else  {
                    tablero[i][j] = 0;
                }
            }
            System.out.println();
        }

        for (int i =0; i<fila; i++){
            for (int j=0; j<fila; j++){
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }



    }
}
