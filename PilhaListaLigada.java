import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PilhaListaLigada {
	
	protected class No {
		Integer chave = null;
		No prox = null;
		
		No (int chave, No prox) {
			this.chave = chave;
			this.prox = prox;
		}
	}
	
	protected No cabeca;
	
	PilhaListaLigada() {
		cabeca = null;
	}
	
	public static BufferedReader leitor(String path) {
		try {
			FileInputStream fstream = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			return br;
		} catch (Exception e) {
			System.out.println("Erro ao ler arquivo " + path + " : " + e.getMessage());
			return null;
		}
	}
	
	public static BufferedWriter escritor(String path) {
		try {
			FileOutputStream fstream = new FileOutputStream(path);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fstream));
			return bw;
		} catch (Exception e) {
			System.out.println("Erro ao escrever no arquivo " + path + " : " + e.getMessage());
			return null;
		}
	}
	
	public void add(int newElement) {
		No novo = new No(newElement, cabeca);
		cabeca = novo;
	}
	
	public int remove() {
		int tmp = cabeca.chave;
		cabeca = cabeca.prox;
		return tmp;
	}
	
	public void limparPilha() {
		cabeca = null;
	}
	
	public static void main(String[] args) {
		try {
			String dirArqLog = "C:\\Users\\PC\\Desktop\\EP3\\log_PilhaListaLigada.txt";			// Insira aqui o caminho do arquivo que recebera o log
			String dirArqTempo = "C:\\Users\\PC\\Desktop\\EP3\\tempo_PilhaListaLigada.csv";		// Insira aqui o caminho do arquivo que recebera o tempo de processamento
			String dirPastaEntradas = "C:\\Users\\PC\\Desktop\\EP3\\entradas";	// Insira aqui o caminho da pasta que contem os arquivos de entrada

//            String dirArqLog = "/Users/stacks-efficiency/saidas/log_PilhaListaLigada.txt";
//            String dirArqTempo = "/Users/stacks-efficiency/saidas/tempo_PilhaListaLigada.csv";
//            String dirPastaEntradas = "/Users/stacks-efficiency/entradas";

			BufferedWriter log = escritor(dirArqLog);
			BufferedWriter tempo = escritor(dirArqTempo);
			tempo.write("Nome do Arquivo;Tempo de Processamento(ms);Quantidade de Linhas"); // Cabecalho do arquivo dos tempos de processamento
			tempo.newLine();
			
			File directoryPath = new File(dirPastaEntradas);
			String contents[] = directoryPath.list(); // Lista com todos os arquivos existentes na pasta de entrada
			
			PilhaListaLigada pilhaListaLigada = new PilhaListaLigada();
			BufferedReader leitor;
			String str;
			
			for (int i = 0; i < contents.length; i++) { // Percorre os arquivos da pasta de entrada
				System.out.println("Processando Arquivo: " + contents[i]);
				log.write("Processando Arquivo: " + contents[i]);
				log.newLine();
				log.newLine();
				
				leitor = leitor(dirPastaEntradas + "\\" + contents[i]);
//                leitor = leitor(dirPastaEntradas + "/" + contents[i]);

				str = leitor.readLine();
				int qntLinhas = 0;
				long t = System.currentTimeMillis();
				
				while (str != null) { // Percorre as linhas do arquivo
					qntLinhas++;
					
					if (str.trim().equals("")) {
						log.write(String.valueOf(pilhaListaLigada.remove()));
						log.newLine();
					} else {
						pilhaListaLigada.add(Integer.parseInt(str));
					}
					str = leitor.readLine();
				}
				t = System.currentTimeMillis() - t;
				
				tempo.write(contents[i] + ";" + t + ";" + qntLinhas);
				tempo.newLine();
				if (i != contents.length - 1) log.newLine();
				pilhaListaLigada.limparPilha();
				leitor.close();
			}
			log.close();
			tempo.close();
			
		} catch (Exception e) {
			System.out.println("Erro ao executar o programa (Possivelmente Arquivo de Entrada Corrompido): " + e.getMessage());
			e.printStackTrace();
		}
	}
}
