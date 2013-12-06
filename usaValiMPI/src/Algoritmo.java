

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
/***************************************************************************
* passos importantes para usar o códio:
 * 1) chamar o método lerArquivo
 * 4) quando for critério de fluxo de dados com (todos-p-usos, todos-c-usos, todos-s-uso) o
 * argumento passado para o método split que está dentro do metodo pegaCobertura()
 * tem qeu mudar para ",  c" (virgula, espaço, espaço e c) ao invés de somente "," que é usado
 * para o critério de fluxo de controle                                                           *
 ***************************************************************************/
public class Algoritmo {

	
	
	static int quantElmentos = 0; //conta a quantidade de elementos req
	int nElementosReq = 34, nDadoteste = 100;
	int [][]matriz = new int[nElementosReq][nDadoteste]; 
	static int nDadoTeste = 1;
	
	/**
	 * le o aquivo de entrada com os dados de teste e mostra cobertura obtida
	 * por cada um deles	 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	public void lerArquivo(String nomeArq, String criterioDeTeste)
			throws IOException, InterruptedException {

		String linha = "", dadoComAspas = "";

		FileReader arq = new FileReader(nomeArq);
		BufferedReader lerArq = new BufferedReader(arq);
		

		linha = lerArq.readLine(); // le a primeira linha
		while (linha != null) {
			// System.out.print("Passei aqui: "+ nDadoTeste);
			dadoComAspas = "\"" + linha + "\" "; // variavel utilzado para o
													// dado
			ValiMPI.vali_exec(String.valueOf(1), dadoComAspas);
			//Thread.sleep(3000);

			System.out.println("Dado de teste: " + nDadoTeste);// ficar no
																// formato
		//	String conteudo = "Dado de teste: " + nDadoTeste;
//			String conteudo2 = "Critério de teste: " + criterioDeTeste;
		//	escreverArquivo("arqCoberturaIndividual.txt", conteudo);
			//escreverArquivo("arqCoberturaIndividualTodas-nos.txt", conteudo2);
			ValiMPI.vali_eval(criterioDeTeste);
			//Thread.sleep(1000);
			ValiMPI.pegaElemReq(criterioDeTeste + ".req");
			ValiMPI.pegaLinhaElemCobertos("arqCoberturaIndividual.txt");
			escrevePlanilha();	
			linha = lerArq.readLine(); // le da segunda linha em diante
			nDadoTeste++;
		}

		// this.obtemCobertura(criterioDeTeste);

		// this.backup();
		lerArq.close();

		// System.out.print("Cobertura: "+ coberturaDoDado);
	}

	/**
	 * le o aquivo de entrada com os dados de teste e mostra cobertura obtida
	 * por cada um deles além de guardar em um diretorio todos os traces executados
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	public void lerArquivoGuardando(String nomeArq, String criterioDeTeste)
			throws IOException, InterruptedException {

		String linha = "", dadoComAspas = "";

		FileReader arq = new FileReader(nomeArq);
		BufferedReader lerArq = new BufferedReader(arq);
		

		linha = lerArq.readLine(); // le a primeira linha
		while (linha != null) {
			// System.out.print("Passei aqui: "+ nDadoTeste);
			dadoComAspas = "\"" + linha + "\" "; // variavel utilzado para o
													// dado
			ValiMPI.vali_exec(String.valueOf(1), dadoComAspas);
			//Thread.sleep(3000);

			System.out.println("Dado de teste: " + nDadoTeste);// ficar no
															// formato
			String conteudo = "Dado de teste: " + nDadoTeste;
//			String conteudo2 = "Critério de teste: " + criterioDeTeste;
			escreverArquivo("arqCoberturaIndividual.txt", conteudo);
			//escreverArquivo("arqCoberturaIndividualTodas-nos.txt", conteudo2);
			ValiMPI.vali_eval(criterioDeTeste);
			ValiMPI.renomearPasta(String.valueOf(nDadoTeste));
			linha = lerArq.readLine(); // le da segunda linha em diante
			nDadoTeste++;
		}

		ValiMPI.vali_eval(criterioDeTeste);

		// this.backup();
		lerArq.close();

		// System.out.print("Cobertura: "+ coberturaDoDado);
	}
	/**
	 * le o aquivo de entrada com os dados de teste e mostra cobertura obtida
	 * pelo diretorio valimpi inteiro	 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void lerArquivo( int nDado, String nomeArq, String criterioDeTeste)
			throws IOException, InterruptedException {

		String linha = "", dadoComAspas = "";

		FileReader arq = new FileReader(nomeArq);
		BufferedReader lerArq = new BufferedReader(arq);
		

		linha = lerArq.readLine(); // le a primeira linha
		while (linha != null) {
			// System.out.print("Passei aqui: "+ nDadoTeste);
			dadoComAspas = "\"" + linha + "\" "; // variavel utilzado para o
													// dado
			ValiMPI.vali_exec(String.valueOf(nDado), dadoComAspas);			

			System.out.println("Dado de teste: " + nDado);// ficar no																// formato

			linha = lerArq.readLine(); // le da segunda linha em diante
			
			nDado++;
		}

		ValiMPI.vali_eval(criterioDeTeste);

		// this.backup();
		lerArq.close();

		// System.out.print("Cobertura: "+ coberturaDoDado);
	}
	
	/**
	 * ler o arquivo de dados e passar para o arquivo args dentro da pasta 0001 dando um
	 *"/n" entre cada argumento do dado. obterm elementos. 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void lerArquivoArgs( String nomeArq, String criterioDeTeste)
			throws IOException, InterruptedException {

		String linha = "", dado = "";
		String []quebra = null;
		FileReader arq = new FileReader(nomeArq);
		BufferedReader lerArq = new BufferedReader(arq);
		

		linha = lerArq.readLine(); // le a primeira linha
		while (linha != null) {
			// System.out.print("Passei aqui: "+ nDadoTeste);
			quebra = linha.split(" "); 
			dado = quebra[0].trim() + "\n" + quebra[1].trim() + "\n" + quebra[2].trim() + "\n";																
			escreverArquivoSobrescrevendo("valimpi/test_case0001/args", dado);														
			ValiMPI.rerun("1");
			System.out.println("Dado de teste: " + nDadoTeste);
			ValiMPI.vali_eval(criterioDeTeste);
			ValiMPI.pegaElemReq(criterioDeTeste + ".req");
			ValiMPI.pegaLinhaElemCobertos("arqCoberturaIndividual.txt");
			escrevePlanilha();	
			linha = lerArq.readLine(); // le da segunda linha em diante
			nDadoTeste++;
		}
		
		// this.backup();
		lerArq.close();

		// System.out.print("Cobertura: "+ coberturaDoDado);
	}
	/*** Metodo usado para executadar um dado de teste em que eh passado por
	 * agurmento o numero do dado e o dado em si 
	 * @throws IOException
	 * @throws InterruptedException */
	

