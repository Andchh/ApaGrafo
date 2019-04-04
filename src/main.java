import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class main {
	public static int tam = 0;
	public static void main(String[] args) throws IOException {
	    
		String path = "C:/Users/Anderson/workspace/Grafos/src/dij10.txt";
		File file = new File(path); //aloca o arquivo passado como argumento
		//List<String> records = new ArrayList<String>();
		Scanner s = null;
		s = new Scanner(file);
		//BufferedReader reader = new BufferedReader(new FileReader(file));
		
		// pega a primeira linha para saber qual o tamanho da matriz quadrática, sendo: tam x tam
		tam = s.nextInt();
		System.out.println("tamanho da matriz: " + tam + " x " + tam);
		
		//salva todos os valores do txt em um array para tratar no futuro
		ArrayList<Integer> numeros = new ArrayList<Integer>();
		
		while(s.hasNextInt()){
			numeros.add(s.nextInt());
			//System.out.println(numeros);
		}
		
		int[] snumber = new int[numeros.size()];
		for(int i = 0; i < numeros.size(); i++){
			snumber[i] = numeros.get(i);
		}
		
		System.out.println("Numeros salvos");
		s.close();
				
		//criando e alocando os numeros à diagonal cima direita da matriz
		
		int[][] matriz = new int[tam][tam];
		
		
		//zerando a matriz
		zeroM(matriz, tam);
		//showmatriz(matriz,tam);
		
		
		/*for(int i = 0; i < tam; i++){ //linhas
			for(int j = 0; j < tam; j++){ //colunas
				matriz[i][j] = 0;
			}			
		}*/
		
		int count = 0;
		int coluna = count+1;
		int pulos = tam;
		for(int i = 0; i < tam; i++){ //linhas
			
			for(int j = 1; j < pulos ; j++){ //colunas
				matriz[i][coluna] = snumber[count];
				//System.out.printf("[" + matriz[i][j]+ "]");
				count++;
				coluna++;
//				System.out.println("count" + count);
			}
			pulos--;
			coluna = (coluna - pulos) + 1;
			//System.out.printf("\n");
		}
		System.out.println("Matriz preenchida:\n");
		
		//preenche a outra diagonal
		//pMatriz(matriz, tam);
		//showmatriz(matriz, tam);
		
		System.out.println("Digite 1 para Prim e 2 Para Dijkstra:");
		Scanner j = null;
		int escolha = j.nextInt();
		if(escolha == 1){
			Prim(matriz);
		}else if(escolha ==2){
			dij(matriz, n, comeco);
		}
		
		
	}
		
	public static void Prim(int[][] matriz){
		
		int[] parent = new int[tam];
		int[] key = new int[tam];
		boolean[] mSet  = new boolean[tam];
		
		for(int i = 0; i < tam; i++){
			key[i] = Integer.MAX_VALUE;
			mSet[i] = false;
		}
		
		key[0] = 0;
		parent[0] = 1;
		
		for(int i = 0; i < tam-1; i++){
			int u = minK(key,mSet,tam);
			mSet[u] = true;
			
			for(int j = 0; j < tam; j++){
				if(mSet[j] == false && matriz[u][j] < key[j]){
					parent[j] = u;
					key[j] = matriz[u][j];
					
				}
			}
			
		}

		printMST(parent, tam, matriz);
		

	}
	
	
	
	public static void dij(int matriz[][],int n, int comeco){
		int[][] custo = new int[tam][tam];
		int[] distancia = new int[tam];
		int[] anterior =new int[tam]; //anterior de cada nó
		int[] visitado = new int[tam];
		int count, min_dist, nextN = 0, i, j;
		
		for(i = 0; i< n; i++){
			for(j = 0; j < n; j++){
				if(matriz[i][j] == 0){
					custo[i][j] = Integer.MAX_VALUE;
				}else{
					custo[i][j] = matriz[i][j];
				}
				
			}
		}
		for(i = 0; i < n; i++){
			distancia[i]=custo[comeco][i];
			anterior[i]=comeco;
			visitado[i]=0;
		}
		
		distancia[comeco] =0;
		visitado[comeco] = 1;
		count = 1;
		while(count < n-1){
			min_dist = Integer.MAX_VALUE;
			
			for(i = 0; i < n; i++){
				if(distancia[i] < min_dist && distancia[i] != visitado[i]){
					min_dist = distancia[i];
					nextN = i; //nó de menor distancia
					
				}
				
				visitado[nextN] = 1;
				for(i = 0;i < n; i ++){
					if(visitado[i] != 0){
						if(min_dist + custo[nextN][i] < distancia[i]){
							distancia[i] = min_dist + custo[nextN][i];
							anterior[i] = nextN;
						}
						
					}
				}
				count++;
			}
		}
		
		for(i = 0; i < n; i++){
			if(i != comeco){
				System.out.println("Caminho:" + i);
				j = i;
				do{
					j=anterior[i];
					System.out.println("<<" + j);
				}while(j != comeco);
			}
		}
		
	}
	
	
	public static int minK(int key[], boolean mSet[], int tam){
		int min = Integer.MAX_VALUE;
		int min_index = 0;
		
		for (int i = 0; i < tam; i++){
			if(mSet[i]== false && key[i] < min){
				min = key[i];
				min_index = i;
			}
		}
		return min_index;
		
	}

	public static void printMST(int parent[], int n, int matriz[][]){
		int total = 0;
		for(int i =1; i < tam; i++){
			
			total += matriz[i][parent[i]];
		}
		System.out.println("Solução:" + total);
	}
	
	
	public static void pMatriz(int[][] matriz, int tam){
		//prenche a outra diagonal da matriz
		
		for(int i = 0; i < tam; i++){ //linhas
			for(int j = 0; j < tam; j++){ //colunas
				if(j < i && j != i){
					matriz[i][j] = matriz[j][i];
				}
			}			
		}
	}

	public static void zeroM(int[][] matriz, int tam){
		for(int i = 0; i < tam; i++){ //linhas
			for(int j = 0; j < tam; j++){ //colunas
				matriz[i][j] = 0;
			}			
		}
	}
	
	public static void showmatriz(int[][] matriz, int tam){	
		for(int i = 0; i < tam; i++){ //linhas
			for(int j = 0; j < tam; j++){ //colunas
				System.out.printf(String.format("[%4d]", matriz[i][j]));
			}			
			System.out.println("\n");
		}
	}
}
 