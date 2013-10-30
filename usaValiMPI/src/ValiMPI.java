

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
 * coisa importante para usar o códio:
 * 1) chamar o método lerstring
 * 2) modificar o tamanho do vetor matriz tem que ter [i] [j], onde
 *  i = nº total de elementos requeridos do critério e j = nº da quantidade
 *  de dados de teste presente no arquivo que contém os dados de teste                                                                    *
 * 3)   o valor da condicao do if dentro do metodo escreverPlanilha() tem que
 * ser igual a quatidade de dados de teste presente no arquivo de dados de teste
 * 4) quando for critério de fluxo de dados com (todos-p-usos, todos-c-usos) o
 * argumento passado para o método split que está dentro do metodo pegaCobertura()
 * tem qeu mudar para ", c" (virgula espaço c) ao invés de somente "," que é usado
 * para o critério de fluxo de controle
 * 5) preencher o valor do argumento passado pra o método pegaElemReq() presente no 
 * metodo lerArquivo com o valor do criterio que esta sendo testado
 *                                     									   *
 *                                                                         *
 ***************************************************************************/
public class ValiMPI {

	
	
	int quantElmentos = 0; //conta a quantidade de elementos req
	int [][]matriz = new int[34][100]; 
	int nDadoTeste = 1;
	
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
			this.executaDado(String.valueOf(1), dadoComAspas);
			//Thread.sleep(3000);

			System.out.println("Dado de teste: " + nDadoTeste);// ficar no
																// formato
//			String conteudo = "Dado de teste: " + nDadoTeste;
//			String conteudo2 = "Critério de teste: " + criterioDeTeste;
			//escreverArquivo("arqCoberturaIndividualTodas-nos.txt", conteudo);
			//escreverArquivo("arqCoberturaIndividualTodas-nos.txt", conteudo2);
			this.obtemCobertura(criterioDeTeste);
			//Thread.sleep(1000);
			pegaElemReq("todos-s-usos.req");
			pegaLinhaElemCobertos("arqCoberturaIndividual.txt");
			escrevePlanilha();
			linha = lerArq.readLine(); // le da segunda linha em diante
			nDadoTeste++;
		}

		// this.obtemCobertura(criterioDeTeste);

		// this.backup();
		lerArq.close();

		// System.out.print("Cobertura: "+ coberturaDoDado);
	}

	/*** Metodo usado para executadar um dado de teste em que eh passado por
	 * agurmento o numero do dado e o dado em si 
	 * @throws IOException
	 * @throws InterruptedException */
	public void executaDado(String nDadoTeste, String dado) throws IOException, InterruptedException {

		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				"/home/labs/Software/ValiMPI/vali_exec/vali_exec " + nDadoTeste
						+ " run 4 gcd " + dado + "; exit  echo '<enter>'; read" };

		Process proc =	Runtime.getRuntime().exec(cmd, null, null);
		
		proc.waitFor();
	}

	/** obtem a cobertura de todos os dados teste dentro da pasta valimpi
	 * @throws IOException 
	 * @throws InterruptedException */
	public void obtemCobertura(String criterio) throws IOException, InterruptedException {

		String cmd[] = {
				"gnome-terminal", "-x",	"bash",		"-c",
				"/home/labs/Software/ValiMPI/vali_eval/vali_eval "	+ criterio
			   + " 4 \"Master(0)\" \"Slave(1,2,3)\" > arqCoberturaIndividual.txt; exit echo '<exit>'; read" };
		
			Process proc =	Runtime.getRuntime().exec(cmd, null, null);
			proc.waitFor();
	}

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

	/*** percorre os arquivo de conbertura e pega somente as linhas que conte a
	 * pocentagem de cobertura @throws IOException */
	public void pegaLinhaElemCobertos(String nomeArq) throws IOException {

		// Nome do arquivo pode ser absoluto, ou pastas /dir/teste.txt
		String linha = "";

		FileReader arq = new FileReader(nomeArq);
		BufferedReader lerArq = new BufferedReader(arq);
		
		apagaConteudoArq("arquivoCobertura.txt");
		// lê a primeira linha
		linha = lerArq.readLine();
		// "linha" recebe "null" ler o última linha do arquivo
		while (linha != null) {
			
			if (linha.equals("-- ELEMENTOS REQUERIDOS NÃO COBERTOS --")){
				break;
			}
			
			if (linha.endsWith("sync 0")) {
				pegaCobertura(linha);
			}
			// lê da segunda até a última linha
			linha = lerArq.readLine();
		}
		// fecha o arquivo
		arq.close();
	}

	/** percorre o arquivo com as linhas de cobertura (obtida pelo método pegaLinha())
	 *  e retorna sómente os valores em porcentagem (ex. 45%) @throws IOException */
	public void pegaCobertura(String linha) throws IOException {
		
		String quebra[];
		String cobertura;
		//quebra a linha recebida no ponto ","
		quebra = linha.split("> c");
		// passa a primeira parte da linha para cobertura
		cobertura = quebra[0].trim();

		// recebe um caminho de um arquivo
		FileWriter fw = new FileWriter("arquivoCobertura.txt", true);
		// da a permissão para esse aquivo ser escrito
		BufferedWriter conexao = new BufferedWriter(fw);
		// escreve no arquivo
		conexao.write(cobertura);
		// adiciona um nova linha no arquivo
		conexao.newLine();
		conexao.close();
	}
	
	/** metodo usado pegas os elementos req. dentdo da pasta valimp/res @throws IOException */
	
	public void pegaElemReq(String Criterio) throws IOException
	{
		// nome do arquivo de elementos requeridos
				String nomeArqElem = "valimpi/res/" + Criterio;
						// le a linha do arquivo que contem os elem. req.
				String linhaArqElem = "";
				// contador da quantidade de elementos requeridos no arq. de elem. req.
				int contLinhasArqElem = 2;
				

				// permissoes de leitura do arquivo de elem. req
				FileReader arqElem = new FileReader(nomeArqElem);
				BufferedReader lerArqElem = new BufferedReader(arqElem);

				// le primeira linha do arquivo de elem. req.
				linhaArqElem = lerArqElem.readLine();
				
				apagaConteudoArq("arqElementos.txt");
				// laco que le todos as linhas do arquivos elem. req
				while (linhaArqElem != null) {
					// escreve na planilha a partir da 2º linha em diante
					if (contLinhasArqElem > 3) {
					//	pw.println(linhaArqElem);
						escreverArquivo("arqElementos.txt", linhaArqElem);
						quantElmentos++;
					}

					// le da segunda até a última linha
					linhaArqElem = lerArqElem.readLine();
					contLinhasArqElem++;
				} // fim do laco while
				lerArqElem.close();
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
								
								if (nDadoTeste ==100){
									
								for(int i=0; i<34; i++){  
						            for(int j=0; j<100; j++){  
						               
						                System.out.print(matriz[i][j]+"\t");  
						            }  
						            System.out.print("\n");  
						        } 
								
								}
								quantElmentos=0;
	}
	
		/** metodo usado para apagar um arquivo @throws IOException */
			public void apagaConteudoArq (String arquivo) throws FileNotFoundException
		{
			PrintWriter writer = new PrintWriter(arquivo);
			writer.print("");
			writer.close();
		}
			
			/** metodo usado para esquever em um arquivo @throws IOException */
			public void escreverArquivo(String caminho, String conteudo)throws IOException {
			
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
	
	
}
