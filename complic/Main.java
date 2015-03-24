package complic;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.Runtime;

import complic.analyse.AnalyseurLexical;
import complic.analyse.AnalyseurSyntaxique;
import complic.analyse.ASMUtils;
import complic.analyse.Bloc;
import complic.analyse.TableSymboles;
import complic.erreurs.ErreurSyntaxique;
import complic.erreurs.FichierInexistant;

public class Main {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.out.println("Usage : java complic.Main [-exec] FILE.plic");
			System.out.println("Note that you must have mars.jar in the current directory in order to execute");
			System.exit(-1);
		} else {
			String program = "";
			boolean exec = false;
			if((args.length == 2) && args[0].equals("-exec")) {
				program = args[1];
				exec = true;
			} else {
				program = args[0];
			}
			try {
				AnalyseurSyntaxique as = new AnalyseurSyntaxique();
				Bloc result = as.analyser(program);
				//System.out.println(result);

				result.verifier();

				FileWriter fw = new FileWriter(new File("out.mips"));
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(ASMUtils.getInstance().generer(result));
				bw.close();

				System.out.println("======================================");
				System.out.println("Code generation successful => out.mips");
				System.out.println("======================================");

				if(exec) {
					execute();
				}
			} catch(IOException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			} catch(FichierInexistant e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			} catch(ErreurSyntaxique ex) {
				System.out.println(ex.getMessage());
				System.exit(-1);
			}
		}
	}

	private static void execute() throws IOException, InterruptedException {
		System.out.println("Now launching program");
		System.out.println("======================================");
		System.out.println();

		Process p = Runtime.getRuntime().exec("java -jar mars.jar nc out.mips");
		BufferedReader output = getOutput(p);

		String ligne = "";
		while((ligne = output.readLine()) != null) {
			System.out.println(ligne);
		}
		p.waitFor();
	}

    private static BufferedReader getOutput(Process p) {
			return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }
}