	int conjuntoAtual = 1;;

	/*** Metudo usado para fazer o backup dos casos de teste executados */
	public void backup() throws IOException {

		File diretorio = new File("valimpi");
		File arquivoBackup = new File("Todos Nos_Conjunto_" + conjuntoAtual);

		arquivoBackup.mkdir();
		copyDirectory(diretorio, arquivoBackup);
		conjuntoAtual++;
	}

	/** Metodo usado copiar um diretorio ( na verdade faz um backup do diretorio
	 * atual, pois este vai ser sobscrito na proxima geracao*/
	public void copyDirectory(File sourceLocation, File targetLocation)
			throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]), new File(
						targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	


	

	 
	public void escrevePlanilha() throws IOException {
			
	//	=====================================================================================================
	//algoritmo usado para comparar dois arrays e quando i == j marcar o vetor k, com o mesmo tamanho de dois
	//outros dois vetores, com 1
				
				String[] VetorI = new String [quantElmentos];
				String[] VetorJ = new String [quantElmentos];
				int[] VetorK = new int [quantElmentos];
				

					
							String linhaArqElem2 = "";
							String linhaArqElemcobertos = "";
							
							// permissoes de leitura do arquivo de elementos
							FileReader arqElem2 = new FileReader("arqElementos.txt");
							BufferedReader lerArqElem2 = new BufferedReader(arqElem2);
							
							// permissoes de leitura do arquivo que contém os elem cobertos
							FileReader arqElemCobertos = new FileReader("arquivoCobertura.txt");
							BufferedReader lerElemCobertos = new BufferedReader(arqElemCobertos);
							
							//copiad o conteudo dos dois aquivos acima para arrays
							//=================================================================================================							
							linhaArqElem2 = lerArqElem2.readLine();
								for (int i = 0; i < VetorI.length; i++) {
									VetorI[i] =	linhaArqElem2;
									//System.out.println("i: "+VetorI[i]);							
									linhaArqElem2 = lerArqElem2.readLine();							
								}//termina for para i
								
								
								linhaArqElemcobertos = lerElemCobertos.readLine();
								for (int j = 0; j < VetorJ.length; j++) {
									VetorJ[j] =	linhaArqElemcobertos;
									//System.out.println("j: "+VetorJ[j]);
									
									linhaArqElemcobertos = lerElemCobertos.readLine();
									}// termina for para j
								
								lerArqElem2.close();
								lerElemCobertos.close();
		//=================================================================================================						
								//algoritmo usado para comparar dois arrays e quando i == j marcar o vetor k, com o mesmo tamanho de dois
								//outros dois vetores, com 1
								
								for (int i = 0; i < VetorI.length; i++) {
									for (int j = 0; j < VetorJ.length; j++) {
										if (VetorI[i].equals(VetorJ[j])) {
											VetorK[i] = 1;
											
											
											
										}										
										
									}// termina for para i

								}// termina for para j
																
								//preenche a matris
								for(int i=nDadoTeste-1; i<=nDadoTeste-1; i++){  
						            for(int j=0; j<matriz.length; j++){  
						                matriz[j][i] = VetorK[j];   
						                //System.out.print(matriz[j][i]+"\n");
						            }
								}
							
								//mostra a matris
								
								if (nDadoTeste == nDadoteste){
								
									
								for(int i=0; i<nElementosReq;  i++){ //valor da condição de i é igual a quantidade  de eleemntos requeridos  
						            for(int j=0; j<nDadoteste; j++){  
						               
						                System.out.print(matriz[i][j]+"\t");  
						            }  
						            System.out.print("\n");  
						        } 
								
								}
								quantElmentos=0;
	}
	
		/** metodo usado para apagar um arquivo @throws IOException */
			public static void apagaConteudoArq (String arquivo) throws FileNotFoundException		{
				PrintWriter writer = new PrintWriter(arquivo);
				writer.print("");
				writer.close();
		}
			
			/** metodo usado para esquever em um arquivo @throws IOException */
			public static void escreverArquivo(String caminho, String conteudo)throws IOException {
			
				// recebe um caminho de um arquivo
				FileWriter fw = new FileWriter(caminho, true);
				// da a permissao para esse aquivo ser escrito
				BufferedWriter conexao = new BufferedWriter(fw);
				// escreve no arquivo
				conexao.write(conteudo);
				// adiciona nova linha no arquivo
				conexao.newLine();
				conexao.close();

		}
			
			/** metodo usado para esquever em um arquivo @throws IOException */
			public static void escreverArquivoSobrescrevendo(String caminho, String conteudo)throws IOException {
				 BufferedWriter out = new BufferedWriter(new FileWriter(caminho));
			     out.write(conteudo);
			     out.flush();
			     out.close();

		}
	
	
}
