

import java.io.IOException;

public class Main extends ValiMPI{

	static ValiMPI objComunicaValiMPI;
	
	public static void main(String[] args) throws IOException,
			InterruptedException {
		objComunicaValiMPI = new ValiMPI();
	//	objComunicaValiMPI.escrevePlanilha();
		
		objComunicaValiMPI.lerArquivo("datasetGCD/dataset - 1.txt", "todas-arestasS");
		
	}
}
