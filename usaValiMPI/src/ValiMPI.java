import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import sun.awt.SunHints.Value;


public class ValiMPI {
	
	/*** percorre os arquivo de conbertura e pega somente as linhas que conte a
	 * pocentagem de cobertura @throws IOException */
	public static void pegaLinhaElemCobertos(String nomeArq) throws IOException {

		// Nome do arquivo pode ser absoluto, ou pastas /dir/teste.txt
		String linha = "";

		FileReader arq = new FileReader(nomeArq);
		BufferedReader lerArq = new BufferedReader(arq);
		
		Algoritmo.apagaConteudoArq("arquivoCobertura.txt");
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
	public static void pegaCobertura(String linha) throws IOException {
		
		String quebra[];
		String cobertura;
		//quebra a linha recebida no ponto ","
		quebra = linha.split(",  c");
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
	
	public static void  pegaElemReq(String Criterio) throws IOException
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
				
				Algoritmo.apagaConteudoArq("arqElementos.txt");
				// laco que le todos as linhas do arquivos elem. req
				while (linhaArqElem != null) {
					// escreve na planilha a partir da 2º linha em diante
					if (contLinhasArqElem > 3) {
					//	pw.println(linhaArqElem);
						Algoritmo.escreverArquivo("arqElementos.txt", linhaArqElem);
						Algoritmo.quantElmentos++;
					}

					// le da segunda até a última linha
					linhaArqElem = lerArqElem.readLine();
					contLinhasArqElem++;
				} // fim do laco while
				lerArqElem.close();
	}
	
	public static void vali_exec(String nDadoTeste, String dado) throws IOException, InterruptedException {

		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				"/home/labs/Software/ValiMPI_mmr/vali_exec/vali_exec " + nDadoTeste
						+ " run 4 gcd " + dado + "; exit  echo '<enter>'; read" };

		Process proc =	Runtime.getRuntime().exec(cmd, null, null);
		
		proc.waitFor();
	}
	
	public static void rerun(String nDadoTeste) throws IOException, InterruptedException {

		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				" /home/labs/Software/ValiMPI_mmr/vali_exec/vali_exec "+ nDadoTeste + " rerun "+"; exit  echo '<enter>'; read" };

		Process proc =	Runtime.getRuntime().exec(cmd, null, null);
		
		proc.waitFor();
	}

	/** obtem a cobertura de todos os dados teste dentro da pasta valimpi
	 * @throws IOException 
	 * @throws InterruptedException */
	public static void vali_eval(String criterio) throws IOException, InterruptedException {

		String cmd[] = {
				"gnome-terminal", "-x",	"bash",		"-c",
				"/home/labs/Software/ValiMPI_mmr/vali_eval/vali_eval "	+ criterio
			   + " 4 \"Master(0)\" \"Slave(1,2,3)\" > arqCoberturaIndividual.txt; exit echo '<exit>'; read" };
		
			Process proc =	Runtime.getRuntime().exec(cmd, null, null);
			proc.waitFor();
	}
	
	public static void renomearPasta(String nDadoTeste) throws IOException, InterruptedException {
		if (Integer.parseInt(nDadoTeste) < 10) {
		String cmd[] = {
				"gnome-terminal",
				"-x",
				"bash",
				"-c",
				" mv  ~/Dropbox/git/usaValiMPI/usaValiMPI/valimpi/test_case0001 ~/Dropbox/git/usaValiMPI/usaValiMPI/copia/test_case000" + nDadoTeste
						+  "; exit  echo '<enter>'; read" };
		
		Process proc =	Runtime.getRuntime().exec(cmd, null, null);
		proc.waitFor();
		
		}else if (Integer.parseInt(nDadoTeste) < 100) {
			String cmd[] = {
					"gnome-terminal",
					"-x",
					"bash",
					"-c",
					" mv  ~/Dropbox/git/usaValiMPI/usaValiMPI/valimpi/test_case0001 ~/Dropbox/git/usaValiMPI/usaValiMPI/copia/test_case00" + nDadoTeste
							+  "; exit  echo '<enter>'; read" };
			
				Process proc =	Runtime.getRuntime().exec(cmd, null, null);
				proc.waitFor();
			}	else if (Integer.parseInt(nDadoTeste) < 1000) { 
			String cmd[] = {
					"gnome-terminal",
					"-x",
					"bash",
					"-c",
					" mv  ~/Dropbox/git/usaValiMPI/usaValiMPI/valimpi/test_case0001 ~/Dropbox/git/usaValiMPI/usaValiMPI/copia/test_case0" + nDadoTeste
							+  "; exit  echo '<enter>'; read" };
			
					Process proc =	Runtime.getRuntime().exec(cmd, null, null);
					proc.waitFor();
			}
		
	}
	
	
}
