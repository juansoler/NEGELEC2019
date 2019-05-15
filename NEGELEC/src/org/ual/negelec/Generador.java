package org.ual.negelec;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class Generador {
	
	int minimumSupport;
	
	int numeroInstancias; 
	
	public StringBuilder salida = new StringBuilder();
	
	public String[] conjuntoCompleto = new String[] {"Placa Base Gigabyte","i7 4660"
			, "i5 3500"
			, "i3 2500"
			, "4GB RAM"
			, "8GB RAM"
			, "Tarjeta Grafica NVIDIA GTX 1250"
			, "Placa Base ASRock VGA INTEGRADA"
			, "Raton optico"
			, "Raton inalambrico"
			, "i7 2500"
			, "i5 3250"
			, "i9 8000"
			, "i3 1500"
			, "Teclado 105 teclas"
			, "Portatil Asus"
			, "Iphone XS"
			, "MacBook Pro"
			, "Monitor 22 pulgadas"
			, "Monitor 32 pulgadas"
			, "Tarjeta Grafica ATI RADEON 9000"
			, "Tarjeta Grafica ATI RADEON 7000"
			, "Pasta Termica"
			, "Tarjeta de memoria 32GB"
			, "Tarjeta de memoria 64GB"
			, "Lector de tarjetas"
			, "CAJA ATX"
			};
	
	public Generador(int minimumSupport, int numeroInstancias, String saveFile) {
		super();
		this.minimumSupport = minimumSupport;
		this.numeroInstancias = numeroInstancias;
		FileWriter fichero = null;
	    PrintWriter pw = null;
	    try
        {
            fichero = new FileWriter(saveFile);
            pw = new PrintWriter(fichero);
            pw.println(minimumSupport);
            salida.append(minimumSupport + System.getProperty("line.separator"));

            for (int i = 0; i < numeroInstancias; i++) {
    			int numeroAtributos = ThreadLocalRandom.current().nextInt(1, 5);
    			HashSet<String> list = new HashSet();
				
    			while (list.size()<=numeroAtributos) {
					list.add(conjuntoCompleto[ThreadLocalRandom.current().nextInt(0, conjuntoCompleto.length)]);
    			}
    			
//    			for (int j = 0; j< numeroAtributos; j++) {
//    				
//    				while (!list.add(conjuntoCompleto[ThreadLocalRandom.current().nextInt(0, conjuntoCompleto.length)])) {
//    					list.add(conjuntoCompleto[ThreadLocalRandom.current().nextInt(0, conjuntoCompleto.length)]);
//    				}
//    				
//    			}
    			String temp = list.toString().replace("[", "");
    			temp = temp.replace("]", "");

    			pw.println(temp);
    			salida.append(temp+System.getProperty("line.separator"));
    		}
    			

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
		
		
		
	}

	
}
